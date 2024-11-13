package com.test.TUdipsaiApi.Service;

import com.test.TUdipsaiApi.Model.*;
import com.test.TUdipsaiApi.Repository.*;
import com.test.TUdipsaiApi.dto.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
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

    @Autowired
    private SedeRepositorio sedeRepositorio;

    @Autowired
    private HistorialCambiosService historialCambiosService;

    @Autowired
    private FichaMedicaRepository fichaMedicaRepository;

    @Autowired
    private DocumentoRepositorio documentoRepositorio;

    @Autowired
    private ImagenService imagenService;  // Servicio para compresión/descompresión de imágenes

    private static final String IMAGE_DIRECTORY = System.getProperty("user.home") + "/UdipsaiImagenesPacientes/";

    // Obtener un paciente por su ID
    public Optional<Paciente> getPacienteById(Integer id) {
        return pacienteRepositorio.findById(id);
    }

    // Guardar o actualizar un paciente en la base de datos
    public Paciente saveOrUpdate(Paciente paciente) {
        return pacienteRepositorio.save(paciente);
    }

    // Guardar la imagen en Base64, comprimirla y guardarla en disco
    public String guardarImagenBase64(String base64Image, String tipo, String nombres, String cedula) throws IOException {
        String fileName = tipo + "-" + nombres.replace(" ", "_") + "-" + cedula;
        return imagenService.saveCompressedImage(base64Image, fileName); // Guardar la imagen comprimida
    }

    // Recuperar la imagen descomprimida en formato Base64
    public String recuperarImagenBase64(String filePath) throws IOException {
        return imagenService.getDecompressedImage(filePath); // Recuperar y descomprimir la imagen
    }

    public PacienteDTO convertToDTO(Paciente paciente) {
        PacienteDTO dto = new PacienteDTO();
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
        dto.setInstitucionEducativa(paciente.getInstitucionEducativa());
        dto.setProyecto(paciente.getProyecto());
        dto.setJornada(paciente.getJornada());
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

        // Verificación de ficha diagnóstica
        if (paciente.getFichaDiagnostica() != null) {
            dto.setFichaDiagnostica(new DocumentoIdDTO(paciente.getFichaDiagnostica().getId()));
        } else {
            dto.setFichaDiagnostica(null);
        }

        // Verificación de ficha compromiso
        if (paciente.getFichaCompromiso() != null) {
            dto.setFichaCompromiso(new DocumentoIdDTO(paciente.getFichaCompromiso().getId()));
        } else {
            dto.setFichaCompromiso(null);
        }

        if (paciente.getSede() != null) {
            dto.setSede(paciente.getSede());
        }

        return dto;
    }


    // Crear un nuevo paciente y guardar la imagen en la carpeta
    public PacienteDTO createPaciente(PacienteUpdateDTO pacienteUpdateDTO) throws IOException {
        Paciente paciente = new Paciente();

        // Guardar los datos del paciente
        paciente.setFechaApertura(pacienteUpdateDTO.getFechaApertura());
        paciente.setPacienteEstado(pacienteUpdateDTO.getPacienteEstado());
        paciente.setNombresApellidos(pacienteUpdateDTO.getNombresApellidos());
        paciente.setCiudad(pacienteUpdateDTO.getCiudad());
        paciente.setFechaNacimiento(pacienteUpdateDTO.getFechaNacimiento());
        paciente.setEdad(pacienteUpdateDTO.getEdad());
        paciente.setCedula(pacienteUpdateDTO.getCedula());
        paciente.setDomicilio(pacienteUpdateDTO.getDomicilio());
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

        // Asignar la institución educativa
        if (pacienteUpdateDTO.getInstitucionEducativa() != null) {
            Optional<InstitucionEducativa> institucion = institucionEducativaRepositorio.findById(pacienteUpdateDTO.getInstitucionEducativa());
            if (institucion.isPresent()) {
                paciente.setInstitucionEducativa(institucion.get());
            } else {
                throw new RuntimeException("Institución Educativa no encontrada con ID: " + pacienteUpdateDTO.getInstitucionEducativa());
            }
        }

        // Asignar la sede completa (en lugar de solo el ID)
        if (pacienteUpdateDTO.getSede() != null) {
            Optional<Sede> sede = sedeRepositorio.findById(pacienteUpdateDTO.getSede());
            if (sede.isPresent()) {
                paciente.setSede(sede.get());
            } else {
                throw new RuntimeException("Sede no encontrada con ID: " + pacienteUpdateDTO.getSede());
            }
        }

        // Guardar la imagen en la carpeta (no en la base de datos)
        if (pacienteUpdateDTO.getImagen() != null) {
            String fileName = "imagen-" + pacienteUpdateDTO.getNombresApellidos().replace(" ", "_") + "-" + pacienteUpdateDTO.getCedula();
            guardarImagenBase64(pacienteUpdateDTO.getImagen(), "imagen", pacienteUpdateDTO.getNombresApellidos(), pacienteUpdateDTO.getCedula());
        }

        // Asignar fichaCompromiso si está presente
        if (pacienteUpdateDTO.getFichaCompromiso() != null) {
            Optional<Documento> fichaCompromiso = documentoRepositorio.findById(pacienteUpdateDTO.getFichaCompromiso());
            fichaCompromiso.ifPresent(paciente::setFichaCompromiso);  // Asignar el documento si existe
        }

        // Guardar el paciente en la base de datos
        Paciente savedPaciente = pacienteRepositorio.save(paciente);

        return convertToDTO(savedPaciente);
    }



    // Actualizar un paciente y gestionar la imagen desde la carpeta
    public Paciente updatePaciente(Integer id, PacienteUpdateDTO pacienteUpdateDTO) throws IOException {
        Optional<Paciente> pacienteOptional = pacienteRepositorio.findById(id);
        if (pacienteOptional.isPresent()) {
            Paciente paciente = pacienteOptional.get();
            Paciente valorAnterior = new Paciente(paciente);  // Guardamos el estado anterior para el historial de cambios

            // Actualizar los campos del paciente
            paciente.setFechaApertura(pacienteUpdateDTO.getFechaApertura());
            paciente.setPacienteEstado(pacienteUpdateDTO.getPacienteEstado());
            paciente.setNombresApellidos(pacienteUpdateDTO.getNombresApellidos());
            paciente.setCiudad(pacienteUpdateDTO.getCiudad());
            paciente.setFechaNacimiento(pacienteUpdateDTO.getFechaNacimiento());
            paciente.setEdad(pacienteUpdateDTO.getEdad());
            paciente.setCedula(pacienteUpdateDTO.getCedula());
            paciente.setDomicilio(pacienteUpdateDTO.getDomicilio());
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

            // Asignar la institución educativa
            if (pacienteUpdateDTO.getInstitucionEducativa() != null) {
                Optional<InstitucionEducativa> institucion = institucionEducativaRepositorio.findById(pacienteUpdateDTO.getInstitucionEducativa());
                if (institucion.isPresent()) {
                    paciente.setInstitucionEducativa(institucion.get());
                } else {
                    throw new RuntimeException("Institución Educativa no encontrada con ID: " + pacienteUpdateDTO.getInstitucionEducativa());
                }
            }

            // Asignar la sede completa (en lugar de solo el ID)
            if (pacienteUpdateDTO.getSede() != null) {
                Optional<Sede> sede = sedeRepositorio.findById(pacienteUpdateDTO.getSede());
                if (sede.isPresent()) {
                    paciente.setSede(sede.get());
                } else {
                    throw new RuntimeException("Sede no encontrada con ID: " + pacienteUpdateDTO.getSede());
                }
            }

            // Guardar la imagen en la carpeta (si se actualiza)
            if (pacienteUpdateDTO.getImagen() != null) {
                String fileName = "imagen-" + pacienteUpdateDTO.getNombresApellidos().replace(" ", "_") + "-" + pacienteUpdateDTO.getCedula();
                guardarImagenBase64(pacienteUpdateDTO.getImagen(), "imagen", pacienteUpdateDTO.getNombresApellidos(), pacienteUpdateDTO.getCedula());
            }

            // Guardar los cambios y registrar el historial
            Paciente valorNuevo = new Paciente(paciente);  // Guardamos el nuevo estado del paciente
            pacienteRepositorio.save(paciente);

            historialCambiosService.registrarCambio("Paciente", id.longValue(), "UPDATE", valorAnterior, valorNuevo);

            return paciente;
        } else {
            return null;
        }
    }



    // Eliminar paciente (cambiando el estado)
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

    // Buscar pacientes
    public List<PacienteSinImagenDTO> searchPacientes(String search, Integer sedeId) {
        List<Paciente> pacientes;
        if (sedeId == null) {
            pacientes = pacienteRepositorio.searchPacientes(search, null, PageRequest.of(0, 500));
        } else {
            pacientes = pacienteRepositorio.searchPacientes(search, sedeId, PageRequest.of(0, 500));
        }
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

    // Conversión de Paciente a DTO sin imagen
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
        if (paciente.getSede() != null) {
            dto.setSede(paciente.getSede());
        }

        return dto;
    }

    // Agregar documento a paciente
    public DocumentoDTO addDocumentoToPaciente(Integer pacienteId, MultipartFile file) throws IOException {
        Optional<Paciente> optionalPaciente = pacienteRepositorio.findById(pacienteId);
        if (optionalPaciente.isPresent()) {
            // Guardar el archivo en disco y obtener la URL
            Documento documento = new Documento();
            String fileUrl = documentoService.guardarArchivoEnDisco(file.getBytes());
            documento.setUrl(fileUrl);
            documento = documentoRepositorio.save(documento);

            // Asignar el documento al paciente
            Paciente paciente = optionalPaciente.get();
            paciente.setFichaDiagnostica(documento);
            pacienteRepositorio.save(paciente);

            // Preparar el DTO para devolver
            DocumentoDTO documentoDTO = new DocumentoDTO();
            documentoDTO.setId(documento.getId());
            documentoDTO.setUrl(documento.getUrl());

            return documentoDTO;
        } else {
            throw new RuntimeException("Paciente no encontrado con ID: " + pacienteId);
        }
    }

    // Eliminar documento de un paciente
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
            throw new RuntimeException("Paciente no encontrado con ID: " + pacienteId);
        }
    }
}
