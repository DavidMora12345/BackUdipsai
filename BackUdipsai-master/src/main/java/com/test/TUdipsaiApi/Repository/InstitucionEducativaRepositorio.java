package com.test.TUdipsaiApi.Repository;

import com.test.TUdipsaiApi.Model.InstitucionEducativa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InstitucionEducativaRepositorio extends JpaRepository<InstitucionEducativa, Integer> {
    List<InstitucionEducativa> findByInstitucionEstado(int estado);
    Optional<InstitucionEducativa> findByNombreInstitucion(String nombreInstitucion);
    Optional<InstitucionEducativa> findByTipoInstitucion(String tipoInstitucion);
    Optional<InstitucionEducativa> findByDireccion(String direccion);
}
