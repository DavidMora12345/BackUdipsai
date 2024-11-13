package com.test.TUdipsaiApi.Repository;

import com.test.TUdipsaiApi.Model.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestRepositorio extends JpaRepository<Test, Long> {
    List<Test> findByActivo(Integer activo);
    List<Test> findByPacienteIdAndActivo(Long pacienteId, Integer activo);
    List<Test> findByPacienteIdAndEspecialistaCedulaAndActivo(Long pacienteId, String cedula, Integer activo);
}
