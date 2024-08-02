package com.test.TUdipsaiApi.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.test.TUdipsaiApi.Model.HistorialCambios;
import com.test.TUdipsaiApi.Repository.HistorialCambiosRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class HistorialCambiosService {

    @Autowired
    private HistorialCambiosRepositorio historialCambiosRepositorio;

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
