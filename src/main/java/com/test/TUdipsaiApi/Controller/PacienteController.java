package com.test.TUdipsaiApi.Controller;

import com.test.TUdipsaiApi.Model.Paciente;
import com.test.TUdipsaiApi.Service.LogService;
import com.test.TUdipsaiApi.Service.PacienteService;
import com.test.TUdipsaiApi.dto.PacienteDTO;
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

    @Autowired
    private LogService logService;

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
    public ResponseEntity<Paciente> crearOActualizarPaciente(@RequestBody PacienteDTO pacienteDTO) {
        Paciente paciente = pacienteService.convertToEntity(pacienteDTO);
        Paciente savedPaciente = pacienteService.saveOrUpdate(paciente);
        logService.logChange("Paciente", savedPaciente.getId().longValue(), "INSERT", null, savedPaciente.toString());
        return ResponseEntity.ok(savedPaciente);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Paciente> updatePaciente(@PathVariable Integer id, @RequestBody PacienteDTO pacienteDTO) {
        Optional<Paciente> pacienteOpt = pacienteService.getPacienteById(id);
        if (pacienteOpt.isPresent()) {
            Paciente paciente = pacienteOpt.get();
            String valorAnterior = paciente.toString();

            paciente.setNombresApellidos(pacienteDTO.getNombresApellidos());
            paciente.setCiudad(pacienteDTO.getCiudad());
            // ... actualiza otros campos ...

            Paciente updatedPaciente = pacienteService.saveOrUpdate(paciente);
            logService.logChange("Paciente", updatedPaciente.getId().longValue(), "UPDATE", valorAnterior, updatedPaciente.toString());
            return ResponseEntity.ok(updatedPaciente);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/buscar")
    public ResponseEntity<List<Paciente>> buscarPaciente(@RequestParam("search") String search) {
        List<Paciente> pacientes = pacienteService.searchPacientes(search);
        return ResponseEntity.ok(pacientes);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Paciente> deletePaciente(@PathVariable Integer id) {
        Optional<Paciente> pacienteOpt = pacienteService.getPacienteById(id);
        if (pacienteOpt.isPresent()) {
            Paciente paciente = pacienteOpt.get();
            pacienteService.deletePaciente(id);
            logService.logChange("Paciente", id.longValue(), "DELETE", paciente.toString(), null);
            return ResponseEntity.ok(paciente);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
