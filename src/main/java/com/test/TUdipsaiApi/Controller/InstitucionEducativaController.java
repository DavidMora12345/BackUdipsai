package com.test.TUdipsaiApi.Controller;

import com.test.TUdipsaiApi.Model.InstitucionEducativa;
import com.test.TUdipsaiApi.Service.InstitucionEducativaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/instituciones")
@CrossOrigin(origins = "*")
public class InstitucionEducativaController {

    private final InstitucionEducativaService institucionEducativaService;

    @Autowired
    public InstitucionEducativaController(InstitucionEducativaService institucionEducativaService) {
        this.institucionEducativaService = institucionEducativaService;
    }

    @GetMapping("/listar")
    public List<InstitucionEducativa> listarInstitucionesActivas() {
        return institucionEducativaService.listarInstitucionesActivas();
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<InstitucionEducativa> obtenerInstitucionPorId(@PathVariable Integer id) {
        Optional<InstitucionEducativa> institucionOpt = institucionEducativaService.obtenerInstitucionPorId(id);
        return institucionOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("insertar")
    public InstitucionEducativa crearInstitucion(@RequestBody InstitucionEducativa institucionEducativa) {
        return institucionEducativaService.guardarInstitucion(institucionEducativa);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<InstitucionEducativa> actualizarInstitucion(@PathVariable Integer id, @RequestBody InstitucionEducativa nuevaInstitucion) {
        try {
            InstitucionEducativa institucionActualizada = institucionEducativaService.actualizarInstitucion(id, nuevaInstitucion);
            return ResponseEntity.ok(institucionActualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarInstitucion(@PathVariable Integer id) {
        institucionEducativaService.cambiarEstadoInstitucion(id);
        return ResponseEntity.noContent().build();
    }
}
