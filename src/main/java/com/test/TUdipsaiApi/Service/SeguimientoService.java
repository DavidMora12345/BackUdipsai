package com.test.TUdipsaiApi.Service;

import com.test.TUdipsaiApi.Model.Seguimiento;
import com.test.TUdipsaiApi.Repository.SeguimientoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeguimientoService {

    @Autowired
    private SeguimientoRepositorio seguimientoRepository;

    public List<Seguimiento> getAllSeguimientos() {
        return seguimientoRepository.findByEstado(1); // Solo devolver seguimientos activos
    }

    public Optional<Seguimiento> getSeguimientoById(int id) {
        return seguimientoRepository.findById(id);
    }

    public Seguimiento saveSeguimiento(Seguimiento seguimiento) {
        return seguimientoRepository.save(seguimiento);
    }

    public void deleteSeguimiento(int id) {
        Optional<Seguimiento> optionalSeguimiento = seguimientoRepository.findById(id);
        if (optionalSeguimiento.isPresent()) {
            Seguimiento seguimiento = optionalSeguimiento.get();
            seguimiento.setEstado(0); // Cambiar estado a inactivo
            seguimientoRepository.save(seguimiento);
        }
    }
}
