package com.test.TUdipsaiApi.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class SeguimientoDTO {
    private Integer id;
    private EspecialistasDTO especialista;
    private PacienteDTO paciente;
    private Date fecha;
    private String observacion;
    private Integer estado;
}
