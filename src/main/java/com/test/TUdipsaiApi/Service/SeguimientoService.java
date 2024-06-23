package com.test.TUdipsaiApi.Service;

import com.test.TUdipsaiApi.Model.*;
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

    public Seguimiento saveSeguimiento(Seguimiento seguimiento) {
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
            if (seguimiento.getPaciente().getInstitucionEducativa() != null) {
                InstitucionEducativaDTO institucionEducativaDTO = new InstitucionEducativaDTO();
                institucionEducativaDTO.setId(seguimiento.getPaciente().getInstitucionEducativa().getId());
                institucionEducativaDTO.setNombreInstitucion(seguimiento.getPaciente().getInstitucionEducativa().getNombreInstitucion());
                institucionEducativaDTO.setDireccion(seguimiento.getPaciente().getInstitucionEducativa().getDireccion());
                institucionEducativaDTO.setTipoInstitucion(seguimiento.getPaciente().getInstitucionEducativa().getTipoInstitucion());
                institucionEducativaDTO.setInstitucionEstado(seguimiento.getPaciente().getInstitucionEducativa().getInstitucionEstado());
                pacienteDTO.setInstitucionEducativa(institucionEducativaDTO);
            }
            pacienteDTO.setProyecto(seguimiento.getPaciente().getProyecto());
            if (seguimiento.getPaciente().getJornada() != null) {
                JornadaDTO jornadaDTO = new JornadaDTO();
                jornadaDTO.setId(seguimiento.getPaciente().getJornada().getId());
                jornadaDTO.setNombreJornada(seguimiento.getPaciente().getJornada().getNombreJornada());
                jornadaDTO.setEstadoJornada(seguimiento.getPaciente().getJornada().getEstadoJornada());
                pacienteDTO.setJornada(jornadaDTO);
            }
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

    public Seguimiento convertToEntity(SeguimientoDTO seguimientoDTO) {
        Seguimiento seguimiento = new Seguimiento();
        seguimiento.setId(seguimientoDTO.getId());
        seguimiento.setFecha(seguimientoDTO.getFecha());
        seguimiento.setObservacion(seguimientoDTO.getObservacion());
        seguimiento.setEstado(seguimientoDTO.getEstado());

        if (seguimientoDTO.getEspecialista() != null) {
            Especialistas especialista = new Especialistas();
            especialista.setCedula(seguimientoDTO.getEspecialista().getCedula());
            especialista.setEspecialistaEstado(seguimientoDTO.getEspecialista().getEspecialistaEstado());
            especialista.setPrimerNombre(seguimientoDTO.getEspecialista().getPrimerNombre());
            especialista.setSegundoNombre(seguimientoDTO.getEspecialista().getSegundoNombre());
            especialista.setPrimerApellido(seguimientoDTO.getEspecialista().getPrimerApellido());
            especialista.setSegundoApellido(seguimientoDTO.getEspecialista().getSegundoApellido());
            if (seguimientoDTO.getEspecialista().getEspecialidad() != null) {
                Especialidad especialidad = new Especialidad();
                especialidad.setId(seguimientoDTO.getEspecialista().getEspecialidad().getId());
                especialidad.setArea(seguimientoDTO.getEspecialista().getEspecialidad().getArea());
                especialista.setEspecialidad(especialidad);
            }
            especialista.setEsPasante(seguimientoDTO.getEspecialista().getEsPasante());
            especialista.setEspecialistaAsignado(seguimientoDTO.getEspecialista().getEspecialistaAsignado());
            especialista.setInicioPasantia(seguimientoDTO.getEspecialista().getInicioPasantia());
            especialista.setFinPasantia(seguimientoDTO.getEspecialista().getFinPasantia());
            especialista.setImagen(seguimientoDTO.getEspecialista().getImagen());
            seguimiento.setEspecialista(especialista);
        }

        if (seguimientoDTO.getPaciente() != null) {
            Paciente paciente = new Paciente();
            paciente.setId(seguimientoDTO.getPaciente().getId());
            paciente.setFechaApertura(seguimientoDTO.getPaciente().getFechaApertura());
            paciente.setPacienteEstado(seguimientoDTO.getPaciente().getPacienteEstado());
            paciente.setNombresApellidos(seguimientoDTO.getPaciente().getNombresApellidos());
            paciente.setCiudad(seguimientoDTO.getPaciente().getCiudad());
            paciente.setFechaNacimiento(seguimientoDTO.getPaciente().getFechaNacimiento());
            paciente.setEdad(seguimientoDTO.getPaciente().getEdad());
            paciente.setCedula(seguimientoDTO.getPaciente().getCedula());
            paciente.setDomicilio(seguimientoDTO.getPaciente().getDomicilio());
            paciente.setImagen(seguimientoDTO.getPaciente().getImagen());
            paciente.setTelefono(seguimientoDTO.getPaciente().getTelefono());
            paciente.setCelular(seguimientoDTO.getPaciente().getCelular());
            if (seguimientoDTO.getPaciente().getInstitucionEducativa() != null) {
                InstitucionEducativa institucionEducativa = new InstitucionEducativa();
                institucionEducativa.setId(seguimientoDTO.getPaciente().getInstitucionEducativa().getId());
                institucionEducativa.setNombreInstitucion(seguimientoDTO.getPaciente().getInstitucionEducativa().getNombreInstitucion());
                institucionEducativa.setDireccion(seguimientoDTO.getPaciente().getInstitucionEducativa().getDireccion());
                institucionEducativa.setTipoInstitucion(seguimientoDTO.getPaciente().getInstitucionEducativa().getTipoInstitucion());
                institucionEducativa.setInstitucionEstado(seguimientoDTO.getPaciente().getInstitucionEducativa().getInstitucionEstado());
                paciente.setInstitucionEducativa(institucionEducativa);
            }
            paciente.setProyecto(seguimientoDTO.getPaciente().getProyecto());
            if (seguimientoDTO.getPaciente().getJornada() != null) {
                Jornada jornada = new Jornada();
                jornada.setId(seguimientoDTO.getPaciente().getJornada().getId());
                jornada.setNombreJornada(seguimientoDTO.getPaciente().getJornada().getNombreJornada());
                jornada.setEstadoJornada(seguimientoDTO.getPaciente().getJornada().getEstadoJornada());
                paciente.setJornada(jornada);
            }
            paciente.setAnioEducacion(seguimientoDTO.getPaciente().getAnioEducacion());
            paciente.setParalelo(seguimientoDTO.getPaciente().getParalelo());
            paciente.setPerteneceInclusion(seguimientoDTO.getPaciente().getPerteneceInclusion());
            paciente.setTieneDiscapacidad(seguimientoDTO.getPaciente().getTieneDiscapacidad());
            paciente.setPortadorCarnet(seguimientoDTO.getPaciente().isPortadorCarnet());
            paciente.setDiagnostico(seguimientoDTO.getPaciente().getDiagnostico());
            paciente.setMotivoConsulta(seguimientoDTO.getPaciente().getMotivoConsulta());
            paciente.setObservaciones(seguimientoDTO.getPaciente().getObservaciones());
            paciente.setTipoDiscapacidad(seguimientoDTO.getPaciente().getTipoDiscapacidad());
            paciente.setDetalleDiscapacidad(seguimientoDTO.getPaciente().getDetalleDiscapacidad());
            paciente.setPorcentajeDiscapacidad(seguimientoDTO.getPaciente().getPorcentajeDiscapacidad());
            paciente.setPerteneceAProyecto(seguimientoDTO.getPaciente().getPerteneceAProyecto());
            seguimiento.setPaciente(paciente);
        }

        return seguimiento;
    }
}
