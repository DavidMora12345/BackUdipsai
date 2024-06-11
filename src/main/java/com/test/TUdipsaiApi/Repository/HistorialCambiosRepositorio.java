package com.test.TUdipsaiApi.Repository;

import com.test.TUdipsaiApi.Model.HistorialCambios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistorialCambiosRepositorio extends JpaRepository<HistorialCambios, Integer> {
    List<HistorialCambios> findByEntidadId(Integer entidadId);
}
