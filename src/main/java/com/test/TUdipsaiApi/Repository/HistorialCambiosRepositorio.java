package com.test.TUdipsaiApi.Repository;

import com.test.TUdipsaiApi.Model.HistorialCambios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistorialCambiosRepositorio extends JpaRepository<HistorialCambios, Long> {
}
