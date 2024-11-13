package com.test.TUdipsaiApi.Repository;

import com.test.TUdipsaiApi.Model.Asignacion;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AsignacionRepositorio extends JpaRepository<Asignacion, Long> {

    List<Asignacion> findByPasante_Cedula(String pasanteId);

    List<Asignacion> findByPasante_CedulaAndPaciente_Sede_Id(String pasanteId, Integer sedeId);

    @Query("SELECT a FROM Asignacion a WHERE " +
            "(LOWER(CAST(a.paciente.id AS string)) LIKE LOWER(CONCAT('%', :busqueda, '%')) OR " +
            "LOWER(a.paciente.nombresApellidos) LIKE LOWER(CONCAT('%', :busqueda, '%')) OR " +
            "LOWER(a.paciente.cedula) LIKE LOWER(CONCAT('%', :busqueda, '%')) OR " +
            "LOWER(a.paciente.domicilio) LIKE LOWER(CONCAT('%', :busqueda, '%')) OR " +
            "LOWER(a.paciente.proyecto) LIKE LOWER(CONCAT('%', :busqueda, '%')) OR " +
            "LOWER(a.paciente.ciudad) LIKE LOWER(CONCAT('%', :busqueda, '%'))) AND " +
            "(a.paciente.sede.id = :sedeId OR :sedeId IS NULL) AND " +
            "a.pasante.cedula = :pasanteId " +
            "ORDER BY a.paciente.fechaApertura DESC")
    List<Asignacion> searchAsignaciones(@Param("busqueda") String busqueda, @Param("sedeId") Integer sedeId, @Param("pasanteId") String pasanteId, Pageable pageable);
}