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
        return testRepositorio.findByActivo(1).stream() // Cambiado a findByActivo(1)
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<TestDTO> getTestById(Long id) {
        return testRepositorio.findById(id).map(this::convertToDTO);
    }

    public Test saveTest(TestDTO testDTO) {
        Test test = convertToEntity(testDTO);
        test.setActivo(1); // Asegurar que el estado se establece a 1

        Documento documento = new Documento();
        documento.setContenido(testDTO.getContenido());
        documento = documentoRepositorio.save(documento);
        test.setDocumentoId(documento.getId());

        return testRepositorio.save(test);
    }

    public Test updateTest(Long id, TestDTO testDTO) {
        Optional<Test> testOptional = testRepositorio.findById(id);
        if (testOptional.isPresent()) {
            Test test = convertToEntity(testDTO);
            test.setId(id);
            Documento documento = documentoRepositorio.findById(testDTO.getDocumentoId()).orElse(new Documento());
            documento.setContenido(testDTO.getContenido());
            documento = documentoRepositorio.save(documento);
            test.setDocumentoId(documento.getId());
            return testRepositorio.save(test);
        }
        return null;
    }

    public void deleteTest(Long id) {
        Optional<Test> optionalTest = testRepositorio.findById(id);
        if (optionalTest.isPresent()) {
            Test test = optionalTest.get();
            test.setActivo(0);
            testRepositorio.save(test);
        }
    }

    public List<TestDTO> getTestsByPacienteId(Long pacienteId) {
        return testRepositorio.findByPacienteIdAndActivo(pacienteId, 1).stream() // Cambiado a findByPacienteIdAndActivo(pacienteId, 1)
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
            PacienteDTO pacienteDTO = new PacienteDTO();
            pacienteDTO.setId(test.getPaciente().getId());
            pacienteDTO.setFechaApertura(test.getPaciente().getFechaApertura());
            pacienteDTO.setPacienteEstado(test.getPaciente().getPacienteEstado());
            pacienteDTO.setNombresApellidos(test.getPaciente().getNombresApellidos());
            pacienteDTO.setCiudad(test.getPaciente().getCiudad());
            pacienteDTO.setFechaNacimiento(test.getPaciente().getFechaNacimiento());
            pacienteDTO.setEdad(test.getPaciente().getEdad());
            pacienteDTO.setCedula(test.getPaciente().getCedula());
            pacienteDTO.setDomicilio(test.getPaciente().getDomicilio());
            pacienteDTO.setImagen(test.getPaciente().getImagen());
            pacienteDTO.setTelefono(test.getPaciente().getTelefono());
            pacienteDTO.setCelular(test.getPaciente().getCelular());
            pacienteDTO.setProyecto(test.getPaciente().getProyecto());
            pacienteDTO.setAnioEducacion(test.getPaciente().getAnioEducacion());
            pacienteDTO.setParalelo(test.getPaciente().getParalelo());
            pacienteDTO.setPerteneceInclusion(test.getPaciente().getPerteneceInclusion());
            pacienteDTO.setTieneDiscapacidad(test.getPaciente().getTieneDiscapacidad());
            pacienteDTO.setPortadorCarnet(test.getPaciente().isPortadorCarnet());
            pacienteDTO.setDiagnostico(test.getPaciente().getDiagnostico());
            pacienteDTO.setMotivoConsulta(test.getPaciente().getMotivoConsulta());
            pacienteDTO.setObservaciones(test.getPaciente().getObservaciones());
            pacienteDTO.setTipoDiscapacidad(test.getPaciente().getTipoDiscapacidad());
            pacienteDTO.setDetalleDiscapacidad(test.getPaciente().getDetalleDiscapacidad());
            pacienteDTO.setPorcentajeDiscapacidad(test.getPaciente().getPorcentajeDiscapacidad());
            pacienteDTO.setPerteneceAProyecto(test.getPaciente().getPerteneceAProyecto());

            if (test.getPaciente().getInstitucionEducativa() != null) {
                InstitucionEducativaDTO institucionEducativaDTO = new InstitucionEducativaDTO();
                institucionEducativaDTO.setId(test.getPaciente().getInstitucionEducativa().getId());
                institucionEducativaDTO.setNombreInstitucion(test.getPaciente().getInstitucionEducativa().getNombreInstitucion());
                institucionEducativaDTO.setDireccion(test.getPaciente().getInstitucionEducativa().getDireccion());
                institucionEducativaDTO.setTipoInstitucion(test.getPaciente().getInstitucionEducativa().getTipoInstitucion());
                pacienteDTO.setInstitucionEducativa(institucionEducativaDTO);
            }

            if (test.getPaciente().getJornada() != null) {
                JornadaDTO jornadaDTO = new JornadaDTO();
                jornadaDTO.setId(test.getPaciente().getJornada().getId());
                jornadaDTO.setNombreJornada(test.getPaciente().getJornada().getNombreJornada());
                jornadaDTO.setEstadoJornada(test.getPaciente().getJornada().getEstadoJornada());
                pacienteDTO.setJornada(jornadaDTO);
            }

            testDTO.setPaciente(pacienteDTO);
        }

        if (test.getEspecialista() != null) {
            EspecialistasDTO especialistaDTO = new EspecialistasDTO();
            especialistaDTO.setCedula(test.getEspecialista().getCedula());
            especialistaDTO.setEspecialistaEstado(test.getEspecialista().getEspecialistaEstado());
            especialistaDTO.setPrimerNombre(test.getEspecialista().getPrimerNombre());
            especialistaDTO.setSegundoNombre(test.getEspecialista().getSegundoNombre());
            especialistaDTO.setPrimerApellido(test.getEspecialista().getPrimerApellido());
            especialistaDTO.setSegundoApellido(test.getEspecialista().getSegundoApellido());

            if (test.getEspecialista().getEspecialidad() != null) {
                EspecialidadDTO especialidadDTO = new EspecialidadDTO();
                especialidadDTO.setId(test.getEspecialista().getEspecialidad().getId());
                especialidadDTO.setArea(test.getEspecialista().getEspecialidad().getArea());
                especialistaDTO.setEspecialidad(especialidadDTO);
            }

            especialistaDTO.setEsPasante(test.getEspecialista().getEsPasante());
            especialistaDTO.setEspecialistaAsignado(test.getEspecialista().getEspecialistaAsignado());
            especialistaDTO.setInicioPasantia(test.getEspecialista().getInicioPasantia());
            especialistaDTO.setFinPasantia(test.getEspecialista().getFinPasantia());
            especialistaDTO.setImagen(test.getEspecialista().getImagen());

            testDTO.setEspecialista(especialistaDTO);
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
            Paciente paciente = new Paciente();
            paciente.setId(testDTO.getPaciente().getId());
            paciente.setFechaApertura(testDTO.getPaciente().getFechaApertura());
            paciente.setPacienteEstado(testDTO.getPaciente().getPacienteEstado());
            paciente.setNombresApellidos(testDTO.getPaciente().getNombresApellidos());
            paciente.setCiudad(testDTO.getPaciente().getCiudad());
            paciente.setFechaNacimiento(testDTO.getPaciente().getFechaNacimiento());
            paciente.setEdad(testDTO.getPaciente().getEdad());
            paciente.setCedula(testDTO.getPaciente().getCedula());
            paciente.setDomicilio(testDTO.getPaciente().getDomicilio());
            paciente.setImagen(testDTO.getPaciente().getImagen());
            paciente.setTelefono(testDTO.getPaciente().getTelefono());
            paciente.setCelular(testDTO.getPaciente().getCelular());
            paciente.setProyecto(testDTO.getPaciente().getProyecto());
            paciente.setAnioEducacion(testDTO.getPaciente().getAnioEducacion());
            paciente.setParalelo(testDTO.getPaciente().getParalelo());
            paciente.setPerteneceInclusion(testDTO.getPaciente().getPerteneceInclusion());
            paciente.setTieneDiscapacidad(testDTO.getPaciente().getTieneDiscapacidad());
            paciente.setPortadorCarnet(testDTO.getPaciente().isPortadorCarnet());
            paciente.setDiagnostico(testDTO.getPaciente().getDiagnostico());
            paciente.setMotivoConsulta(testDTO.getPaciente().getMotivoConsulta());
            paciente.setObservaciones(testDTO.getPaciente().getObservaciones());
            paciente.setTipoDiscapacidad(testDTO.getPaciente().getTipoDiscapacidad());
            paciente.setDetalleDiscapacidad(testDTO.getPaciente().getDetalleDiscapacidad());
            paciente.setPorcentajeDiscapacidad(testDTO.getPaciente().getPorcentajeDiscapacidad());
            paciente.setPerteneceAProyecto(testDTO.getPaciente().getPerteneceAProyecto());

            if (testDTO.getPaciente().getInstitucionEducativa() != null) {
                InstitucionEducativa institucionEducativa = new InstitucionEducativa();
                institucionEducativa.setId(testDTO.getPaciente().getInstitucionEducativa().getId());
                institucionEducativa.setNombreInstitucion(testDTO.getPaciente().getInstitucionEducativa().getNombreInstitucion());
                institucionEducativa.setDireccion(testDTO.getPaciente().getInstitucionEducativa().getDireccion());
                institucionEducativa.setTipoInstitucion(testDTO.getPaciente().getInstitucionEducativa().getTipoInstitucion());
                paciente.setInstitucionEducativa(institucionEducativa);
            }

            if (testDTO.getPaciente().getJornada() != null) {
                Jornada jornada = new Jornada();
                jornada.setId(testDTO.getPaciente().getJornada().getId());
                jornada.setNombreJornada(testDTO.getPaciente().getJornada().getNombreJornada());
                jornada.setEstadoJornada(testDTO.getPaciente().getJornada().getEstadoJornada());
                paciente.setJornada(jornada);
            }

            test.setPaciente(paciente);
        }

        if (testDTO.getEspecialista() != null) {
            Especialistas especialista = new Especialistas();
            especialista.setCedula(testDTO.getEspecialista().getCedula());
            especialista.setEspecialistaEstado(testDTO.getEspecialista().getEspecialistaEstado());
            especialista.setPrimerNombre(testDTO.getEspecialista().getPrimerNombre());
            especialista.setSegundoNombre(testDTO.getEspecialista().getSegundoNombre());
            especialista.setPrimerApellido(testDTO.getEspecialista().getPrimerApellido());
            especialista.setSegundoApellido(testDTO.getEspecialista().getSegundoApellido());

            if (testDTO.getEspecialista().getEspecialidad() != null) {
                Especialidad especialidad = new Especialidad();
                especialidad.setId(testDTO.getEspecialista().getEspecialidad().getId());
                especialidad.setArea(testDTO.getEspecialista().getEspecialidad().getArea());
                especialista.setEspecialidad(especialidad);
            }

            especialista.setEsPasante(testDTO.getEspecialista().getEsPasante());
            especialista.setEspecialistaAsignado(testDTO.getEspecialista().getEspecialistaAsignado());
            especialista.setInicioPasantia(testDTO.getEspecialista().getInicioPasantia());
            especialista.setFinPasantia(testDTO.getEspecialista().getFinPasantia());
            especialista.setImagen(testDTO.getEspecialista().getImagen());

            test.setEspecialista(especialista);
        }

        return test;
    }
}
