package com.test.TUdipsaiApi.Repository;

import com.test.TUdipsaiApi.Model.Especialidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EspecialidadRepositorio extends JpaRepository<Especialidad, Integer> {
}
