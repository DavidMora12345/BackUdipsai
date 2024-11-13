package com.test.TUdipsaiApi.Controller;

import com.test.TUdipsaiApi.Model.Documento;
import com.test.TUdipsaiApi.Model.Especialistas;
import com.test.TUdipsaiApi.Model.Paciente;
import com.test.TUdipsaiApi.Model.Seguimiento;
import com.test.TUdipsaiApi.Service.DocumentoService;
import com.test.TUdipsaiApi.Service.SeguimientoService;
import com.test.TUdipsaiApi.dto.SeguimientoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/seguimientos")
@CrossOrigin(origins = "*")
public class SeguimientoController {

    @Autowired
    private SeguimientoService seguimientoService;

    @Autowired
    private DocumentoService documentoService;

    @GetMapping
    public List<SeguimientoDTO> getAllSeguimientos() {
        return seguimientoService.getAllSeguimientos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeguimientoDTO> getSeguimientoById(@PathVariable int id) {
        Optional<SeguimientoDTO> seguimiento = seguimientoService.getSeguimientoById(id);
        return seguimiento.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/paciente/{pacienteId}")
    public List<SeguimientoDTO> getSeguimientosByPacienteId(@PathVariable Integer pacienteId) {
        return seguimientoService.getSeguimientosByPacienteId(pacienteId);
    }

    @PostMapping
    public ResponseEntity<Seguimiento> createSeguimiento(@RequestBody SeguimientoDTO seguimientoDTO) {
        Seguimiento savedSeguimiento = seguimientoService.saveSeguimiento(seguimientoDTO);
        return ResponseEntity.ok(savedSeguimiento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SeguimientoDTO> updateSeguimiento(@PathVariable int id, @RequestBody SeguimientoDTO seguimientoDetails) {
        Optional<Seguimiento> optionalSeguimiento = seguimientoService.getSeguimientoById(id).map(this::convertToEntity);
        if (optionalSeguimiento.isPresent()) {
            Seguimiento seguimiento = optionalSeguimiento.get();
            seguimiento.setFecha(seguimientoDetails.getFecha());
            seguimiento.setObservacion(seguimientoDetails.getObservacion());
            seguimiento.setEstado(seguimientoDetails.getEstado());
            final Seguimiento updatedSeguimiento = seguimientoService.saveSeguimiento(seguimientoService.convertToDTO(seguimiento));
            return ResponseEntity.ok(seguimientoService.convertToDTO(updatedSeguimiento));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeguimiento(@PathVariable int id) {
        seguimientoService.deleteSeguimiento(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/documento")
    public ResponseEntity<?> subirFichaCompromiso(@PathVariable Integer id, @RequestParam("file") MultipartFile file) {
        try {
            Optional<Seguimiento> seguimientoOpt = seguimientoService.getSeguimientoById(id).map(this::convertToEntity);
            if (seguimientoOpt.isPresent()) {
                Seguimiento seguimiento = seguimientoOpt.get();
                Documento documento = documentoService.saveDocumento(file);
                seguimiento.setDocumento(documento);
                seguimientoService.saveSeguimiento(seguimientoService.convertToDTO(seguimiento));
                return ResponseEntity.ok("Ficha compromiso subida exitosamente con ID: " + documento.getId());
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Seguimiento no encontrado con ID: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al subir ficha compromiso: " + e.getMessage());
        }
    }

    @DeleteMapping("/documento/{seguimientoId}")
    public ResponseEntity<?> eliminarFichaCompromisoPorSeguimientoId(@PathVariable Integer seguimientoId) {
        try {
            Optional<Seguimiento> seguimientoOpt = seguimientoService.getSeguimientoById(seguimientoId).map(this::convertToEntity);
            if (seguimientoOpt.isPresent()) {
                Seguimiento seguimiento = seguimientoOpt.get();
                Documento documento = seguimiento.getDocumento();
                if (documento != null) {
                    seguimiento.setDocumento(null);
                    seguimientoService.saveSeguimiento(seguimientoService.convertToDTO(seguimiento));
                    documentoService.deleteDocumento(documento.getId());
                    return ResponseEntity.ok("Ficha compromiso eliminada exitosamente");
                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El seguimiento con ID: " + seguimientoId + " no tiene ficha compromiso asociada");
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Seguimiento no encontrado con ID: " + seguimientoId);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al eliminar ficha compromiso: " + e.getMessage());
        }
    }

    @GetMapping("/{id}/documento")
    public ResponseEntity<?> listarFichaCompromiso(@PathVariable Integer id) {
        try {
            Optional<Seguimiento> seguimientoOpt = seguimientoService.getSeguimientoById(id).map(this::convertToEntity);
            if (seguimientoOpt.isPresent()) {
                Seguimiento seguimiento = seguimientoOpt.get();
                Documento documento = seguimiento.getDocumento();
                if (documento != null) {
                    return ResponseEntity.ok(documento.getId());
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No hay ficha compromiso para el seguimiento con ID: " + id);
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Seguimiento no encontrado con ID: " + id);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al listar ficha compromiso: " + e.getMessage());
        }
    }

    private Seguimiento convertToEntity(SeguimientoDTO dto) {
        Seguimiento entity = new Seguimiento();
        entity.setId(dto.getId());
        entity.setFecha(dto.getFecha());
        entity.setObservacion(dto.getObservacion());
        entity.setEstado(dto.getEstado());

        Especialistas especialista = new Especialistas();
        especialista.setCedula(dto.getEspecialista().getCedula());
        entity.setEspecialista(especialista);

        Paciente paciente = new Paciente();
        paciente.setId(dto.getPaciente().getId());
        entity.setPaciente(paciente);

        if (dto.getDocumento() != null && dto.getDocumento().getId() != null) {
            Documento documento = documentoService.getDocumentoById(dto.getDocumento().getId())
                    .orElseThrow(() -> new RuntimeException("Documento not found"));
            entity.setDocumento(documento);
        } else {
            entity.setDocumento(null);
        }

        return entity;
    }
}
