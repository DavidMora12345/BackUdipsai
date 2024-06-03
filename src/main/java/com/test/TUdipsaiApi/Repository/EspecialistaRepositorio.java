package com.test.TUdipsaiApi.Repository;

import com.test.TUdipsaiApi.Model.Especialista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio para el manejo de especialistas, incluyendo la autenticación.
 *
 * Author: Ing.David Esteban Mora Cabrera
 * Date: 14/12/2023
 * Country: Cuenca-Ecuador
 */
@Repository
public interface EspecialistaRepositorio extends JpaRepository<Especialista, String> {

    // Método para encontrar un especialista por su cédula
    Optional<Especialista> findByCedula(String cedula);

    // Método para obtener todos los especialistas con un estado específico
    List<Especialista> findByEspecialistaEstado(Integer especialistaEstado);

    @Query("SELECT e FROM Especialista e WHERE " +
            "(LOWER(CAST(e.cedula AS string)) LIKE LOWER(CONCAT('%', :busqueda, '%')) OR " +
            "LOWER(e.primerNombre) LIKE LOWER(CONCAT('%', :busqueda, '%')) OR " +
            "LOWER(e.segundoNombre) LIKE LOWER(CONCAT('%', :busqueda, '%')) OR " +
            "LOWER(e.primerApellido) LIKE LOWER(CONCAT('%', :busqueda, '%')) OR " +
            "LOWER(e.segundoApellido) LIKE LOWER(CONCAT('%', :busqueda, '%'))) AND " +
            "e.especialistaEstado = 1 " +
            "ORDER BY e.cedula")
    List<Especialista> searchEspecialistas(@Param("busqueda") String busqueda);
}
