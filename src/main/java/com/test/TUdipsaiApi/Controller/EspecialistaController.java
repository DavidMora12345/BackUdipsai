package com.test.TUdipsaiApi.Controller;

import com.test.TUdipsaiApi.Model.Especialista;
import com.test.TUdipsaiApi.Service.EspecialistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/especialistas")
@CrossOrigin(origins = "*")
public class EspecialistaController {

    @Autowired
    private EspecialistaService especialistaService;


    @GetMapping("/listar")
    public ResponseEntity<List<Especialista>> getAllEspecialistas() {
        List<Especialista> especialistas = especialistaService.findByEspecialistaEstado(1);
        return ResponseEntity.ok(especialistas);
    }

    @GetMapping("/listar/{cedula}")
    public ResponseEntity<Especialista> getEspecialistaById(@PathVariable String cedula) {
        Optional<Especialista> especialista = especialistaService.findById(cedula);
        return especialista.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/insertar")
    public ResponseEntity<Especialista> createEspecialista(@RequestBody Especialista especialista) {
        Especialista nuevoEspecialista = especialistaService.save(especialista);
        return ResponseEntity.ok(nuevoEspecialista);
    }

    @PutMapping("/actualizar/{cedula}")
    public ResponseEntity<Especialista> updateEspecialista(@PathVariable String cedula, @RequestBody Especialista especialistaDetails) {
        Optional<Especialista> updatedEspecialista = especialistaService.updateEspecialista(cedula, especialistaDetails);
        return updatedEspecialista.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/eliminar/{cedula}")
    public ResponseEntity<Especialista> deleteEspecialista(@PathVariable String cedula) {
        Optional<Especialista> especialista = especialistaService.deleteById(cedula);
        return especialista.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Setter para inyección manual del servicio (opcional)
    public void setEspecialistaService(EspecialistaService especialistaService) {
        this.especialistaService = especialistaService;
    }
}
