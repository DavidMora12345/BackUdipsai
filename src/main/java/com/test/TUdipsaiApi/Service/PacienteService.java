package com.test.TUdipsaiApi.Service;

import com.test.TUdipsaiApi.Model.*;
import com.test.TUdipsaiApi.Repository.InstitucionEducativaRepositorio;
import com.test.TUdipsaiApi.Repository.JornadaRepositorio;
import com.test.TUdipsaiApi.Repository.PacienteRepositorio;
import com.test.TUdipsaiApi.dto.PacienteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepositorio pacienteRepositorio;

    @Autowired
    private InstitucionEducativaRepositorio institucionEducativaRepositorio;

    @Autowired
    private JornadaRepositorio jornadaRepositorio;

    public Optional<Paciente> getPacienteById(Integer id) {
        return pacienteRepositorio.findById(id);
    }

    public Paciente saveOrUpdate(Paciente paciente) {
        return pacienteRepositorio.save(paciente);
    }

    public Paciente convertToEntity(PacienteDTO pacienteDTO) {
        Paciente paciente = new Paciente();
        paciente.setFechaApertura(pacienteDTO.getFechaApertura());
        paciente.setPacienteEstado(pacienteDTO.getPacienteEstado());
        paciente.setNombresApellidos(pacienteDTO.getNombresApellidos());
        paciente.setCiudad(pacienteDTO.getCiudad());
        paciente.setFechaNacimiento(pacienteDTO.getFechaNacimiento());
        paciente.setEdad(pacienteDTO.getEdad());
        paciente.setCedula(pacienteDTO.getCedula());
        paciente.setDomicilio(pacienteDTO.getDomicilio());
        paciente.setImagen(pacienteDTO.getImagen());
        paciente.setTelefono(pacienteDTO.getTelefono());
        paciente.setCelular(pacienteDTO.getCelular());
        paciente.setProyecto(pacienteDTO.getProyecto());
        paciente.setAnioEducacion(pacienteDTO.getAnioEducacion());
        paciente.setParalelo(pacienteDTO.getParalelo());
        paciente.setPerteneceInclusion(pacienteDTO.getPerteneceInclusion());
        paciente.setTieneDiscapacidad(pacienteDTO.getTieneDiscapacidad());
        paciente.setPortadorCarnet(pacienteDTO.isPortadorCarnet());
        paciente.setDiagnostico(pacienteDTO.getDiagnostico());
        paciente.setMotivoConsulta(pacienteDTO.getMotivoConsulta());
        paciente.setObservaciones(pacienteDTO.getObservaciones());
        paciente.setTipoDiscapacidad(pacienteDTO.getTipoDiscapacidad());
        paciente.setDetalleDiscapacidad(pacienteDTO.getDetalleDiscapacidad());
        paciente.setPorcentajeDiscapacidad(pacienteDTO.getPorcentajeDiscapacidad());
        paciente.setPerteneceAProyecto(pacienteDTO.getPerteneceAProyecto());

        if (pacienteDTO.getInstitucionEducativa() != null) {
            InstitucionEducativa institucion = institucionEducativaRepositorio.findById(pacienteDTO.getInstitucionEducativa())
                    .orElseThrow(() -> new RuntimeException("Institucion Educativa not found"));
            paciente.setInstitucionEducativa(institucion);
        }

        if (pacienteDTO.getJornada() != null) {
            Jornada jornada = jornadaRepositorio.findById(pacienteDTO.getJornada())
                    .orElseThrow(() -> new RuntimeException("Jornada not found"));
            paciente.setJornada(jornada);
        }

        return paciente;
    }

    public PacienteDTO convertToDTO(Paciente paciente) {
        PacienteDTO pacienteDTO = new PacienteDTO();
        pacienteDTO.setId(paciente.getId());
        pacienteDTO.setFechaApertura(paciente.getFechaApertura());
        pacienteDTO.setPacienteEstado(paciente.getPacienteEstado());
        pacienteDTO.setNombresApellidos(paciente.getNombresApellidos());
        pacienteDTO.setCiudad(paciente.getCiudad());
        pacienteDTO.setFechaNacimiento(paciente.getFechaNacimiento());
        pacienteDTO.setEdad(paciente.getEdad());
        pacienteDTO.setCedula(paciente.getCedula());
        pacienteDTO.setDomicilio(paciente.getDomicilio());
        pacienteDTO.setImagen(paciente.getImagen());
        pacienteDTO.setTelefono(paciente.getTelefono());
        pacienteDTO.setCelular(paciente.getCelular());

        if (paciente.getInstitucionEducativa() != null) {
            pacienteDTO.setInstitucionEducativa(paciente.getInstitucionEducativa().getId());
        }

        if (paciente.getJornada() != null) {
            pacienteDTO.setJornada(paciente.getJornada().getId());
        }

        pacienteDTO.setProyecto(paciente.getProyecto());
        pacienteDTO.setAnioEducacion(paciente.getAnioEducacion());
        pacienteDTO.setParalelo(paciente.getParalelo());
        pacienteDTO.setPerteneceInclusion(paciente.getPerteneceInclusion());
        pacienteDTO.setTieneDiscapacidad(paciente.getTieneDiscapacidad());
        pacienteDTO.setPortadorCarnet(paciente.isPortadorCarnet());
        pacienteDTO.setDiagnostico(paciente.getDiagnostico());
        pacienteDTO.setMotivoConsulta(paciente.getMotivoConsulta());
        pacienteDTO.setObservaciones(paciente.getObservaciones());
        pacienteDTO.setTipoDiscapacidad(paciente.getTipoDiscapacidad());
        pacienteDTO.setDetalleDiscapacidad(paciente.getDetalleDiscapacidad());
        pacienteDTO.setPorcentajeDiscapacidad(paciente.getPorcentajeDiscapacidad());
        pacienteDTO.setPerteneceAProyecto(paciente.getPerteneceAProyecto());

        return pacienteDTO;
    }

    public Paciente updatePaciente(Integer id, PacienteDTO pacienteDTO) {
        Optional<Paciente> pacienteOptional = pacienteRepositorio.findById(id);
        if (pacienteOptional.isPresent()) {
            Paciente paciente = pacienteOptional.get();

            paciente.setFechaApertura(pacienteDTO.getFechaApertura());
            paciente.setPacienteEstado(pacienteDTO.getPacienteEstado());
            paciente.setNombresApellidos(pacienteDTO.getNombresApellidos());
            paciente.setCiudad(pacienteDTO.getCiudad());
            paciente.setFechaNacimiento(pacienteDTO.getFechaNacimiento());
            paciente.setEdad(pacienteDTO.getEdad());
            paciente.setCedula(pacienteDTO.getCedula());
            paciente.setDomicilio(pacienteDTO.getDomicilio());
            paciente.setImagen(pacienteDTO.getImagen());
            paciente.setTelefono(pacienteDTO.getTelefono());
            paciente.setCelular(pacienteDTO.getCelular());
            paciente.setProyecto(pacienteDTO.getProyecto());
            paciente.setAnioEducacion(pacienteDTO.getAnioEducacion());
            paciente.setParalelo(pacienteDTO.getParalelo());
            paciente.setPerteneceInclusion(pacienteDTO.getPerteneceInclusion());
            paciente.setTieneDiscapacidad(pacienteDTO.getTieneDiscapacidad());
            paciente.setPortadorCarnet(pacienteDTO.isPortadorCarnet());
            paciente.setDiagnostico(pacienteDTO.getDiagnostico());
            paciente.setMotivoConsulta(pacienteDTO.getMotivoConsulta());
            paciente.setObservaciones(pacienteDTO.getObservaciones());
            paciente.setTipoDiscapacidad(pacienteDTO.getTipoDiscapacidad());
            paciente.setDetalleDiscapacidad(pacienteDTO.getDetalleDiscapacidad());
            paciente.setPorcentajeDiscapacidad(pacienteDTO.getPorcentajeDiscapacidad());
            paciente.setPerteneceAProyecto(pacienteDTO.getPerteneceAProyecto());

            if (pacienteDTO.getInstitucionEducativa() != null) {
                InstitucionEducativa institucion = institucionEducativaRepositorio.findById(pacienteDTO.getInstitucionEducativa())
                        .orElseThrow(() -> new RuntimeException("Institucion Educativa not found"));
                paciente.setInstitucionEducativa(institucion);
            } else {
                paciente.setInstitucionEducativa(null);
            }

            if (pacienteDTO.getJornada() != null) {
                Jornada jornada = jornadaRepositorio.findById(pacienteDTO.getJornada())
                        .orElseThrow(() -> new RuntimeException("Jornada not found"));
                paciente.setJornada(jornada);
            } else {
                paciente.setJornada(null);
            }

            return pacienteRepositorio.save(paciente);
        } else {
            return null;
        }
    }

    public Optional<Paciente> deletePaciente(Integer id) {
        Optional<Paciente> optionalPaciente = pacienteRepositorio.findById(id);
        if (optionalPaciente.isPresent()) {
            Paciente paciente = optionalPaciente.get();
            paciente.setPacienteEstado(0);
            pacienteRepositorio.save(paciente);
            return Optional.of(paciente);
        } else {
            return Optional.empty();
        }
    }

    public List<Paciente> searchPacientes(String busqueda) {
        Pageable pageable = PageRequest.of(0, 100);
        return pacienteRepositorio.searchPacientes(busqueda, pageable);
    }

    public List<Paciente> getAllPacientes() {
        return pacienteRepositorio.findByPacienteEstado(1);
    }
}
