package com.test.TUdipsaiApi.Controller;

import com.test.TUdipsaiApi.Model.Paciente;
import com.test.TUdipsaiApi.Service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/pacientes")
@CrossOrigin(origins = "*")

public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @GetMapping("/listar")
    public ResponseEntity<List<Paciente>> getAllPacientes() {
        List<Paciente> pacientes = pacienteService.getAllPacientes();
        return ResponseEntity.ok(pacientes);
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<Paciente> getPacienteById(@PathVariable Integer id) {
        Optional<Paciente> detalleEntidad = pacienteService.getPacienteById(id);
        return detalleEntidad.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/insertar")
    public ResponseEntity<Paciente> createPaciente(@RequestBody Paciente paciente) {
        Paciente nuevoPaciente = pacienteService.createPaciente(paciente);
        return ResponseEntity.ok(nuevoPaciente);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Paciente> updatePaciente(@PathVariable Integer id, @RequestBody Paciente pacienteDetails) {
        Paciente updatedPaciente = pacienteService.updatePaciente(id, pacienteDetails);
        if (updatedPaciente != null) {
            return ResponseEntity.ok(updatedPaciente);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Paciente> deletePaciente(@PathVariable Integer id) {
        Optional<Paciente> paciente = pacienteService.deletePaciente(id);
        return paciente.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
