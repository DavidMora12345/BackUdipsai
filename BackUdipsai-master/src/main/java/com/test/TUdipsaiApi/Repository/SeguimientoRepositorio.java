package com.test.TUdipsaiApi.Repository;

import com.test.TUdipsaiApi.Model.Seguimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeguimientoRepositorio extends JpaRepository<Seguimiento, Integer> {
    List<Seguimiento> findByEstado(Integer estado);
    List<Seguimiento> findByPacienteIdAndEstado(Integer pacienteId, Integer estado);
}
