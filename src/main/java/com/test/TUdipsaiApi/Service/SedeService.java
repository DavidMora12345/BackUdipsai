package com.test.TUdipsaiApi.Service;

import com.test.TUdipsaiApi.Model.Sede;
import com.test.TUdipsaiApi.Repository.SedeRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SedeService {

    @Autowired
    private SedeRepositorio sedeRepositorio;

    public List<Sede> getAllActiveSedes() {
        return sedeRepositorio.findByEstado(1);
    }

    public Optional<Sede> getSedeById(Integer id) {
        return sedeRepositorio.findById(id);
    }

    public Sede saveOrUpdate(Sede sede) {
        sede.setNombre(sede.getNombre().toUpperCase());
        return sedeRepositorio.save(sede);
    }

    public void deleteSede(Integer id) {
        Optional<Sede> sedeOpt = sedeRepositorio.findById(id);
        sedeOpt.ifPresent(sede -> {
            sede.setEstado(0);
            sedeRepositorio.save(sede);
        });
    }
}
