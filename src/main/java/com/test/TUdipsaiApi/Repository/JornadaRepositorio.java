package com.test.TUdipsaiApi.Repository;

import com.test.TUdipsaiApi.Model.Jornada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JornadaRepositorio extends JpaRepository<Jornada, Integer> {
    Optional<Jornada> findByNombreJornada(String nombreJornada);
    Optional<Jornada> findByNombreJornadaIgnoreCase(String nombreJornada);

}
