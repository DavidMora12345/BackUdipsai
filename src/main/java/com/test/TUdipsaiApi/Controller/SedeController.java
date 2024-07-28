package com.test.TUdipsaiApi.Controller;

import com.test.TUdipsaiApi.Model.Sede;
import com.test.TUdipsaiApi.Service.SedeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sedes")
@CrossOrigin(origins = "*")
public class SedeController {

    @Autowired
    private SedeService sedeService;

    @GetMapping("/listar")
    public ResponseEntity<List<Sede>> getAllActiveSedes() {
        List<Sede> sedes = sedeService.getAllActiveSedes();
        return ResponseEntity.ok(sedes);
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<Sede> getSedeById(@PathVariable Integer id) {
        Optional<Sede> sede = sedeService.getSedeById(id);
        return sede.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/insertar")
    public ResponseEntity<Sede> crearSede(@RequestBody Sede sede) {
        Sede nuevaSede = sedeService.saveOrUpdate(sede);
        return ResponseEntity.ok(nuevaSede);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Sede> actualizarSede(@PathVariable Integer id, @RequestBody Sede sede) {
        Optional<Sede> sedeOpt = sedeService.getSedeById(id);
        if (sedeOpt.isPresent()) {
            sede.setId(id);
            Sede sedeActualizada = sedeService.saveOrUpdate(sede);
            return ResponseEntity.ok(sedeActualizada);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Void> eliminarSede(@PathVariable Integer id) {
        sedeService.deleteSede(id);
        return ResponseEntity.ok().build();
    }
}
