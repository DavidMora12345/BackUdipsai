package com.test.TUdipsaiApi.Controller;

import com.test.TUdipsaiApi.Model.Especialistas;
import com.test.TUdipsaiApi.Service.EspecialistasService;
import com.test.TUdipsaiApi.dto.EspecialistasDTO;
import com.test.TUdipsaiApi.dto.EspecialistasIdDTO;
import com.test.TUdipsaiApi.dto.EspecialistasSinImagenDTO;
import com.test.TUdipsaiApi.dto.PermisosDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public Optional<Especialistas> buscarPorCedula(@PathVariable String cedula) {
        return especialistasService.findByCedula(cedula);
    }

    @PostMapping("/insertar")
    public ResponseEntity<Especialistas> crearOActualizarEspecialista(@RequestBody EspecialistasIdDTO especialistaDTO) {
        try {
            Especialistas savedEspecialista = especialistasService.saveOrUpdate(especialistaDTO);
            return ResponseEntity.ok(savedEspecialista);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
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
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PutMapping("/actualizar/{cedula}")
    public ResponseEntity<Especialistas> actualizarEspecialista(@PathVariable String cedula, @RequestBody EspecialistasIdDTO especialistaDTO) {
        try {
            Optional<Especialistas> updatedEspecialista = especialistasService.updateEspecialista(cedula, especialistaDTO);
            return updatedEspecialista.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @GetMapping("/pasantes")
    public ResponseEntity<List<EspecialistasSinImagenDTO>> listarPasantes() {
        List<EspecialistasSinImagenDTO> pasantes = especialistasService.findAllPasantesSinImagen();
        return ResponseEntity.ok(pasantes);
    }
}
