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

    public void deleteById(String cedula) {
        especialistaRepositorio.deleteById(cedula);
    }


    

}
