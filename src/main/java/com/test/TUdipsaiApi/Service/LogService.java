package com.test.TUdipsaiApi.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.TUdipsaiApi.Model.LogEntry;
import com.test.TUdipsaiApi.Repository.LogEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class LogService {

    @Autowired
    private LogEntryRepository logEntryRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public void logChange(String entidad, Long entidadId, String operacion, Object valorAnterior, Object valorNuevo) {
        LogEntry logEntry = new LogEntry();
        logEntry.setEntidad(entidad);
        logEntry.setEntidadId(entidadId);
        logEntry.setOperacion(operacion);
        logEntry.setFechaCambio(LocalDateTime.now());

        try {
            String valorAnteriorStr = valorAnterior != null ? objectToJson(valorAnterior) : null;
            String valorNuevoStr = valorNuevo != null ? objectToJson(valorNuevo) : null;
            logEntry.setValorAnterior(valorAnteriorStr);
            logEntry.setValorNuevo(valorNuevoStr);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        logEntryRepository.save(logEntry);
    }

    private String objectToJson(Object obj) throws JsonProcessingException {
        Map<String, Object> fieldMap = new HashMap<>();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                fieldMap.put(field.getName(), field.get(obj));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return objectMapper.writeValueAsString(fieldMap);
    }

    public void logError(String message, Exception e) {
        // Implementación de logging de errores
        System.err.println("Error: " + message);
        e.printStackTrace();
    }
}
