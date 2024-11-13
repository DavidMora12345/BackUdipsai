package com.test.TUdipsaiApi.Repository;

import com.test.TUdipsaiApi.Model.FichaMedica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FichaMedicaRepository extends JpaRepository<FichaMedica, Integer> {
    List<FichaMedica> findByEstado(int estado);
    List<FichaMedica> findByPacienteId(int idPaciente);
}
