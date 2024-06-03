package com.test.TUdipsaiApi.Repository;

import com.test.TUdipsaiApi.Model.Paciente;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Repositorio para el manejo de DetalleEntidad.
 *
 * Author: Ing.David Esteban Mora Cabrera
 * Date: 14/12/2023
 * Country: Cuenca-Ecuador
 */

@Repository
public interface PacienteRepositorio  extends JpaRepository<Paciente, Integer> {

    //Metodo para encontrar paciente por su ID
    Optional<Paciente> findById(Integer id);

    //Metodo para obtener todos los datos de paciente
    List<Paciente> findByPacienteEstado(Integer pacienteEstado);
    @Query("SELECT p FROM Paciente p WHERE " +
            "(LOWER(CAST(p.id AS string)) LIKE LOWER(CONCAT('%', :busqueda, '%')) OR " +
            "LOWER(p.nombresApellidos) LIKE LOWER(CONCAT('%', :busqueda, '%')) OR " +
            "LOWER(p.domicilio) LIKE LOWER(CONCAT('%', :busqueda, '%')) OR " +
            "LOWER(p.proyecto) LIKE LOWER(CONCAT('%', :busqueda, '%')) OR " +
            "LOWER(p.cedula) LIKE LOWER(CONCAT('%', :busqueda, '%')) OR " +
            "LOWER(p.ciudad) LIKE LOWER(CONCAT('%', :busqueda, '%'))) AND " +
            "p.pacienteEstado = 1"+
            "ORDER BY p.fecha_apertura")
    List<Paciente> searchPacientes(@Param("busqueda") String busqueda, Pageable pageable);
}
