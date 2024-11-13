package com.test.TUdipsaiApi.Repository;

import com.test.TUdipsaiApi.Model.HistorialCambios;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistorialCambiosRepositorio extends JpaRepository<HistorialCambios, Long> {
    List<HistorialCambios> findByEntidadId(Long entidadId);
    List<HistorialCambios> findByEntidadIdAndEntidad(Long entidadId, String entidad);


}
