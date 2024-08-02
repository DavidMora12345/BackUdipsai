package com.test.TUdipsaiApi.Repository;

import com.test.TUdipsaiApi.Model.Asignacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AsignacionRepositorio extends JpaRepository<Asignacion, Long> {

    List<Asignacion> findByPasante_Cedula(String pasanteId);

    List<Asignacion> findByPasante_CedulaAndPaciente_Sede_Id(String pasanteId, Integer sedeId);
}
