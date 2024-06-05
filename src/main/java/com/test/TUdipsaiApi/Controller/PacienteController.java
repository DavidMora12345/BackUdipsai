package com.test.TUdipsaiApi.Controller;

import com.test.TUdipsaiApi.Model.Paciente;
import com.test.TUdipsaiApi.Service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.test.TUdipsaiApi.Service.ExcelService;
import java.util.List;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/pacientes")
@CrossOrigin(origins = "*")
public class PacienteController {

    @Autowired
    private ExcelService excelService;

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
    public Paciente crearOActualizarPaciente(@RequestBody Paciente paciente) {
        return pacienteService.saveOrUpdate(paciente);
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

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            excelService.savePatientsFromExcel(file);
            return ResponseEntity.ok("Archivo subido y procesado exitosamente.");
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error al procesar el archivo: " + e.getMessage());
        }
    }
}
