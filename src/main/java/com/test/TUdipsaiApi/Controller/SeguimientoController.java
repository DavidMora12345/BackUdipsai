package com.test.TUdipsaiApi.Controller;

import com.test.TUdipsaiApi.Model.Seguimiento;
import com.test.TUdipsaiApi.Service.SeguimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/seguimientos")
public class SeguimientoController {

    @Autowired
    private SeguimientoService seguimientoService;

    @GetMapping
    public List<Seguimiento> getAllSeguimientos() {
        return seguimientoService.getAllSeguimientos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Seguimiento> getSeguimientoById(@PathVariable int id) {
        Optional<Seguimiento> seguimiento = seguimientoService.getSeguimientoById(id);
        if (seguimiento.isPresent()) {
            return ResponseEntity.ok(seguimiento.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Seguimiento createSeguimiento(@RequestBody Seguimiento seguimiento) {
        return seguimientoService.saveSeguimiento(seguimiento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Seguimiento> updateSeguimiento(@PathVariable int id, @RequestBody Seguimiento seguimientoDetails) {
        Optional<Seguimiento> optionalSeguimiento = seguimientoService.getSeguimientoById(id);
        if (optionalSeguimiento.isPresent()) {
            Seguimiento seguimiento = optionalSeguimiento.get();
            seguimiento.setEspecialista(seguimientoDetails.getEspecialista());
            seguimiento.setPaciente(seguimientoDetails.getPaciente());
            seguimiento.setFecha(seguimientoDetails.getFecha());
            seguimiento.setObservacion(seguimientoDetails.getObservacion());
            final Seguimiento updatedSeguimiento = seguimientoService.saveSeguimiento(seguimiento);
            return ResponseEntity.ok(updatedSeguimiento);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeguimiento(@PathVariable int id) {
        seguimientoService.deleteSeguimiento(id);
        return ResponseEntity.noContent().build();
    }
}
