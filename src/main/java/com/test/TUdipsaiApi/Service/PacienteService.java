package com.test.TUdipsaiApi.Service;

import com.test.TUdipsaiApi.Model.Paciente;
import com.test.TUdipsaiApi.Repository.PacienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {
    @Autowired
    private PacienteRepositorio pacienteRepositorio;

    // Metodo para obtener datos del paciente
    public Optional<Paciente> getPacienteById(Integer id) {
        return pacienteRepositorio.findById(id);
    }

    // Metodo para crear un nuevo paciente
    public Paciente createPaciente(Paciente paciente) {
        return pacienteRepositorio.save(paciente);
    }

    // Metodo para actualizar un paciente existente
    public Paciente updatePaciente(Integer id, Paciente pacienteDetails) {
        Optional<Paciente> optionalPaciente = pacienteRepositorio.findById(id);
        if (optionalPaciente.isPresent()) {
            Paciente paciente = optionalPaciente.get();
            // Actualiza los campos necesarios del paciente
            paciente.setFechaApertura(pacienteDetails.getFechaApertura());
            paciente.setPacienteEstado(pacienteDetails.getPacienteEstado());
            paciente.setNombresApellidos(pacienteDetails.getNombresApellidos());
            paciente.setCiudad(pacienteDetails.getCiudad());
            paciente.setFechaNacimiento(pacienteDetails.getFechaNacimiento());
            paciente.setEdad(pacienteDetails.getEdad());
            paciente.setCedula(pacienteDetails.getCedula());
            paciente.setDomicilio(pacienteDetails.getDomicilio());
            paciente.setImagen(pacienteDetails.getImagen());
            paciente.setTelefono(pacienteDetails.getTelefono());
            paciente.setCelular(pacienteDetails.getCelular());
            paciente.setInstitucionEducativa(pacienteDetails.getInstitucionEducativa());
            paciente.setTipoInstitucion(pacienteDetails.getTipoInstitucion());
            paciente.setProyecto(pacienteDetails.getProyecto());
            paciente.setJornada(pacienteDetails.getJornada());
            paciente.setDireccionInstitucion(pacienteDetails.getDireccionInstitucion());
            paciente.setAnioEducacion(pacienteDetails.getAnioEducacion());
            paciente.setParalelo(pacienteDetails.getParalelo());
            paciente.setPerteneceInclusion(pacienteDetails.getPerteneceInclusion());
            paciente.setTieneDiscapacidad(pacienteDetails.getTieneDiscapacidad());
            paciente.setPortadorCarnet(pacienteDetails.isPortadorCarnet());
            paciente.setDiagnostico(pacienteDetails.getDiagnostico());
            paciente.setMotivoConsulta(pacienteDetails.getMotivoConsulta());
            paciente.setObservaciones(pacienteDetails.getObservaciones());
            return pacienteRepositorio.save(paciente);
        } else {
            return null;
        }
    }

    // Metodo para "eliminar" un paciente cambiando su estado a 0
    public Optional<Paciente> deletePaciente(Integer id) {
        Optional<Paciente> optionalPaciente = pacienteRepositorio.findById(id);
        if (optionalPaciente.isPresent()) {
            Paciente paciente = optionalPaciente.get();
            paciente.setPacienteEstado(0); // Cambia el estado a 0
            pacienteRepositorio.save(paciente);
            return Optional.of(paciente);
        } else {
            return Optional.empty();
        }
    }
    public List<Paciente> searchPacientes(String busqueda) {
        return pacienteRepositorio.searchPacientes(busqueda, PageRequest.of(0, 100));
    }
    // Metodo para listar pacientes con estado 1
    public List<Paciente> getAllPacientes() {
        return pacienteRepositorio.findByPacienteEstado(1);
    }

    // Método setter para la inyección manual del repositorio
    public void setPacienteRepositorio(PacienteRepositorio pacienteRepositorio) {
        this.pacienteRepositorio = pacienteRepositorio;
    }
}