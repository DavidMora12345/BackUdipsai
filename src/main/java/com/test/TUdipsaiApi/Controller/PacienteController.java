package com.test.TUdipsaiApi.Controller;

import com.test.TUdipsaiApi.Model.Documento;
import com.test.TUdipsaiApi.Model.Paciente;
import com.test.TUdipsaiApi.Service.*;
import com.test.TUdipsaiApi.dto.PacienteDTO;
import com.test.TUdipsaiApi.dto.PacienteSinImagenDTO;
import com.test.TUdipsaiApi.dto.PacienteUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/api/pacientes")
@CrossOrigin(origins = "*")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @Autowired
    private LogService logService;

    @Autowired
    private ImagenService imagenService;

    @Autowired
    private DocumentoService documentoService;

    @Autowired
    private ExcelService excelService;

    @Autowired
    private ReporteGeneralService reporteGeneralService;

    @Autowired
    private CedulaValidatorService cedulaValidatorService;

    @GetMapping("/listar")
    public ResponseEntity<List<PacienteSinImagenDTO>> getAllPacientes() {
        List<PacienteSinImagenDTO> pacientes = pacienteService.getAllPacientesSinImagen();
        return ResponseEntity.ok(pacientes);
    }

    @GetMapping("/listar/{id}")
    public ResponseEntity<PacienteDTO> getPacienteById(@PathVariable Integer id) throws IOException {
        Optional<Paciente> detalleEntidad = pacienteService.getPacienteById(id);

        return detalleEntidad.map(paciente -> {
                    PacienteDTO pacienteDTO = pacienteService.convertToDTO(paciente);

                    // Recuperar la imagen comprimida desde el archivo y convertirla a Base64 si existe
                    String fileName = "imagen-" + paciente.getNombresApellidos().replace(" ", "_") + "-" + paciente.getCedula();
                    String imagePath = (System.getProperty("os.name").toLowerCase().contains("win") ?
                            "C:/UdipsaiImagenesPacientes/" : System.getProperty("user.home") + "/UdipsaiImagenesPacientes/")
                            + fileName + ".txt.gz";

                    try {
                        String base64Image = imagenService.getDecompressedImage(imagePath); // Recuperar la imagen descomprimida en Base64
                        pacienteDTO.setImagen(base64Image);  // Asignar la imagen en formato Base64 al DTO
                    } catch (FileNotFoundException e) {
                        // No se encontró la imagen, establecer null en el campo de imagen
                        pacienteDTO.setImagen(null);
                    } catch (IOException e) {
                        // En caso de error en la descompresión, se puede retornar un error con un mensaje adecuado
                        return null;  // Retornamos null aquí para indicar que el flujo debe continuar
                    }

                    return pacienteDTO;
                }).map(ResponseEntity::ok) // Transformar el resultado en un ResponseEntity
                .orElseGet(() -> ResponseEntity.notFound().build());
    }



    @PostMapping("/insertar")
    public ResponseEntity<?> createPaciente(@RequestBody PacienteUpdateDTO pacienteUpdateDTO) {
        try {
            PacienteDTO nuevoPaciente = pacienteService.createPaciente(pacienteUpdateDTO);

            // Validar la cédula después de crear el paciente
            boolean cedulaValida = cedulaValidatorService.validarCedulaEcuatoriana(pacienteUpdateDTO.getCedula());

            Map<String, Object> respuesta = new HashMap<>();
            respuesta.put("paciente", nuevoPaciente);

            if (!cedulaValida) {
                respuesta.put("advertencia", "La cédula es inválida, pero el registro se ha realizado.");
            }

            return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }




    @PutMapping("/actualizar/{id}")
    public ResponseEntity<?> updatePaciente(@PathVariable Integer id, @RequestBody PacienteUpdateDTO pacienteUpdateDTO) {
        try {
            Optional<Paciente> existingPaciente = pacienteService.getPacienteById(id);
            if (existingPaciente.isPresent()) {
                Paciente updatedPaciente = pacienteService.updatePaciente(id, pacienteUpdateDTO);
                logService.logChange("Paciente", updatedPaciente.getId().longValue(), "UPDATE", existingPaciente.get(), updatedPaciente);

                // Validar la cédula después de actualizar
                boolean cedulaValida = cedulaValidatorService.validarCedulaEcuatoriana(pacienteUpdateDTO.getCedula());

                Map<String, Object> respuesta = new HashMap<>();
                respuesta.put("paciente", updatedPaciente);

                if (!cedulaValida) {
                    respuesta.put("advertencia", "La cédula es inválida, pero la actualización se ha realizado.");
                }

                return ResponseEntity.ok(respuesta);
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

    @GetMapping("/{id}/reporte-general")
    public ResponseEntity<byte[]> obtenerReporteGeneralPorPacienteId(@PathVariable Integer id) {
        try {
            byte[] pdfContent = reporteGeneralService.generarReporteGeneralPorPacienteId(id);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte_general_paciente_" + id + ".pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfContent);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null); // Devolver un 404 si no se encuentra la información necesaria
        }
    }

}
