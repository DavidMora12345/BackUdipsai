package com.test.TUdipsaiApi.Service;

import com.test.TUdipsaiApi.Model.Seguimiento;
import com.test.TUdipsaiApi.Model.Especialistas;
import com.test.TUdipsaiApi.Model.Paciente;
import com.test.TUdipsaiApi.Repository.SeguimientoRepositorio;
import com.test.TUdipsaiApi.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SeguimientoService {

    @Autowired
    private SeguimientoRepositorio seguimientoRepository;

    public List<SeguimientoDTO> getAllSeguimientos() {
        return seguimientoRepository.findByEstado(1).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<SeguimientoDTO> getSeguimientoById(int id) {
        return seguimientoRepository.findById(id).map(this::convertToDTO);
    }

    public Seguimiento saveSeguimiento(SeguimientoDTO seguimientoDTO) {
        Seguimiento seguimiento = convertToEntity(seguimientoDTO);
        return seguimientoRepository.save(seguimiento);
    }

    public void deleteSeguimiento(int id) {
        Optional<Seguimiento> optionalSeguimiento = seguimientoRepository.findById(id);
        if (optionalSeguimiento.isPresent()) {
            Seguimiento seguimiento = optionalSeguimiento.get();
            seguimiento.setEstado(0); // Cambiar estado a inactivo
            seguimientoRepository.save(seguimiento);
        }
    }

    public List<SeguimientoDTO> getSeguimientosByPacienteId(Integer pacienteId) {
        return seguimientoRepository.findByPacienteIdAndEstado(pacienteId, 1).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public SeguimientoDTO convertToDTO(Seguimiento seguimiento) {
        SeguimientoDTO seguimientoDTO = new SeguimientoDTO();
        seguimientoDTO.setId(seguimiento.getId());
        seguimientoDTO.setFecha(seguimiento.getFecha());
        seguimientoDTO.setObservacion(seguimiento.getObservacion());
        seguimientoDTO.setEstado(seguimiento.getEstado());

        if (seguimiento.getEspecialista() != null) {
            EspecialistasDTO especialistaDTO = new EspecialistasDTO();
            especialistaDTO.setCedula(seguimiento.getEspecialista().getCedula());
            especialistaDTO.setEspecialistaEstado(seguimiento.getEspecialista().getEspecialistaEstado());
            especialistaDTO.setPrimerNombre(seguimiento.getEspecialista().getPrimerNombre());
            especialistaDTO.setSegundoNombre(seguimiento.getEspecialista().getSegundoNombre());
            especialistaDTO.setPrimerApellido(seguimiento.getEspecialista().getPrimerApellido());
            especialistaDTO.setSegundoApellido(seguimiento.getEspecialista().getSegundoApellido());
            if (seguimiento.getEspecialista().getEspecialidad() != null) {
                EspecialidadDTO especialidadDTO = new EspecialidadDTO();
                especialidadDTO.setId(seguimiento.getEspecialista().getEspecialidad().getId());
                especialidadDTO.setArea(seguimiento.getEspecialista().getEspecialidad().getArea());
                especialistaDTO.setEspecialidad(especialidadDTO);
            }
            especialistaDTO.setEsPasante(seguimiento.getEspecialista().getEsPasante());
            especialistaDTO.setEspecialistaAsignado(seguimiento.getEspecialista().getEspecialistaAsignado());
            especialistaDTO.setInicioPasantia(seguimiento.getEspecialista().getInicioPasantia());
            especialistaDTO.setFinPasantia(seguimiento.getEspecialista().getFinPasantia());
            especialistaDTO.setImagen(seguimiento.getEspecialista().getImagen());
            seguimientoDTO.setEspecialista(especialistaDTO);
        }

        if (seguimiento.getPaciente() != null) {
            PacienteDTO pacienteDTO = new PacienteDTO();
            pacienteDTO.setId(seguimiento.getPaciente().getId());
            pacienteDTO.setFechaApertura(seguimiento.getPaciente().getFechaApertura());
            pacienteDTO.setPacienteEstado(seguimiento.getPaciente().getPacienteEstado());
            pacienteDTO.setNombresApellidos(seguimiento.getPaciente().getNombresApellidos());
            pacienteDTO.setCiudad(seguimiento.getPaciente().getCiudad());
            pacienteDTO.setFechaNacimiento(seguimiento.getPaciente().getFechaNacimiento());
            pacienteDTO.setEdad(seguimiento.getPaciente().getEdad());
            pacienteDTO.setCedula(seguimiento.getPaciente().getCedula());
            pacienteDTO.setDomicilio(seguimiento.getPaciente().getDomicilio());
            pacienteDTO.setImagen(seguimiento.getPaciente().getImagen());
            pacienteDTO.setTelefono(seguimiento.getPaciente().getTelefono());
            pacienteDTO.setCelular(seguimiento.getPaciente().getCelular());
            pacienteDTO.setProyecto(seguimiento.getPaciente().getProyecto());
            pacienteDTO.setAnioEducacion(seguimiento.getPaciente().getAnioEducacion());
            pacienteDTO.setParalelo(seguimiento.getPaciente().getParalelo());
            pacienteDTO.setPerteneceInclusion(seguimiento.getPaciente().getPerteneceInclusion());
            pacienteDTO.setTieneDiscapacidad(seguimiento.getPaciente().getTieneDiscapacidad());
            pacienteDTO.setPortadorCarnet(seguimiento.getPaciente().isPortadorCarnet());
            pacienteDTO.setDiagnostico(seguimiento.getPaciente().getDiagnostico());
            pacienteDTO.setMotivoConsulta(seguimiento.getPaciente().getMotivoConsulta());
            pacienteDTO.setObservaciones(seguimiento.getPaciente().getObservaciones());
            pacienteDTO.setTipoDiscapacidad(seguimiento.getPaciente().getTipoDiscapacidad());
            pacienteDTO.setDetalleDiscapacidad(seguimiento.getPaciente().getDetalleDiscapacidad());
            pacienteDTO.setPorcentajeDiscapacidad(seguimiento.getPaciente().getPorcentajeDiscapacidad());
            pacienteDTO.setPerteneceAProyecto(seguimiento.getPaciente().getPerteneceAProyecto());

            seguimientoDTO.setPaciente(pacienteDTO);
        }

        return seguimientoDTO;
    }

    private Seguimiento convertToEntity(SeguimientoDTO seguimientoDTO) {
        Seguimiento seguimiento = new Seguimiento();
        seguimiento.setId(seguimientoDTO.getId());
        seguimiento.setFecha(seguimientoDTO.getFecha());
        seguimiento.setObservacion(seguimientoDTO.getObservacion());
        seguimiento.setEstado(seguimientoDTO.getEstado());

        if (seguimientoDTO.getEspecialista() != null) {
            Especialistas especialista = new Especialistas();
            especialista.setCedula(seguimientoDTO.getEspecialista().getCedula());
            seguimiento.setEspecialista(especialista);
        }

        if (seguimientoDTO.getPaciente() != null) {
            Paciente paciente = new Paciente();
            paciente.setId(seguimientoDTO.getPaciente().getId());
            seguimiento.setPaciente(paciente);
        }

        return seguimiento;
    }
}
