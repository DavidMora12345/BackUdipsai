package com.test.TUdipsaiApi.Repository;

import com.test.TUdipsaiApi.Model.Sede;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SedeRepositorio extends JpaRepository<Sede, Integer> {
    List<Sede> findByEstado(Integer estado);
}
