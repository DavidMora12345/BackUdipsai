package com.test.TUdipsaiApi.Controller;

import com.test.TUdipsaiApi.Model.Especialistas;
import com.test.TUdipsaiApi.Service.CedulaValidatorService;
import com.test.TUdipsaiApi.Service.EspecialistasService;
import com.test.TUdipsaiApi.dto.EspecialistasDTO;
import com.test.TUdipsaiApi.dto.EspecialistasIdDTO;
import com.test.TUdipsaiApi.dto.EspecialistasSinImagenDTO;
import com.test.TUdipsaiApi.dto.PermisosDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/especialistas")
@CrossOrigin(origins = "*")
public class EspecialistaController {

    @Autowired
    private EspecialistasService especialistasService;

    @Autowired
    private CedulaValidatorService cedulaValidatorService;

    @GetMapping("/activos")
    public ResponseEntity<List<EspecialistasSinImagenDTO>> listarEspecialistasActivos() {
        List<EspecialistasSinImagenDTO> especialistas = especialistasService.findAllActiveSinImagen();
        return ResponseEntity.ok(especialistas);
    }

    @GetMapping("/activos/nopasantes")
    public ResponseEntity<List<Especialistas>> getAllActiveNonPasantes() {
        List<Especialistas> especialistas = especialistasService.findAllActiveNonPasantes();
        return ResponseEntity.ok(especialistas);
    }

    @GetMapping("/{cedula}")
    public ResponseEntity<?> buscarPorCedula(@PathVariable String cedula) {
        try {
            Optional<Especialistas> especialistaOptional = especialistasService.findByCedula(cedula);
            if (especialistaOptional.isPresent()) {
                Especialistas especialista = especialistaOptional.get();
                EspecialistasDTO especialistaDTO = especialistasService.convertToDTO(especialista);
                return ResponseEntity.ok(especialistaDTO);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Especialista no encontrado con cédula: " + cedula);
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar la imagen del especialista.");
        }
    }

    @PostMapping("/insertar")
    public ResponseEntity<?> createEspecialista(@RequestBody EspecialistasIdDTO especialistaDTO) {
        try {
            EspecialistasDTO nuevoEspecialista = especialistasService.createEspecialista(especialistaDTO);

            boolean cedulaValida = cedulaValidatorService.validarCedulaEcuatoriana(especialistaDTO.getCedula());

            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("especialista", nuevoEspecialista);

            if (!cedulaValida) {
                respuesta.put("advertencia", "La cédula es inválida, pero el registro se ha realizado.");
            }

            return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }



    @PutMapping("/actualizar/{cedula}")
    public ResponseEntity<?> actualizarEspecialista(
            @PathVariable String cedula,
            @RequestBody EspecialistasIdDTO especialistaDTO) {
        try {
            Optional<Especialistas> updatedEspecialista = especialistasService.updateEspecialista(cedula, especialistaDTO);

            if (updatedEspecialista.isPresent()) {
                // Validar la cédula después de actualizar
                boolean cedulaValida = cedulaValidatorService.validarCedulaEcuatoriana(cedula);

                Map<String, Object> respuesta = new HashMap<>();
                respuesta.put("especialista", updatedEspecialista.get());

                if (!cedulaValida) {
                    respuesta.put("advertencia", "La cédula es inválida, pero la actualización se ha realizado.");
                }

                return ResponseEntity.ok(respuesta);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }



    @DeleteMapping("/{cedula}")
    public boolean eliminarEspecialista(@PathVariable String cedula) {
        return especialistasService.deleteByCedula(cedula);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Especialistas especialista) {
        Especialistas resultadoLogin = especialistasService.login(especialista.getCedula(), especialista.getContrasena());
        if (resultadoLogin != null) {
            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("cedula", resultadoLogin.getCedula());
            respuesta.put("primerNombre", resultadoLogin.getPrimerNombre());
            respuesta.put("segundoNombre", resultadoLogin.getSegundoNombre());
            respuesta.put("primerApellido", resultadoLogin.getPrimerApellido());
            respuesta.put("segundoApellido", resultadoLogin.getSegundoApellido());
            respuesta.put("esPasante", resultadoLogin.getEsPasante());

            Map<String, Object> especialidad = new HashMap<>();
            especialidad.put("id", resultadoLogin.getEspecialidad().getId());
            especialidad.put("area", resultadoLogin.getEspecialidad().getArea());
            respuesta.put("especialidad", especialidad);

            PermisosDTO permisosDTO = especialistasService.convertToPermisosDTO(resultadoLogin.getEspecialidad().getPermisos());
            respuesta.put("permisos", permisosDTO);

            if (resultadoLogin.getSede() != null) {
                respuesta.put("sede", resultadoLogin.getSede());
            }

            return ResponseEntity.ok(respuesta);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Especialista no está activo o credenciales incorrectas"));
        }
    }

    @GetMapping("/pasantes")
    public ResponseEntity<List<EspecialistasSinImagenDTO>> listarPasantes() {
        List<EspecialistasSinImagenDTO> pasantes = especialistasService.findAllPasantesSinImagen();
        return ResponseEntity.ok(pasantes);
    }
}
