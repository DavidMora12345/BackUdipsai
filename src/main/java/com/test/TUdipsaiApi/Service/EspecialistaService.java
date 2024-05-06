package com.test.TUdipsaiApi.Service;

import com.test.TUdipsaiApi.Model.Especialista;
import com.test.TUdipsaiApi.Repository.EspecialistaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Servicio para gestionar especialistas, incluyendo autenticación y otras operaciones relacionadas.
 */
@Service
public class EspecialistaService {

    @Autowired
    private EspecialistaRepositorio especialistaRepositorio;

    /**
     * Verifica las credenciales de un especialista.
     *
     * @param cedula la cédula del especialista
     * @param contrasena la contraseña del especialista
     * @return true si las credenciales son válidas, false en caso contrario
     */
    public boolean verificarCredenciales(String cedula, String contrasena) {
        Optional<Especialista> especialistaOpt = especialistaRepositorio.findByCedula(cedula);
        
        if (especialistaOpt.isPresent()) {
            Especialista especialista = especialistaOpt.get();
            // Verificar que la contraseña en texto plano coincida con la proporcionada
            return contrasena.equals(especialista.getContrasena());
        }
        
        return false;
    }
    
    // Aquí puedes agregar otros métodos relacionados con especialistas como crear, actualizar, eliminar, etc.
}
