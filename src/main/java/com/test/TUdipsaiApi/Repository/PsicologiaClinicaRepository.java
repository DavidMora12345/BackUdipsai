package com.test.TUdipsaiApi.Repository;

import com.test.TUdipsaiApi.Model.PsicologiaClinica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PsicologiaClinicaRepository extends JpaRepository<PsicologiaClinica, Integer> {
    List<PsicologiaClinica> findByEstado(int estado);
    List<PsicologiaClinica> findByPacienteId(int idPaciente);
}
