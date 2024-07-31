package com.test.TUdipsaiApi.Service;

import com.test.TUdipsaiApi.Model.HistorialCambios;
import com.test.TUdipsaiApi.Repository.HistorialCambiosRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistorialCambiosService {

    @Autowired
    private HistorialCambiosRepositorio historialCambiosRepositorio;

    public List<HistorialCambios> findAll() {
        return historialCambiosRepositorio.findAll();
    }
}
