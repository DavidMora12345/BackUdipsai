package com.test.TUdipsaiApi.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TestDTO {

    private Long id;
    private PacienteDTO paciente;
    private EspecialistasDTO especialista;
    private String nombreArchivo;
    private Date fecha;
    private Integer activo;
    private Long documentoId;
    private byte[] contenido;
}
