package com.test.TUdipsaiApi.Controller;


import com.test.TUdipsaiApi.Model.Paciente;
import com.test.TUdipsaiApi.Service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/pacientes")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> getPacienteById(@PathVariable Integer id){
        Optional<Paciente> detalleEntidad = pacienteService.getPacienteById(id);
        return detalleEntidad.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
