package com.test.TUdipsaiApi.Controller;

import com.test.TUdipsaiApi.Model.Especialistas;
import com.test.TUdipsaiApi.Model.Paciente;
import com.test.TUdipsaiApi.Model.Seguimiento;
import com.test.TUdipsaiApi.Service.SeguimientoService;
import com.test.TUdipsaiApi.dto.SeguimientoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/seguimientos")
@CrossOrigin(origins = "*")
public class SeguimientoController {

    @Autowired
    private SeguimientoService seguimientoService;

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

        return entity;
    }
}
