package com.test.TUdipsaiApi.Repository;

import com.test.TUdipsaiApi.Model.Especialistas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EspecialistasRepositorio extends JpaRepository<Especialistas, String> {

    @Query("SELECT e FROM Especialistas e WHERE e.especialistaEstado = true")
    List<Especialistas> findAllByEstado();

    Especialistas findByCedula(String cedula);

    Especialistas findByCedulaAndContrasena(String cedula, String contrasena);

    @Query("SELECT e FROM Especialistas e WHERE e.especialistaEstado = true AND e.esPasante = false")
    List<Especialistas> findAllByEspecialistaEstadoTrueAndEsPasanteFalse();
}
