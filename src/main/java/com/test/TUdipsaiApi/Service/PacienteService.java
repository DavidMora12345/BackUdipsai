package com.test.TUdipsaiApi.Service;

import com.test.TUdipsaiApi.Model.Documento;
import com.test.TUdipsaiApi.Model.InstitucionEducativa;
import com.test.TUdipsaiApi.Model.Jornada;
import com.test.TUdipsaiApi.Model.Paciente;
import com.test.TUdipsaiApi.Repository.InstitucionEducativaRepositorio;
import com.test.TUdipsaiApi.Repository.JornadaRepositorio;
import com.test.TUdipsaiApi.Repository.PacienteRepositorio;
import com.test.TUdipsaiApi.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepositorio pacienteRepositorio;

    @Autowired
    private InstitucionEducativaRepositorio institucionEducativaRepositorio;

    @Autowired
    private JornadaRepositorio jornadaRepositorio;

    @Autowired
    private DocumentoService documentoService;

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
        paciente.setPerteneceAProyecto(pacienteDTO.getPerteneceAProyecto());
        paciente.setSede(pacienteDTO.getSede());

        if (pacienteDTO.getInstitucionEducativa() != null && pacienteDTO.getInstitucionEducativa().getId() != null) {
            InstitucionEducativa institucion = institucionEducativaRepositorio.findById(pacienteDTO.getInstitucionEducativa().getId())
                    .orElseThrow(() -> new RuntimeException("Institucion Educativa not found"));
            paciente.setInstitucionEducativa(institucion);
        } else {
            paciente.setInstitucionEducativa(null);
        }

        if (pacienteDTO.getJornada() != null && pacienteDTO.getJornada().getId() != null) {
            Jornada jornada = jornadaRepositorio.findById(pacienteDTO.getJornada().getId())
                    .orElseThrow(() -> new RuntimeException("Jornada not found"));
            paciente.setJornada(jornada);
        } else {
            paciente.setJornada(null);
        }

        if (pacienteDTO.getFichaDiagnostica() != null && pacienteDTO.getFichaDiagnostica().getId() != null) {
            Documento documento = documentoService.findById(pacienteDTO.getFichaDiagnostica().getId())
                    .orElseThrow(() -> new RuntimeException("Documento not found"));
            paciente.setFichaDiagnostica(documento);
        } else {
            paciente.setFichaDiagnostica(null);
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
            InstitucionEducativa institucion = paciente.getInstitucionEducativa();
            pacienteDTO.setInstitucionEducativa(institucion);
        }

        if (paciente.getJornada() != null) {
            Jornada jornada = paciente.getJornada();
            pacienteDTO.setJornada(jornada);
        }

        if (paciente.getFichaDiagnostica() != null) {
            Documento documento = paciente.getFichaDiagnostica();
            pacienteDTO.setFichaDiagnosticaId(documento.getId());
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
        pacienteDTO.setSede(paciente.getSede());

        return pacienteDTO;
    }

    public PacienteSinImagenDTO convertToSinImagenDTO(Paciente paciente) {
        PacienteSinImagenDTO dto = new PacienteSinImagenDTO();
        dto.setId(paciente.getId());
        dto.setFechaApertura(paciente.getFechaApertura());
        dto.setPacienteEstado(paciente.getPacienteEstado());
        dto.setNombresApellidos(paciente.getNombresApellidos());
        dto.setCiudad(paciente.getCiudad());
        dto.setFechaNacimiento(paciente.getFechaNacimiento());
        dto.setEdad(paciente.getEdad());
        dto.setCedula(paciente.getCedula());
        dto.setDomicilio(paciente.getDomicilio());
        dto.setTelefono(paciente.getTelefono());
        dto.setCelular(paciente.getCelular());

        if (paciente.getInstitucionEducativa() != null) {
            dto.setInstitucionEducativa(paciente.getInstitucionEducativa());
        }

        if (paciente.getJornada() != null) {
            dto.setJornada(paciente.getJornada());
        }

        dto.setProyecto(paciente.getProyecto());
        dto.setAnioEducacion(paciente.getAnioEducacion());
        dto.setParalelo(paciente.getParalelo());
        dto.setPerteneceInclusion(paciente.getPerteneceInclusion());
        dto.setTieneDiscapacidad(paciente.getTieneDiscapacidad());
        dto.setPortadorCarnet(paciente.isPortadorCarnet());
        dto.setDiagnostico(paciente.getDiagnostico());
        dto.setMotivoConsulta(paciente.getMotivoConsulta());
        dto.setObservaciones(paciente.getObservaciones());
        dto.setTipoDiscapacidad(paciente.getTipoDiscapacidad());
        dto.setDetalleDiscapacidad(paciente.getDetalleDiscapacidad());
        dto.setPorcentajeDiscapacidad(paciente.getPorcentajeDiscapacidad());
        dto.setPerteneceAProyecto(paciente.getPerteneceAProyecto());
        dto.setSede(paciente.getSede());

        return dto;
    }

    public Paciente updatePaciente(Integer id, PacienteUpdateDTO pacienteUpdateDTO) {
        Optional<Paciente> pacienteOptional = pacienteRepositorio.findById(id);
        if (pacienteOptional.isPresent()) {
            Paciente paciente = pacienteOptional.get();

            paciente.setFechaApertura(pacienteUpdateDTO.getFechaApertura());
            paciente.setPacienteEstado(pacienteUpdateDTO.getPacienteEstado());
            paciente.setNombresApellidos(pacienteUpdateDTO.getNombresApellidos());
            paciente.setCiudad(pacienteUpdateDTO.getCiudad());
            paciente.setFechaNacimiento(pacienteUpdateDTO.getFechaNacimiento());
            paciente.setEdad(pacienteUpdateDTO.getEdad());
            paciente.setCedula(pacienteUpdateDTO.getCedula());
            paciente.setDomicilio(pacienteUpdateDTO.getDomicilio());
            paciente.setImagen(pacienteUpdateDTO.getImagen());
            paciente.setTelefono(pacienteUpdateDTO.getTelefono());
            paciente.setCelular(pacienteUpdateDTO.getCelular());
            paciente.setProyecto(pacienteUpdateDTO.getProyecto());
            paciente.setAnioEducacion(pacienteUpdateDTO.getAnioEducacion());
            paciente.setParalelo(pacienteUpdateDTO.getParalelo());
            paciente.setPerteneceInclusion(pacienteUpdateDTO.getPerteneceInclusion());
            paciente.setTieneDiscapacidad(pacienteUpdateDTO.getTieneDiscapacidad());
            paciente.setPortadorCarnet(pacienteUpdateDTO.isPortadorCarnet());
            paciente.setDiagnostico(pacienteUpdateDTO.getDiagnostico());
            paciente.setMotivoConsulta(pacienteUpdateDTO.getMotivoConsulta());
            paciente.setObservaciones(pacienteUpdateDTO.getObservaciones());
            paciente.setTipoDiscapacidad(pacienteUpdateDTO.getTipoDiscapacidad());
            paciente.setDetalleDiscapacidad(pacienteUpdateDTO.getDetalleDiscapacidad());
            paciente.setPorcentajeDiscapacidad(pacienteUpdateDTO.getPorcentajeDiscapacidad());
            paciente.setPerteneceAProyecto(pacienteUpdateDTO.getPerteneceAProyecto());
            paciente.setSede(pacienteUpdateDTO.getSede());

            if (pacienteUpdateDTO.getInstitucionEducativa() != null) {
                InstitucionEducativa institucion = institucionEducativaRepositorio.findById(pacienteUpdateDTO.getInstitucionEducativa())
                        .orElseThrow(() -> new RuntimeException("Institucion Educativa not found"));
                paciente.setInstitucionEducativa(institucion);
            } else {
                paciente.setInstitucionEducativa(null);
            }

            if (pacienteUpdateDTO.getJornada() != null) {
                Jornada jornada = jornadaRepositorio.findById(pacienteUpdateDTO.getJornada())
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

    public Paciente createPaciente(PacienteUpdateDTO pacienteUpdateDTO) {
        Paciente paciente = new Paciente();

        paciente.setFechaApertura(pacienteUpdateDTO.getFechaApertura());
        paciente.setPacienteEstado(pacienteUpdateDTO.getPacienteEstado());
        paciente.setNombresApellidos(pacienteUpdateDTO.getNombresApellidos());
        paciente.setCiudad(pacienteUpdateDTO.getCiudad());
        paciente.setFechaNacimiento(pacienteUpdateDTO.getFechaNacimiento());
        paciente.setEdad(pacienteUpdateDTO.getEdad());
        paciente.setCedula(pacienteUpdateDTO.getCedula());
        paciente.setDomicilio(pacienteUpdateDTO.getDomicilio());
        paciente.setImagen(pacienteUpdateDTO.getImagen());
        paciente.setTelefono(pacienteUpdateDTO.getTelefono());
        paciente.setCelular(pacienteUpdateDTO.getCelular());
        paciente.setProyecto(pacienteUpdateDTO.getProyecto());
        paciente.setAnioEducacion(pacienteUpdateDTO.getAnioEducacion());
        paciente.setParalelo(pacienteUpdateDTO.getParalelo());
        paciente.setPerteneceInclusion(pacienteUpdateDTO.getPerteneceInclusion());
        paciente.setTieneDiscapacidad(pacienteUpdateDTO.getTieneDiscapacidad());
        paciente.setPortadorCarnet(pacienteUpdateDTO.isPortadorCarnet());
        paciente.setDiagnostico(pacienteUpdateDTO.getDiagnostico());
        paciente.setMotivoConsulta(pacienteUpdateDTO.getMotivoConsulta());
        paciente.setObservaciones(pacienteUpdateDTO.getObservaciones());
        paciente.setTipoDiscapacidad(pacienteUpdateDTO.getTipoDiscapacidad());
        paciente.setDetalleDiscapacidad(pacienteUpdateDTO.getDetalleDiscapacidad());
        paciente.setPorcentajeDiscapacidad(pacienteUpdateDTO.getPorcentajeDiscapacidad());
        paciente.setPerteneceAProyecto(pacienteUpdateDTO.getPerteneceAProyecto());
        paciente.setSede(pacienteUpdateDTO.getSede());


        if (pacienteUpdateDTO.getInstitucionEducativa() != null) {
            InstitucionEducativa institucion = institucionEducativaRepositorio.findById(pacienteUpdateDTO.getInstitucionEducativa())
                    .orElseThrow(() -> new RuntimeException("Institucion Educativa not found"));
            paciente.setInstitucionEducativa(institucion);
        } else {
            paciente.setInstitucionEducativa(null);
        }

        if (pacienteUpdateDTO.getJornada() != null) {
            Jornada jornada = jornadaRepositorio.findById(pacienteUpdateDTO.getJornada())
                    .orElseThrow(() -> new RuntimeException("Jornada not found"));
            paciente.setJornada(jornada);
        } else {
            paciente.setJornada(null);
        }


        return pacienteRepositorio.save(paciente);
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

    public List<PacienteSinImagenDTO> searchPacientes(String search, String sede) {
        List<Paciente> pacientes;

        pacientes =  pacienteRepositorio.searchPacientes(search,sede,PageRequest.of(0, 100));


        // Convierte las entidades a DTO
        return pacientes.stream()
                .map(this::convertToSinImagenDTO)
                .collect(Collectors.toList());
    }

    public List<Paciente> getAllPacientes() {
        return pacienteRepositorio.findByPacienteEstado(1);
    }

    public List<PacienteSinImagenDTO> getAllPacientesSinImagen() {
        List<Paciente> pacientes = getAllPacientes();
        return pacientes.stream()
                .map(this::convertToSinImagenDTO)
                .collect(Collectors.toList());
    }

    public DocumentoDTO addDocumentoToPaciente(Integer pacienteId, MultipartFile file) throws IOException {
        Optional<Paciente> optionalPaciente = pacienteRepositorio.findById(pacienteId);
        if (optionalPaciente.isPresent()) {
            Documento documento = documentoService.saveDocumento(file);
            Paciente paciente = optionalPaciente.get();
            paciente.setFichaDiagnostica(documento);
            pacienteRepositorio.save(paciente);
            DocumentoDTO documentoDTO = new DocumentoDTO();
            documentoDTO.setId(documento.getId());
            documentoDTO.setContenido(documento.getContenido());
            return documentoDTO;
        } else {
            throw new RuntimeException("Paciente not found with ID: " + pacienteId);
        }
    }

    public void deleteDocumentoFromPaciente(Integer pacienteId) {
        Optional<Paciente> optionalPaciente = pacienteRepositorio.findById(pacienteId);
        if (optionalPaciente.isPresent()) {
            Paciente paciente = optionalPaciente.get();
            Documento documento = paciente.getFichaDiagnostica();
            if (documento != null) {
                documentoService.deleteDocumento(documento.getId());
                paciente.setFichaDiagnostica(null);
                pacienteRepositorio.save(paciente);
            }
        } else {
            throw new RuntimeException("Paciente not found with ID: " + pacienteId);
        }
    }
}
