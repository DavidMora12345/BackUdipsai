package com.test.TUdipsaiApi.Listener;

import com.test.TUdipsaiApi.Model.HistorialCambios;
import com.test.TUdipsaiApi.Model.HistorialCambiosAware;
import com.test.TUdipsaiApi.Repository.HistorialCambiosRepositorio;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Date;

@Component
public class AuditingEntityListener {

    private static HistorialCambiosRepositorio historialCambiosRepositorio;

    @Autowired
    public void init(HistorialCambiosRepositorio historialCambiosRepositorio) {
        AuditingEntityListener.historialCambiosRepositorio = historialCambiosRepositorio;
    }

    @PreUpdate
    @PrePersist
    private void beforeAnyUpdate(Object entity) throws IllegalAccessException {
        if (entity instanceof HistorialCambiosAware) {
            HistorialCambiosAware awareEntity = (HistorialCambiosAware) entity;
            Integer entidadId = awareEntity.getId();

            for (Field field : entity.getClass().getDeclaredFields()) {
                field.setAccessible(true);

                String campo = field.getName();
                Object valorNuevo = field.get(entity);

                // Obtener valor anterior
                Object valorAnterior = null;
                if (entidadId != null) {
                    Object entidadAnterior = historialCambiosRepositorio.findById(entidadId).orElse(null);
                    if (entidadAnterior != null) {
                        try {
                            Field oldField = entidadAnterior.getClass().getDeclaredField(campo);
                            oldField.setAccessible(true);
                            valorAnterior = oldField.get(entidadAnterior);
                        } catch (NoSuchFieldException e) {
                            // Manejar el caso donde el campo no existe en la entidad anterior
                            System.out.println("Campo no encontrado: " + campo);
                        }
                    }
                }

                if (valorAnterior != null && !valorAnterior.equals(valorNuevo)) {
                    HistorialCambios historialCambios = new HistorialCambios();
                    historialCambios.setEntidad(entity.getClass().getSimpleName());
                    historialCambios.setEntidadId(entidadId);
                    historialCambios.setCampo(campo);
                    historialCambios.setValorAnterior(valorAnterior != null ? valorAnterior.toString() : null);
                    historialCambios.setValorNuevo(valorNuevo != null ? valorNuevo.toString() : null);
                    historialCambios.setUsuario("TODO: Retrieve current user");
                    historialCambios.setFechaCambio(new Date());
                    historialCambiosRepositorio.save(historialCambios);
                }
            }
        }
    }
}
