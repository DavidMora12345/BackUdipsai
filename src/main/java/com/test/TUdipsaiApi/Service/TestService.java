package com.test.TUdipsaiApi.Service;

import com.test.TUdipsaiApi.Model.*;
import com.test.TUdipsaiApi.Repository.DocumentoRepositorio;
import com.test.TUdipsaiApi.Repository.TestRepositorio;
import com.test.TUdipsaiApi.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TestService {

    @Autowired
    private TestRepositorio testRepositorio;

    @Autowired
    private DocumentoRepositorio documentoRepositorio;

    public List<TestDTO> getAllTests() {
        return testRepositorio.findByActivo(1).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<TestDTO> getTestById(Long id) {
        return testRepositorio.findById(id).map(this::convertToDTO);
    }

    public Test saveTest(TestDTO testDTO) {
        Test test = convertToEntity(testDTO);

        Documento documento = new Documento();
        documento.setContenido(testDTO.getContenido());
        documento = documentoRepositorio.save(documento);
        test.setDocumentoId(documento.getId());

        return testRepositorio.save(test);
    }

    public Test updateTest(Long id, TestDTO testDTO) {
        Optional<Test> optionalTest = testRepositorio.findById(id);
        if (optionalTest.isPresent()) {
            Test test = optionalTest.get();
            test.setNombreArchivo(testDTO.getNombreArchivo());
            test.setFecha(testDTO.getFecha());
            test.setActivo(testDTO.getActivo());

            if (testDTO.getPaciente() != null) {
                test.setPaciente(convertDTOToPaciente(testDTO.getPaciente()));
            }

            if (testDTO.getEspecialista() != null) {
                test.setEspecialista(convertDTOToEspecialista(testDTO.getEspecialista()));
            }

            Documento documento = new Documento();
            documento.setContenido(testDTO.getContenido());
            documento = documentoRepositorio.save(documento);
            test.setDocumentoId(documento.getId());

            return testRepositorio.save(test);
        } else {
            throw new RuntimeException("Test not found");
        }
    }

    public void deleteTest(Long id) {
        Optional<Test> optionalTest = testRepositorio.findById(id);
        if (optionalTest.isPresent()) {
            Test test = optionalTest.get();
            test.setActivo(0); // Cambiar estado a inactivo
            testRepositorio.save(test);
        }
    }

    public List<TestDTO> getTestsByPacienteId(Long pacienteId) {
        return testRepositorio.findByPacienteIdAndActivo(pacienteId, 1).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<TestDTO> getTestsByPacienteIdAndEspecialistaCedula(Long pacienteId, String cedulaEspecialista) {
        return testRepositorio.findByPacienteIdAndEspecialistaCedulaAndActivo(pacienteId, cedulaEspecialista, 1).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private TestDTO convertToDTO(Test test) {
        TestDTO testDTO = new TestDTO();
        testDTO.setId(test.getId());
        testDTO.setNombreArchivo(test.getNombreArchivo());
        testDTO.setFecha(test.getFecha());
        testDTO.setActivo(test.getActivo());
        testDTO.setDocumentoId(test.getDocumentoId());

        if (test.getPaciente() != null) {
            testDTO.setPaciente(convertPacienteToDTO(test.getPaciente()));
        }

        if (test.getEspecialista() != null) {
            testDTO.setEspecialista(convertEspecialistaToDTO(test.getEspecialista()));
        }

        return testDTO;
    }

    private Test convertToEntity(TestDTO testDTO) {
        Test test = new Test();
        test.setId(testDTO.getId());
        test.setNombreArchivo(testDTO.getNombreArchivo());
        test.setFecha(testDTO.getFecha());
        test.setActivo(testDTO.getActivo());

        if (testDTO.getPaciente() != null) {
            test.setPaciente(convertDTOToPaciente(testDTO.getPaciente()));
        }

        if (testDTO.getEspecialista() != null) {
            test.setEspecialista(convertDTOToEspecialista(testDTO.getEspecialista()));
        }

        return test;
    }

    private PacienteDTO convertPacienteToDTO(Paciente paciente) {
        if (paciente == null) {
            return null;
        }
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
        dto.setImagen(paciente.getImagen());
        dto.setTelefono(paciente.getTelefono());
        dto.setCelular(paciente.getCelular());
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

        if (paciente.getInstitucionEducativa() != null) {
            dto.setInstitucionEducativa(paciente.getInstitucionEducativa());
        }

        if (paciente.getJornada() != null) {
            dto.setJornada(paciente.getJornada());
        }

        return dto;
    }

    private Paciente convertDTOToPaciente(PacienteDTO dto) {
        if (dto == null) {
            return null;
        }
        Paciente paciente = new Paciente();
        paciente.setId(dto.getId());
        paciente.setFechaApertura(dto.getFechaApertura());
        paciente.setPacienteEstado(dto.getPacienteEstado());
        paciente.setNombresApellidos(dto.getNombresApellidos());
        paciente.setCiudad(dto.getCiudad());
        paciente.setFechaNacimiento(dto.getFechaNacimiento());
        paciente.setEdad(dto.getEdad());
        paciente.setCedula(dto.getCedula());
        paciente.setDomicilio(dto.getDomicilio());
        paciente.setImagen(dto.getImagen());
        paciente.setTelefono(dto.getTelefono());
        paciente.setCelular(dto.getCelular());
        paciente.setProyecto(dto.getProyecto());
        paciente.setAnioEducacion(dto.getAnioEducacion());
        paciente.setParalelo(dto.getParalelo());
        paciente.setPerteneceInclusion(dto.getPerteneceInclusion());
        paciente.setTieneDiscapacidad(dto.getTieneDiscapacidad());
        paciente.setPortadorCarnet(dto.isPortadorCarnet());
        paciente.setDiagnostico(dto.getDiagnostico());
        paciente.setMotivoConsulta(dto.getMotivoConsulta());
        paciente.setObservaciones(dto.getObservaciones());
        paciente.setTipoDiscapacidad(dto.getTipoDiscapacidad());
        paciente.setDetalleDiscapacidad(dto.getDetalleDiscapacidad());
        paciente.setPorcentajeDiscapacidad(dto.getPorcentajeDiscapacidad());
        paciente.setPerteneceAProyecto(dto.getPerteneceAProyecto());

        if (dto.getInstitucionEducativa() != null) {
            paciente.setInstitucionEducativa(dto.getInstitucionEducativa());
        }

        if (dto.getJornada() != null) {
            paciente.setJornada(dto.getJornada());
        }

        return paciente;
    }

    private EspecialistasDTO convertEspecialistaToDTO(Especialistas especialista) {
        if (especialista == null) {
            return null;
        }
        EspecialistasDTO dto = new EspecialistasDTO();
        dto.setCedula(especialista.getCedula());
        dto.setEspecialistaEstado(especialista.getEspecialistaEstado());
        dto.setPrimerNombre(especialista.getPrimerNombre());
        dto.setSegundoNombre(especialista.getSegundoNombre());
        dto.setPrimerApellido(especialista.getPrimerApellido());
        dto.setSegundoApellido(especialista.getSegundoApellido());
        dto.setEspecialidad(convertEspecialidadToDTO(especialista.getEspecialidad()));
        dto.setEsPasante(especialista.getEsPasante());
        dto.setEspecialistaAsignado(especialista.getEspecialistaAsignado());
        dto.setInicioPasantia(especialista.getInicioPasantia());
        dto.setFinPasantia(especialista.getFinPasantia());
        dto.setImagen(especialista.getImagen());
        return dto;
    }

    private Especialistas convertDTOToEspecialista(EspecialistasDTO dto) {
        if (dto == null) {
            return null;
        }
        Especialistas especialista = new Especialistas();
        especialista.setCedula(dto.getCedula());
        especialista.setEspecialistaEstado(dto.getEspecialistaEstado());
        especialista.setPrimerNombre(dto.getPrimerNombre());
        especialista.setSegundoNombre(dto.getSegundoNombre());
        especialista.setPrimerApellido(dto.getPrimerApellido());
        especialista.setSegundoApellido(dto.getSegundoApellido());
        especialista.setEspecialidad(convertDTOToEspecialidad(dto.getEspecialidad()));
        especialista.setEsPasante(dto.getEsPasante());
        especialista.setEspecialistaAsignado(dto.getEspecialistaAsignado());
        especialista.setInicioPasantia(dto.getInicioPasantia());
        especialista.setFinPasantia(dto.getFinPasantia());
        especialista.setImagen(dto.getImagen());
        return especialista;
    }

    private InstitucionEducativaDTO convertInstitucionEducativaToDTO(InstitucionEducativa institucionEducativa) {
        if (institucionEducativa == null) {
            return null;
        }
        InstitucionEducativaDTO dto = new InstitucionEducativaDTO();
        dto.setId(institucionEducativa.getId());
        dto.setNombreInstitucion(institucionEducativa.getNombreInstitucion());
        dto.setDireccion(institucionEducativa.getDireccion());
        dto.setTipoInstitucion(institucionEducativa.getTipoInstitucion());
        dto.setInstitucionEstado(institucionEducativa.getInstitucionEstado());
        return dto;
    }

    private InstitucionEducativa convertDTOToInstitucionEducativa(InstitucionEducativaDTO dto) {
        if (dto == null) {
            return null;
        }
        InstitucionEducativa institucionEducativa = new InstitucionEducativa();
        institucionEducativa.setId(dto.getId());
        institucionEducativa.setNombreInstitucion(dto.getNombreInstitucion());
        institucionEducativa.setDireccion(dto.getDireccion());
        institucionEducativa.setTipoInstitucion(dto.getTipoInstitucion());
        institucionEducativa.setInstitucionEstado(dto.getInstitucionEstado());
        return institucionEducativa;
    }

    private JornadaDTO convertJornadaToDTO(Jornada jornada) {
        if (jornada == null) {
            return null;
        }
        JornadaDTO dto = new JornadaDTO();
        dto.setId(jornada.getId());
        dto.setNombreJornada(jornada.getNombreJornada());
        dto.setEstadoJornada(jornada.getEstadoJornada());
        return dto;
    }

    private Jornada convertDTOToJornada(JornadaDTO dto) {
        if (dto == null) {
            return null;
        }
        Jornada jornada = new Jornada();
        jornada.setId(dto.getId());
        jornada.setNombreJornada(dto.getNombreJornada());
        jornada.setEstadoJornada(dto.getEstadoJornada());
        return jornada;
    }

    private EspecialidadDTO convertEspecialidadToDTO(Especialidad especialidad) {
        if (especialidad == null) {
            return null;
        }
        EspecialidadDTO dto = new EspecialidadDTO();
        dto.setId(especialidad.getId());
        dto.setArea(especialidad.getArea());
        return dto;
    }

    private Especialidad convertDTOToEspecialidad(EspecialidadDTO dto) {
        if (dto == null) {
            return null;
        }
        Especialidad especialidad = new Especialidad();
        especialidad.setId(dto.getId());
        especialidad.setArea(dto.getArea());
        return especialidad;
    }
}
