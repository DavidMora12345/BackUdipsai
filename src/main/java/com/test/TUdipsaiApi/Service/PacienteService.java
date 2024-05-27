package com.test.TUdipsaiApi.Service;


import com.test.TUdipsaiApi.Model.Paciente;
import com.test.TUdipsaiApi.Repository.PacienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PacienteService {
    @Autowired
    private PacienteRepositorio pacienteRepositorio;

    //Metodo para obtener datos del paciente

    public Optional<Paciente> getPacienteById(Integer id){
        return pacienteRepositorio.findById(id);
    }



}
