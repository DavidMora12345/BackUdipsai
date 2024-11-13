package com.test.TUdipsaiApi.Repository;

import com.test.TUdipsaiApi.Model.Documento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentoRepositorio extends JpaRepository<Documento, Long> {
}
