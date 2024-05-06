package com.test.TUdipsaiApi.Repository;

import com.test.TUdipsaiApi.Model.Especialista;
import com.test.TUdipsaiApi.Model.Especialista;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para el manejo de especialistas, incluyendo la autenticación.
 * 
 * @author Ing.David Esteban Mora Cabrera
 * Date: 14/12/2023
 * Country: Cuenca-Ecuador
 */
@Repository
public interface EspecialistaRepositorio extends JpaRepository<Especialista, String> {
    
    // Método para encontrar un especialista por su cédula
    Optional<Especialista> findByCedula(String cedula);
}
