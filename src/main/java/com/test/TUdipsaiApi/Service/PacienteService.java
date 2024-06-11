package com.test.TUdipsaiApi.Service;

import com.test.TUdipsaiApi.Model.InstitucionEducativa;
import com.test.TUdipsaiApi.Model.Paciente;
import com.test.TUdipsaiApi.Repository.InstitucionEducativaRepositorio;
import com.test.TUdipsaiApi.Repository.PacienteRepositorio;
import com.test.TUdipsaiApi.dto.PacienteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepositorio pacienteRepositorio;

    @Autowired
    private InstitucionEducativaRepositorio institucionEducativaRepositorio;

    @Autowired
    private HistorialCambiosService historialCambiosService;

    public Optional<Paciente> getPacienteById(Integer id) {
        return pacienteRepositorio.findById(id);
    }

    public Paciente saveOrUpdate(Paciente paciente) {
        return pacienteRepositorio.save(paciente);
    }

    public Paciente convertToEntity(PacienteDTO pacienteDTO) {
        Paciente paciente = new Paciente();
        // Set all fields from PacienteDTO to Paciente entity
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

        return paciente;
    }

    public Paciente updatePaciente(Integer id, PacienteDTO pacienteDTO) {
        Optional<Paciente> pacienteOptional = pacienteRepositorio.findById(id);
        if (pacienteOptional.isPresent()) {
            Paciente paciente = pacienteOptional.get();
            registrarCambios(paciente, pacienteDTO);

            // Update all fields from PacienteDTO to Paciente entity
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

            return pacienteRepositorio.save(paciente);
        } else {
            return null;
        }
    }

    private void registrarCambios(Paciente paciente, PacienteDTO pacienteDTO) {
        if (!paciente.getFechaApertura().equals(pacienteDTO.getFechaApertura())) {
            historialCambiosService.registrarCambio("Paciente", paciente.getId(), "fechaApertura", paciente.getFechaApertura().toString(), pacienteDTO.getFechaApertura().toString());
        }
        if (!paciente.getPacienteEstado().equals(pacienteDTO.getPacienteEstado())) {
            historialCambiosService.registrarCambio("Paciente", paciente.getId(), "pacienteEstado", paciente.getPacienteEstado().toString(), pacienteDTO.getPacienteEstado().toString());
        }
        if (!paciente.getNombresApellidos().equals(pacienteDTO.getNombresApellidos())) {
            historialCambiosService.registrarCambio("Paciente", paciente.getId(), "nombresApellidos", paciente.getNombresApellidos(), pacienteDTO.getNombresApellidos());
        }
        if (!paciente.getCiudad().equals(pacienteDTO.getCiudad())) {
            historialCambiosService.registrarCambio("Paciente", paciente.getId(), "ciudad", paciente.getCiudad(), pacienteDTO.getCiudad());
        }
        if (!paciente.getFechaNacimiento().equals(pacienteDTO.getFechaNacimiento())) {
            historialCambiosService.registrarCambio("Paciente", paciente.getId(), "fechaNacimiento", paciente.getFechaNacimiento().toString(), pacienteDTO.getFechaNacimiento().toString());
        }
        if (!paciente.getEdad().equals(pacienteDTO.getEdad())) {
            historialCambiosService.registrarCambio("Paciente", paciente.getId(), "edad", paciente.getEdad(), pacienteDTO.getEdad());
        }
        if (!paciente.getCedula().equals(pacienteDTO.getCedula())) {
            historialCambiosService.registrarCambio("Paciente", paciente.getId(), "cedula", paciente.getCedula(), pacienteDTO.getCedula());
        }
        if (!paciente.getDomicilio().equals(pacienteDTO.getDomicilio())) {
            historialCambiosService.registrarCambio("Paciente", paciente.getId(), "domicilio", paciente.getDomicilio(), pacienteDTO.getDomicilio());
        }
        if (!paciente.getTelefono().equals(pacienteDTO.getTelefono())) {
            historialCambiosService.registrarCambio("Paciente", paciente.getId(), "telefono", paciente.getTelefono(), pacienteDTO.getTelefono());
        }
        if (!paciente.getCelular().equals(pacienteDTO.getCelular())) {
            historialCambiosService.registrarCambio("Paciente", paciente.getId(), "celular", paciente.getCelular(), pacienteDTO.getCelular());
        }
        if (!paciente.getProyecto().equals(pacienteDTO.getProyecto())) {
            historialCambiosService.registrarCambio("Paciente", paciente.getId(), "proyecto", paciente.getProyecto(), pacienteDTO.getProyecto());
        }
        if (!paciente.getAnioEducacion().equals(pacienteDTO.getAnioEducacion())) {
            historialCambiosService.registrarCambio("Paciente", paciente.getId(), "anioEducacion", paciente.getAnioEducacion(), pacienteDTO.getAnioEducacion());
        }
        if (!paciente.getParalelo().equals(pacienteDTO.getParalelo())) {
            historialCambiosService.registrarCambio("Paciente", paciente.getId(), "paralelo", paciente.getParalelo(), pacienteDTO.getParalelo());
        }
        if (!paciente.getPerteneceInclusion().equals(pacienteDTO.getPerteneceInclusion())) {
            historialCambiosService.registrarCambio("Paciente", paciente.getId(), "perteneceInclusion", paciente.getPerteneceInclusion(), pacienteDTO.getPerteneceInclusion());
        }
        if (!paciente.getTieneDiscapacidad().equals(pacienteDTO.getTieneDiscapacidad())) {
            historialCambiosService.registrarCambio("Paciente", paciente.getId(), "tieneDiscapacidad", paciente.getTieneDiscapacidad(), pacienteDTO.getTieneDiscapacidad());
        }
        if (paciente.isPortadorCarnet() != pacienteDTO.isPortadorCarnet()) {
            historialCambiosService.registrarCambio("Paciente", paciente.getId(), "portadorCarnet", String.valueOf(paciente.isPortadorCarnet()), String.valueOf(pacienteDTO.isPortadorCarnet()));
        }
        if (!paciente.getDiagnostico().equals(pacienteDTO.getDiagnostico())) {
            historialCambiosService.registrarCambio("Paciente", paciente.getId(), "diagnostico", paciente.getDiagnostico(), pacienteDTO.getDiagnostico());
        }
        if (!paciente.getMotivoConsulta().equals(pacienteDTO.getMotivoConsulta())) {
            historialCambiosService.registrarCambio("Paciente", paciente.getId(), "motivoConsulta", paciente.getMotivoConsulta(), pacienteDTO.getMotivoConsulta());
        }
        if (!paciente.getObservaciones().equals(pacienteDTO.getObservaciones())) {
            historialCambiosService.registrarCambio("Paciente", paciente.getId(), "observaciones", paciente.getObservaciones(), pacienteDTO.getObservaciones());
        }
        if (!paciente.getTipoDiscapacidad().equals(pacienteDTO.getTipoDiscapacidad())) {
            historialCambiosService.registrarCambio("Paciente", paciente.getId(), "tipoDiscapacidad", paciente.getTipoDiscapacidad(), pacienteDTO.getTipoDiscapacidad());
        }
        if (!paciente.getDetalleDiscapacidad().equals(pacienteDTO.getDetalleDiscapacidad())) {
            historialCambiosService.registrarCambio("Paciente", paciente.getId(), "detalleDiscapacidad", paciente.getDetalleDiscapacidad(), pacienteDTO.getDetalleDiscapacidad());
        }
        if (!paciente.getPorcentajeDiscapacidad().equals(pacienteDTO.getPorcentajeDiscapacidad())) {
            historialCambiosService.registrarCambio("Paciente", paciente.getId(), "porcentajeDiscapacidad", paciente.getPorcentajeDiscapacidad().toString(), pacienteDTO.getPorcentajeDiscapacidad().toString());
        }
        if (paciente.getInstitucionEducativa() == null || !paciente.getInstitucionEducativa().getId().equals(pacienteDTO.getInstitucionEducativa())) {
            historialCambiosService.registrarCambio("Paciente", paciente.getId(), "institucionEducativa", paciente.getInstitucionEducativa() == null ? "null" : paciente.getInstitucionEducativa().getId().toString(), pacienteDTO.getInstitucionEducativa().toString());
        }
        if (paciente.getJornada() == null || !paciente.getJornada().getId().equals(pacienteDTO.getJornada())) {
            historialCambiosService.registrarCambio("Paciente", paciente.getId(), "jornada", paciente.getJornada() == null ? "null" : paciente.getJornada().getId().toString(), pacienteDTO.getJornada().toString());
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
