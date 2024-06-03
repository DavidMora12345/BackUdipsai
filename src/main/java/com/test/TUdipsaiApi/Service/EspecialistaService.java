package com.test.TUdipsaiApi.Service;

import com.test.TUdipsaiApi.Model.Especialista;
import com.test.TUdipsaiApi.Repository.EspecialistaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EspecialistaService {

    @Autowired
    private EspecialistaRepositorio especialistaRepositorio;

    public boolean verificarCredenciales(String cedula, String contrasena) {
        Optional<Especialista> especialistaOpt = especialistaRepositorio.findByCedula(cedula);

        if (especialistaOpt.isPresent()) {
            Especialista especialista = especialistaOpt.get();
            return contrasena.equals(especialista.getContrasena());
        }

        return false;
    }

    public List<Especialista> findAll() {
        return especialistaRepositorio.findAll();
    }

    public Optional<Especialista> findById(String cedula) {
        return especialistaRepositorio.findById(cedula);
    }

    public Especialista save(Especialista especialista) {
        return especialistaRepositorio.save(especialista);
    }

    public Optional<Especialista> deleteById(String cedula) {
        Optional<Especialista> optionalEspecialista = especialistaRepositorio.findById(cedula);
        if (optionalEspecialista.isPresent()) {
            Especialista especialista = optionalEspecialista.get();
            especialista.setEspecialistaEstado(0); // Cambia el estado a 0
            especialistaRepositorio.save(especialista);
            return Optional.of(especialista);
        } else {
            return Optional.empty();
        }
    }

    public List<Especialista> findByEspecialistaEstado(Integer especialistaEstado) {
        return especialistaRepositorio.findByEspecialistaEstado(especialistaEstado);
    }

    public List<Especialista> searchEspecialistas(String busqueda) {
        return especialistaRepositorio.searchEspecialistas(busqueda);
    }

    public Optional<Especialista> updateEspecialista(String cedula, Especialista especialistaDetails) {
        Optional<Especialista> optionalEspecialista = especialistaRepositorio.findById(cedula);
        if (optionalEspecialista.isPresent()) {
            Especialista especialista = optionalEspecialista.get();
            especialista.setPrimerNombre(especialistaDetails.getPrimerNombre());
            especialista.setSegundoNombre(especialistaDetails.getSegundoNombre());
            especialista.setPrimerApellido(especialistaDetails.getPrimerApellido());
            especialista.setSegundoApellido(especialistaDetails.getSegundoApellido());
            especialista.setEspecialidad(especialistaDetails.getEspecialidad());
            especialista.setEsPasante(especialistaDetails.getEsPasante());
            especialista.setEspecialistaAsignado(especialistaDetails.getEspecialistaAsignado());
            especialista.setContrasena(especialistaDetails.getContrasena());
            especialista.setEspecialistaEstado(especialistaDetails.getEspecialistaEstado());
            if (Boolean.TRUE.equals(especialistaDetails.getEsPasante())) {
                especialista.setInicioPasantia(especialistaDetails.getInicioPasantia());
                especialista.setFinPasantia(especialistaDetails.getFinPasantia());
            } else {
                especialista.setInicioPasantia(null);
                especialista.setFinPasantia(null);
            }
            especialista.setImagen(especialistaDetails.getImagen());
            especialistaRepositorio.save(especialista);
            return Optional.of(especialista);
        } else {
            return Optional.empty();
        }
    }
}
