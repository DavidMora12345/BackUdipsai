/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.test.TUdipsaiApi.Controller;

import com.test.TUdipsaiApi.Model.Especialista;
import com.test.TUdipsaiApi.Service.EspecialistaService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/especialista")
@CrossOrigin(origins = "*")

public class EspecialistaController {

    @Autowired
    private EspecialistaService especialistaService;

@PostMapping("/login")
public ResponseEntity<String> login(@RequestBody Map<String, String> credentials) {
    String cedula = credentials.get("cedula");
    String contrasena = credentials.get("contrasena");

    boolean loginCorrecto = especialistaService.verificarCredenciales(cedula, contrasena);

    if (loginCorrecto) {
        return ResponseEntity.ok("Login exitoso.");
    } else {
        return ResponseEntity.status(401).body("Credenciales inválidas.");
    }
}

    @GetMapping
    public List<Especialista> getAllEspecialistas() {
        return especialistaService.findAll();
    }

    @GetMapping("/{cedula}")
    public ResponseEntity<Especialista> getEspecialistaById(@PathVariable String cedula) {
        Optional<Especialista> especialista = especialistaService.findById(cedula);
        return especialista.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Especialista createEspecialista(@RequestBody Especialista especialista) {
        return especialistaService.save(especialista);
    }

    @PutMapping("/{cedula}")
    public ResponseEntity<Especialista> updateEspecialista(@PathVariable String cedula, @RequestBody Especialista especialistaDetails) {
        Optional<Especialista> especialista = especialistaService.findById(cedula);
        if (especialista.isPresent()) {
            Especialista updatedEspecialista = especialista.get();
            updatedEspecialista.setPrimerNombre(especialistaDetails.getPrimerNombre());
            updatedEspecialista.setSegundoNombre(especialistaDetails.getSegundoNombre());
            updatedEspecialista.setPrimerApellido(especialistaDetails.getPrimerApellido());
            updatedEspecialista.setSegundoApellido(especialistaDetails.getSegundoApellido());
            updatedEspecialista.setEspecialidad(especialistaDetails.getEspecialidad());
            updatedEspecialista.setEsPasante(especialistaDetails.getEsPasante());
            updatedEspecialista.setEspecialistaAsignado(especialistaDetails.getEspecialistaAsignado());
            updatedEspecialista.setContrasena(especialistaDetails.getContrasena());
            if (especialistaDetails.getEsPasante() != null && especialistaDetails.getEsPasante()) {
                updatedEspecialista.setInicioPasantia(especialistaDetails.getInicioPasantia());
                updatedEspecialista.setFinPasantia(especialistaDetails.getFinPasantia());
            } else {
                updatedEspecialista.setInicioPasantia(null);
                updatedEspecialista.setFinPasantia(null);
            }
            updatedEspecialista.setImagen(especialistaDetails.getImagen());
            especialistaService.save(updatedEspecialista);
            return ResponseEntity.ok(updatedEspecialista);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{cedula}")
    public ResponseEntity<Void> deleteEspecialista(@PathVariable String cedula) {
        Optional<Especialista> especialista = especialistaService.findById(cedula);
        if (especialista.isPresent()) {
            especialistaService.deleteById(cedula);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }



}
