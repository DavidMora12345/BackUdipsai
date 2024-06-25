package com.test.TUdipsaiApi.Controller;

import com.test.TUdipsaiApi.Model.Paciente;
import com.test.TUdipsaiApi.Service.LogService;
import com.test.TUdipsaiApi.Service.PacienteService;
import com.test.TUdipsaiApi.dto.PacienteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<PacienteDTO> getPacienteById(@PathVariable Integer id) {
        Optional<Paciente> detalleEntidad = pacienteService.getPacienteById(id);
        return detalleEntidad.map(paciente -> ResponseEntity.ok(pacienteService.convertToDTO(paciente)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/insertar")
    public ResponseEntity<?> crearOActualizarPaciente(@RequestBody PacienteDTO pacienteDTO) {
        try {
            Paciente paciente = pacienteService.convertToEntity(pacienteDTO);
            Paciente savedPaciente = pacienteService.saveOrUpdate(paciente);
            logService.logChange("Paciente", savedPaciente.getId().longValue(), "INSERT", null, savedPaciente.toString());
            return ResponseEntity.ok(savedPaciente);
        } catch (Exception e) {
            logService.logError("Error al insertar paciente", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al insertar paciente: " + e.getMessage());
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> updatePaciente(@PathVariable Integer id, @RequestBody PacienteDTO pacienteDTO) {
        try {
            Paciente updatedPaciente = pacienteService.updatePaciente(id, pacienteDTO);
            if (updatedPaciente != null) {
                logService.logChange("Paciente", updatedPaciente.getId().longValue(), "UPDATE", null, updatedPaciente.toString());
                return ResponseEntity.ok(updatedPaciente);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente no encontrado con ID: " + id);
            }
        } catch (Exception e) {
            logService.logError("Error al actualizar paciente", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al actualizar paciente: " + e.getMessage());
        }
    }

    @PostMapping("/buscar")
    public ResponseEntity<List<Paciente>> buscarPaciente(@RequestParam("search") String search) {
        List<Paciente> pacientes = pacienteService.searchPacientes(search);
        return ResponseEntity.ok(pacientes);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> deletePaciente(@PathVariable Integer id) {
        try {
            Optional<Paciente> pacienteOpt = pacienteService.getPacienteById(id);
            if (pacienteOpt.isPresent()) {
                Paciente paciente = pacienteOpt.get();
                pacienteService.deletePaciente(id);
                logService.logChange("Paciente", id.longValue(), "DELETE", paciente.toString(), null);
                return ResponseEntity.ok(paciente);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente no encontrado con ID: " + id);
            }
        } catch (Exception e) {
            logService.logError("Error al eliminar paciente", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al eliminar paciente: " + e.getMessage());
        }
    }
}
