package com.test.TUdipsaiApi.Controller;

import com.test.TUdipsaiApi.Model.Especialistas;
import com.test.TUdipsaiApi.Service.EspecialistasService;
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
public class EspecialistaController {

    @Autowired
    private EspecialistasService especialistasService;

    @GetMapping("/activos")
    public List<Especialistas> listarEspecialistasActivos() {
        return especialistasService.findAllActive();
    }

    @GetMapping("/{cedula}")
    public Optional<Especialistas> buscarPorCedula(@PathVariable String cedula) {
        return especialistasService.findByCedula(cedula);
    }

    @PostMapping("/insertar")
    public Especialistas crearOActualizarEspecialista(@RequestBody Especialistas especialista) {
        return especialistasService.saveOrUpdate(especialista);
    }

    @DeleteMapping("/{cedula}")
    public boolean eliminarEspecialista(@PathVariable String cedula) {
        return especialistasService.deleteByCedula(cedula);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody Especialistas especialista) {
        Especialistas resultadoLogin = especialistasService.login(especialista.getCedula(), especialista.getContrasena());
        if (resultadoLogin != null) {
            Map<String, String> respuesta = new HashMap<>();
            respuesta.put("primerNombre", resultadoLogin.getPrimerNombre());
            respuesta.put("segundoNombre", resultadoLogin.getSegundoNombre());
            respuesta.put("primerApellido", resultadoLogin.getPrimerApellido());
            respuesta.put("segundoApellido", resultadoLogin.getSegundoApellido());
            return ResponseEntity.ok(respuesta);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build(); // O devuelve otro código de estado si lo prefieres
        }
    }

}
