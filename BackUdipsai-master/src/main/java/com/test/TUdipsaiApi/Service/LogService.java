package com.test.TUdipsaiApi.Service;

import com.test.TUdipsaiApi.Model.LogEntry;
import com.test.TUdipsaiApi.Repository.LogEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LogService {

    @Autowired
    private LogEntryRepository logEntryRepository;

    public void logChange(String entidad, Long entidadId, String operacion, String valorAnterior, String valorNuevo) {
        LogEntry logEntry = new LogEntry();
        logEntry.setEntidad(entidad);
        logEntry.setEntidadId(entidadId);
        logEntry.setOperacion(operacion);
        logEntry.setValorAnterior(valorAnterior);
        logEntry.setValorNuevo(valorNuevo);
        logEntry.setFechaCambio(LocalDateTime.now());

        logEntryRepository.save(logEntry);
    }

    public void logChange1(String entity, Long entityId, String action, String oldValue, String newValue) {
        // Implementación de logging de cambios
        System.out.println("Change logged: " + entity + ", " + entityId + ", " + action + ", " + oldValue + ", " + newValue);
    }

    public void logError(String message, Exception e) {
        // Implementación de logging de errores
        System.err.println("Error: " + message);
        e.printStackTrace();
    }
}
