package com.test.TUdipsaiApi.Repository;

import com.test.TUdipsaiApi.Model.InstitucionEducativa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstitucionEducativaRepositorio extends JpaRepository<InstitucionEducativa, Integer> {
    List<InstitucionEducativa> findByInstitucionEstado(Integer estado);
}
