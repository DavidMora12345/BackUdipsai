package com.test.TUdipsaiApi.Repository;

import com.test.TUdipsaiApi.Model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
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
}
