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
                Paciente paciente = new Paciente();
                paciente.setId(testDTO.getPaciente().getId());
                test.setPaciente(paciente);
            }

            if (testDTO.getEspecialista() != null) {
                Especialistas especialista = new Especialistas();
                especialista.setCedula(testDTO.getEspecialista().getCedula());
                test.setEspecialista(especialista);
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
            PacienteDTO pacienteDTO = new PacienteDTO();
            pacienteDTO.setId(test.getPaciente().getId());
            testDTO.setPaciente(pacienteDTO);
        }

        if (test.getEspecialista() != null) {
            EspecialistasDTO especialistaDTO = new EspecialistasDTO();
            especialistaDTO.setCedula(test.getEspecialista().getCedula());
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
            test.setPaciente(paciente);
        }

        if (testDTO.getEspecialista() != null) {
            Especialistas especialista = new Especialistas();
            especialista.setCedula(testDTO.getEspecialista().getCedula());
            test.setEspecialista(especialista);
        }

        return test;
    }
}
