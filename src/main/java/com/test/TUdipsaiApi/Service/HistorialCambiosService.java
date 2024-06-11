package com.test.TUdipsaiApi.Service;

import com.test.TUdipsaiApi.Model.HistorialCambios;
import com.test.TUdipsaiApi.Repository.HistorialCambiosRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class HistorialCambiosService {

    @Autowired
    private HistorialCambiosRepositorio historialCambiosRepositorio;

    public void registrarCambio(String entidad, Integer entidadId, String campo, String valorAnterior, String valorNuevo) {
        HistorialCambios historialCambios = new HistorialCambios();
        historialCambios.setEntidad(entidad);
        historialCambios.setEntidadId(entidadId);
        historialCambios.setCampo(campo);
        historialCambios.setValorAnterior(valorAnterior);
        historialCambios.setValorNuevo(valorNuevo);
        historialCambios.setUsuario("Usuario");  // Puedes cambiar esto según tus necesidades
        historialCambios.setFechaCambio(new Date());
        historialCambiosRepositorio.save(historialCambios);
    }
}
