package com.test.TUdipsaiApi.Repository;

import com.test.TUdipsaiApi.Model.Fonoaudiologia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FonoaudiologiaRepository extends JpaRepository<Fonoaudiologia, Integer> {
    List<Fonoaudiologia> findByPacienteIdAndEstado(int idPaciente, int estado);
    List<Fonoaudiologia> findByEstado(int estado);
}
