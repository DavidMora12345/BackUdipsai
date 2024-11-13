package com.test.TUdipsaiApi.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.test.TUdipsaiApi.Model.HistorialCambios;
import com.test.TUdipsaiApi.Repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class HistorialCambiosService {

    @Autowired
    private HistorialCambiosRepositorio historialCambiosRepositorio;

    @Autowired
    private FonoaudiologiaRepository fonoaudiologiaRepository;

    @Autowired
    private PsicologiaClinicaRepository psicologiaClinicaRepository;

    @Autowired
    private PsicologiaEducativaRepository psicologiaEducativaRepository;

    @Autowired
    private FichaMedicaRepository fichaMedicaRepository;

    private final ObjectMapper objectMapper;

    public HistorialCambiosService() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
    }

    public List<HistorialCambios> findAll() {
        return historialCambiosRepositorio.findAll();
    }

    public List<HistorialCambios> findByEntidadId(Long entidadId) {
        return historialCambiosRepositorio.findByEntidadId(entidadId);
    }

    public List<HistorialCambios> findByEntidadIdAndEntidad(Long entidadId, String entidad) {
        return historialCambiosRepositorio.findByEntidadIdAndEntidad(entidadId, entidad);
    }

    public List<HistorialCambios> findAllHistorialesPorPacienteOrdenadosPorFecha(Long pacienteId) {
        List<HistorialCambios> historialTotal = new ArrayList<>();

        // Obtener todos los registros de cambios de Fonoaudiologia para el paciente
        fonoaudiologiaRepository.findByPacienteIdAndEstado(pacienteId.intValue(), 1).forEach(fonoaudiologia -> {
            historialTotal.addAll(historialCambiosRepositorio.findByEntidadIdAndEntidad(fonoaudiologia.getId().longValue(), "Fonoaudiologia"));
        });

        // Obtener todos los registros de cambios de PsicologiaClinica para el paciente
        psicologiaClinicaRepository.findByPacienteId(pacienteId.intValue()).forEach(psicologiaClinica -> {
            historialTotal.addAll(historialCambiosRepositorio.findByEntidadIdAndEntidad(psicologiaClinica.getId().longValue(), "PsicologiaClinica"));
        });

        // Obtener todos los registros de cambios de PsicologiaEducativa para el paciente
        psicologiaEducativaRepository.findByPacienteIdAndEstado(pacienteId.intValue(), 1).forEach(psicologiaEducativa -> {
            historialTotal.addAll(historialCambiosRepositorio.findByEntidadIdAndEntidad(psicologiaEducativa.getId().longValue(), "PsicologiaEducativa"));
        });

        // Obtener todos los registros de cambios de FichaMedica para el paciente
        fichaMedicaRepository.findByPacienteId(pacienteId.intValue()).forEach(fichaMedica -> {
            historialTotal.addAll(historialCambiosRepositorio.findByEntidadIdAndEntidad(fichaMedica.getId().longValue(), "FichaMedica"));
        });

        // Obtener todos los registros de cambios de la tabla Paciente (si es necesario)
        historialTotal.addAll(historialCambiosRepositorio.findByEntidadIdAndEntidad(pacienteId, "Paciente"));

// Ordenar por fecha de cambio desde el más reciente hasta el más antiguo
        return historialTotal.stream()
                .sorted(Comparator.comparing(HistorialCambios::getFechaCambio).reversed())
                .limit(100)  // Limitar a un máximo de 100 resultados
                .collect(Collectors.toList());

    }

    public void registrarCambio(String entidad, Long entidadId, String operacion, Object valorAnterior, Object valorNuevo) {
        try {
            String valorAnteriorStr = valorAnterior != null ? objectMapper.writeValueAsString(valorAnterior) : null;
            String valorNuevoStr = valorNuevo != null ? objectMapper.writeValueAsString(valorNuevo) : null;

            Map<String, ObjectNode> cambios = compararValores(valorAnteriorStr, valorNuevoStr);

            ObjectNode valorAnteriorFiltrado = cambios.get("valorAnterior");
            ObjectNode valorNuevoFiltrado = cambios.get("valorNuevo");

            if (valorAnteriorFiltrado.size() > 0 || valorNuevoFiltrado.size() > 0) {
                HistorialCambios historialCambios = new HistorialCambios();
                historialCambios.setEntidad(entidad);
                historialCambios.setEntidadId(entidadId);
                historialCambios.setFechaCambio(java.time.LocalDateTime.now());
                historialCambios.setOperacion(operacion);
                historialCambios.setValorAnterior(valorAnteriorFiltrado.toString());
                historialCambios.setValorNuevo(valorNuevoFiltrado.toString());

                historialCambiosRepositorio.save(historialCambios);
            }
        } catch (Exception e) {
            throw new RuntimeException("Error al registrar el cambio en el historial", e);
        }
    }

    private Map<String, ObjectNode> compararValores(String valorAnteriorStr, String valorNuevoStr) throws Exception {
        ObjectNode valorAnteriorNode = valorAnteriorStr != null ? (ObjectNode) objectMapper.readTree(valorAnteriorStr) : null;
        ObjectNode valorNuevoNode = valorNuevoStr != null ? (ObjectNode) objectMapper.readTree(valorNuevoStr) : null;

        ObjectNode valorAnteriorFiltrado = objectMapper.createObjectNode();
        ObjectNode valorNuevoFiltrado = objectMapper.createObjectNode();

        if (valorAnteriorNode != null && valorNuevoNode != null) {
            Iterator<String> fieldNames = valorAnteriorNode.fieldNames();
            while (fieldNames.hasNext()) {
                String field = fieldNames.next();
                JsonNode oldValue = valorAnteriorNode.get(field);
                JsonNode newValue = valorNuevoNode.get(field);

                if (newValue != null && !newValue.equals(oldValue)) {
                    valorAnteriorFiltrado.set(field, oldValue);
                    valorNuevoFiltrado.set(field, newValue);
                }
            }
        }

        Map<String, ObjectNode> result = new HashMap<>();
        result.put("valorAnterior", valorAnteriorFiltrado);
        result.put("valorNuevo", valorNuevoFiltrado);

        return result;
    }
}