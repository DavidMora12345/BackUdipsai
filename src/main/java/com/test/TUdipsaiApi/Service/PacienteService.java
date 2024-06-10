package com.test.TUdipsaiApi.Service;

import com.test.TUdipsaiApi.Model.InstitucionEducativa;
import com.test.TUdipsaiApi.Model.Jornada;
import com.test.TUdipsaiApi.Model.Paciente;
import com.test.TUdipsaiApi.Repository.InstitucionEducativaRepositorio;
import com.test.TUdipsaiApi.Repository.JornadaRepositorio;
import com.test.TUdipsaiApi.Repository.PacienteRepositorio;
import com.test.TUdipsaiApi.dto.PacienteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        paciente.setId(pacienteDTO.getId());
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

        if (pacienteDTO.getInstitucionEducativa() != null) {
            Optional<InstitucionEducativa> institucion = institucionEducativaRepositorio.findById(pacienteDTO.getInstitucionEducativa());
            institucion.ifPresent(paciente::setInstitucionEducativa);
        }
        if (pacienteDTO.getJornada() != null) {
            Optional<Jornada> jornada = jornadaRepositorio.findById(pacienteDTO.getJornada());
            jornada.ifPresent(paciente::setJornada);
        }

        return paciente;
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

            if (pacienteDTO.getInstitucionEducativa() != null) {
                Optional<InstitucionEducativa> institucion = institucionEducativaRepositorio.findById(pacienteDTO.getInstitucionEducativa());
                institucion.ifPresent(paciente::setInstitucionEducativa);
            }

            if (pacienteDTO.getJornada() != null) {
                Optional<Jornada> jornada = jornadaRepositorio.findById(pacienteDTO.getJornada());
                jornada.ifPresent(paciente::setJornada);
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

    public void setPacienteRepositorio(PacienteRepositorio pacienteRepositorio) {
        this.pacienteRepositorio = pacienteRepositorio;
    }
}
