package com.test.TUdipsaiApi.Repository;

import com.test.TUdipsaiApi.Model.Jornada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JornadaRepositorio extends JpaRepository<Jornada, Integer> {
}
