/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.test.TUdipsaiApi.Controller;

import com.test.TUdipsaiApi.Service.EspecialistaService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/especialista")
@CrossOrigin(origins = "*")

public class EspecialistaController {

    @Autowired
    private EspecialistaService especialistaService;

    /**
     * Endpoint para iniciar sesión de especialistas.
     *
     * @param cedula la cédula del especialista
     * @param contrasena la contraseña del especialista
     * @return ResponseEntity con el resultado de la operación
     */
@PostMapping("/login")
public ResponseEntity<String> login(@RequestBody Map<String, String> credentials) {
    String cedula = credentials.get("cedula");
    String contrasena = credentials.get("contrasena");
    // Asegúrate de que cedula y contrasena no sean null antes de continuar

    boolean loginCorrecto = especialistaService.verificarCredenciales(cedula, contrasena);

    if (loginCorrecto) {
        return ResponseEntity.ok("Login exitoso.");
    } else {
        return ResponseEntity.status(401).body("Credenciales inválidas.");
    }
}



}
