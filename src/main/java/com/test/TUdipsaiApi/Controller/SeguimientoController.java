package com.test.TUdipsaiApi.Controller;

import com.test.TUdipsaiApi.Model.Especialidad;
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
        if (seguimiento.isPresent()) {
            return ResponseEntity.ok(seguimiento.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/paciente/{pacienteId}")
    public List<SeguimientoDTO> getSeguimientosByPacienteId(@PathVariable Integer pacienteId) {
        return seguimientoService.getSeguimientosByPacienteId(pacienteId);
    }

    @PostMapping
    public Seguimiento createSeguimiento(@RequestBody Seguimiento seguimiento) {
        return seguimientoService.saveSeguimiento(seguimiento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SeguimientoDTO> updateSeguimiento(@PathVariable int id, @RequestBody Seguimiento seguimientoDetails) {
        Optional<Seguimiento> optionalSeguimiento = seguimientoService.getSeguimientoById(id).map(this::convertToEntity);
        if (optionalSeguimiento.isPresent()) {
            Seguimiento seguimiento = optionalSeguimiento.get();
            seguimiento.setEspecialista(seguimientoDetails.getEspecialista());
            seguimiento.setPaciente(seguimientoDetails.getPaciente());
            seguimiento.setFecha(seguimientoDetails.getFecha());
            seguimiento.setObservacion(seguimientoDetails.getObservacion());
            final Seguimiento updatedSeguimiento = seguimientoService.saveSeguimiento(seguimiento);
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
        especialista.setEspecialistaEstado(dto.getEspecialista().getEspecialistaEstado());
        especialista.setPrimerNombre(dto.getEspecialista().getPrimerNombre());
        especialista.setSegundoNombre(dto.getEspecialista().getSegundoNombre());
        especialista.setPrimerApellido(dto.getEspecialista().getPrimerApellido());
        especialista.setSegundoApellido(dto.getEspecialista().getSegundoApellido());
        especialista.setEspecialistaAsignado(dto.getEspecialista().getEspecialistaAsignado());
        especialista.setInicioPasantia(dto.getEspecialista().getInicioPasantia());
        especialista.setFinPasantia(dto.getEspecialista().getFinPasantia());
        especialista.setImagen(dto.getEspecialista().getImagen());

        if (dto.getEspecialista().getEspecialidad() != null) {
            Especialidad especialidad = new Especialidad();
            especialidad.setId(dto.getEspecialista().getEspecialidad().getId());
            especialidad.setArea(dto.getEspecialista().getEspecialidad().getArea());
            especialista.setEspecialidad(especialidad);
        }

        Paciente paciente = new Paciente();
        paciente.setId(dto.getPaciente().getId());
        paciente.setNombresApellidos(dto.getPaciente().getNombresApellidos());
        paciente.setCedula(dto.getPaciente().getCedula());
        paciente.setTelefono(dto.getPaciente().getTelefono());
        paciente.setCelular(dto.getPaciente().getCelular());
        paciente.setCiudad(dto.getPaciente().getCiudad());

        entity.setEspecialista(especialista);
        entity.setPaciente(paciente);

        return entity;
    }
}
