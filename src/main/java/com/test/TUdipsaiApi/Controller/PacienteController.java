package com.test.TUdipsaiApi.Controller;

import com.test.TUdipsaiApi.Model.Documento;
import com.test.TUdipsaiApi.Model.Paciente;
import com.test.TUdipsaiApi.Service.DocumentoService;
import com.test.TUdipsaiApi.Service.ExcelService;
import com.test.TUdipsaiApi.Service.LogService;
import com.test.TUdipsaiApi.Service.PacienteService;
import com.test.TUdipsaiApi.dto.PacienteDTO;
import com.test.TUdipsaiApi.dto.PacienteSinImagenDTO;
import com.test.TUdipsaiApi.dto.PacienteUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    private DocumentoService documentoService;

    @Autowired
    private ExcelService excelService;

    @GetMapping("/listar")
    public ResponseEntity<List<PacienteSinImagenDTO>> getAllPacientes() {
        List<PacienteSinImagenDTO> pacientes = pacienteService.getAllPacientesSinImagen();
        return ResponseEntity.ok(pacientes);
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<PacienteDTO> getPacienteById(@PathVariable Integer id) {
        Optional<Paciente> detalleEntidad = pacienteService.getPacienteById(id);
        return detalleEntidad.map(paciente -> ResponseEntity.ok(pacienteService.convertToDTO(paciente)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/insertar")
    public ResponseEntity<?> crearOActualizarPaciente(@RequestBody PacienteUpdateDTO pacienteUpdateDTO) {
        try {
            Paciente paciente = pacienteService.createPaciente(pacienteUpdateDTO);
            logService.logChange("Paciente", paciente.getId().longValue(), "INSERT", null, paciente);
            return ResponseEntity.ok(paciente);
        } catch (Exception e) {
            logService.logError("Error al insertar paciente", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al insertar paciente: " + e.getMessage());
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> updatePaciente(@PathVariable Integer id, @RequestBody PacienteUpdateDTO pacienteUpdateDTO) {
        try {
            Optional<Paciente> existingPaciente = pacienteService.getPacienteById(id);
            if (existingPaciente.isPresent()) {
                Paciente updatedPaciente = pacienteService.updatePaciente(id, pacienteUpdateDTO);
                logService.logChange("Paciente", updatedPaciente.getId().longValue(), "UPDATE", existingPaciente.get(), updatedPaciente);
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
    public ResponseEntity<List<PacienteSinImagenDTO>> buscarPacientes(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "sedeId", required = false) Integer sedeId) {

        List<PacienteSinImagenDTO> pacientes = pacienteService.searchPacientes(search, sedeId);
        return ResponseEntity.ok(pacientes);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> deletePaciente(@PathVariable Integer id) {
        try {
            Optional<Paciente> pacienteOpt = pacienteService.getPacienteById(id);
            if (pacienteOpt.isPresent()) {
                Paciente paciente = pacienteOpt.get();
                pacienteService.deletePaciente(id);
                logService.logChange("Paciente", id.longValue(), "DELETE", paciente, null);
                return ResponseEntity.ok(paciente);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente no encontrado con ID: " + id);
            }
        } catch (Exception e) {
            logService.logError("Error al eliminar paciente", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al eliminar paciente: " + e.getMessage());
        }
    }

    @PostMapping("/{id}/documento")
    public ResponseEntity<?> subirDocumento(@PathVariable Integer id, @RequestParam("file") MultipartFile file) {
        try {
            Optional<Paciente> pacienteOpt = pacienteService.getPacienteById(id);
            if (pacienteOpt.isPresent()) {
                Paciente paciente = pacienteOpt.get();
                Documento documento = documentoService.saveDocumento(file);
                paciente.setFichaDiagnostica(documento);
                pacienteService.saveOrUpdate(paciente);
                return ResponseEntity.ok("Documento subido exitosamente con ID: " + documento.getId());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente no encontrado con ID: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al subir documento: " + e.getMessage());
        }
    }

    @DeleteMapping("/documentos/{pacienteId}")
    public ResponseEntity<?> eliminarDocumentoPorPacienteId(@PathVariable Integer pacienteId) {
        try {
            Optional<Paciente> pacienteOpt = pacienteService.getPacienteById(pacienteId);
            if (pacienteOpt.isPresent()) {
                Paciente paciente = pacienteOpt.get();
                Documento documento = paciente.getFichaDiagnostica();
                if (documento != null) {
                    paciente.setFichaDiagnostica(null);
                    pacienteService.saveOrUpdate(paciente);
                    documentoService.deleteDocumento(documento.getId());
                    return ResponseEntity.ok("Documento eliminado exitosamente");
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El paciente con ID: " + pacienteId + " no tiene documento asociado");
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente no encontrado con ID: " + pacienteId);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al eliminar documento: " + e.getMessage());
        }
    }

    @GetMapping("/{id}/documento")
    public ResponseEntity<?> listarDocumentos(@PathVariable Integer id) {
        try {
            Optional<Paciente> pacienteOpt = pacienteService.getPacienteById(id);
            if (pacienteOpt.isPresent()) {
                Paciente paciente = pacienteOpt.get();
                Documento documento = paciente.getFichaDiagnostica();
                if (documento != null) {
                    return ResponseEntity.ok(documento.getId());
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay documentos para el paciente con ID: " + id);
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente no encontrado con ID: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al listar documentos: " + e.getMessage());
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<?> subirPacientesExcel(@RequestParam("file") MultipartFile file) {
        try {
            List<String> messages = excelService.savePatientsFromExcel(file);
            return ResponseEntity.ok(messages);
        } catch (Exception e) {
            logService.logError("Error al subir pacientes desde Excel", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al subir pacientes desde Excel: " + e.getMessage());
        }
    }

    @PostMapping("/{id}/fichaCompromiso")
    public ResponseEntity<?> subirFichaCompromiso(@PathVariable Integer id, @RequestParam("file") MultipartFile file) {
        try {
            Optional<Paciente> pacienteOpt = pacienteService.getPacienteById(id);
            if (pacienteOpt.isPresent()) {
                Paciente paciente = pacienteOpt.get();
                Documento documento = documentoService.saveDocumento(file);
                paciente.setFichaCompromiso(documento);
                pacienteService.saveOrUpdate(paciente);
                return ResponseEntity.ok("Ficha compromiso subida exitosamente con ID: " + documento.getId());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente no encontrado con ID: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al subir ficha compromiso: " + e.getMessage());
        }
    }

    @DeleteMapping("/fichaCompromiso/{pacienteId}")
    public ResponseEntity<?> eliminarFichaCompromisoPorPacienteId(@PathVariable Integer pacienteId) {
        try {
            Optional<Paciente> pacienteOpt = pacienteService.getPacienteById(pacienteId);
            if (pacienteOpt.isPresent()) {
                Paciente paciente = pacienteOpt.get();
                Documento documento = paciente.getFichaCompromiso();
                if (documento != null) {
                    paciente.setFichaCompromiso(null);
                    pacienteService.saveOrUpdate(paciente);
                    documentoService.deleteDocumento(documento.getId());
                    return ResponseEntity.ok("Ficha compromiso eliminada exitosamente");
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El paciente con ID: " + pacienteId + " no tiene ficha compromiso asociada");
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente no encontrado con ID: " + pacienteId);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al eliminar ficha compromiso: " + e.getMessage());
        }
    }

    @GetMapping("/{id}/fichaCompromiso")
    public ResponseEntity<?> listarFichaCompromiso(@PathVariable Integer id) {
        try {
            Optional<Paciente> pacienteOpt = pacienteService.getPacienteById(id);
            if (pacienteOpt.isPresent()) {
                Paciente paciente = pacienteOpt.get();
                Documento documento = paciente.getFichaCompromiso();
                if (documento != null) {
                    return ResponseEntity.ok(documento.getId());
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay ficha compromiso para el paciente con ID: " + id);
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Paciente no encontrado con ID: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al listar ficha compromiso: " + e.getMessage());
        }
    }
}
