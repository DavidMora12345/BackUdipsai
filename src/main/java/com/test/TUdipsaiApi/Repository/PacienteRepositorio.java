package com.test.TUdipsaiApi.Repository;

import com.test.TUdipsaiApi.Model.Paciente;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PacienteRepositorio extends JpaRepository<Paciente, Integer> {

    Optional<Paciente> findById(Integer id);

    List<Paciente> findByPacienteEstado(Integer pacienteEstado);

    @Query("SELECT p FROM Paciente p WHERE " +
            "(LOWER(CAST(p.id AS string)) LIKE LOWER(CONCAT('%', :busqueda, '%')) OR " +
            "LOWER(p.nombresApellidos) LIKE LOWER(CONCAT('%', :busqueda, '%')) OR " +
            "LOWER(p.domicilio) LIKE LOWER(CONCAT('%', :busqueda, '%')) OR " +
            "LOWER(p.proyecto) LIKE LOWER(CONCAT('%', :busqueda, '%')) OR " +
            "LOWER(p.cedula) LIKE LOWER(CONCAT('%', :busqueda, '%')) OR " +
            "LOWER(p.ciudad) LIKE LOWER(CONCAT('%', :busqueda, '%'))) AND " +
            "p.sede.id = :sedeId AND " +
            "p.pacienteEstado = 1 " +
            "ORDER BY p.fechaApertura DESC")
    List<Paciente> searchPacientes(@Param("busqueda") String busqueda, @Param("sedeId") Integer sedeId, Pageable pageable);

    @Query("SELECT p FROM Paciente p WHERE p.sede.id = :sedeId")
    List<Paciente> findBySede(@Param("sedeId") Integer sedeId);

    @Query("SELECT p FROM Paciente p WHERE LOWER(p.nombresApellidos) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(p.cedula) LIKE LOWER(CONCAT('%', :search, '%'))")
    List<Paciente> findBySearchCriteria(@Param("search") String search);

    @Query("SELECT p FROM Paciente p WHERE (LOWER(p.nombresApellidos) LIKE LOWER(CONCAT('%', :search, '%')) OR LOWER(p.cedula) LIKE LOWER(CONCAT('%', :search, '%'))) AND p.sede.id = :sedeId")
    List<Paciente> findBySearchCriteriaAndSede(@Param("search") String search, @Param("sedeId") Integer sedeId);
}
