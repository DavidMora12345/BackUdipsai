package com.test.TUdipsaiApi.Service;

import com.test.TUdipsaiApi.Model.Especialistas;
import com.test.TUdipsaiApi.Repository.EspecialistasRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EspecialistasService {

    @Autowired
    private EspecialistasRepositorio especialistasRepositorio;

    // Crear o actualizar un especialista
    public Especialistas saveOrUpdate(Especialistas especialista) {
        return especialistasRepositorio.save(especialista);
    }

    // Listar todos los especialistas
    public List<Especialistas> findAll() {
        return especialistasRepositorio.findAll();
    }

    // Listar todos los especialistas activos (estado = 1)
    public List<Especialistas> findAllActive() {
        return especialistasRepositorio.findAllByEstado();
    }

    // Buscar un especialista por su cédula
    public Optional<Especialistas> findByCedula(String cedula) {
        return especialistasRepositorio.findById(cedula);
    }

    // Eliminar un especialista (cambiar su estado a 0)
    public boolean deleteByCedula(String cedula) {
        Optional<Especialistas> especialistaOptional = especialistasRepositorio.findById(cedula);
        if (especialistaOptional.isPresent()) {
            Especialistas especialista = especialistaOptional.get();
            especialista.setEspecialistaEstado(false); // Cambiado a false
            especialistasRepositorio.save(especialista);
            return true;
        }
        return false;
    }

    // Login de un especialista
    public Especialistas login(String cedula, String contrasena) {
        return especialistasRepositorio.findByCedulaAndContrasena(cedula, contrasena);
    }
}
