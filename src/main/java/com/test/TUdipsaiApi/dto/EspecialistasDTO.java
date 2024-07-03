package com.test.TUdipsaiApi.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EspecialistasDTO {
    private String cedula;
    private Boolean especialistaEstado;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private EspecialidadDTO especialidad;
    private Boolean esPasante;
    private String especialistaAsignado;
    private LocalDate inicioPasantia;
    private LocalDate finPasantia;
    private byte[] imagen;
}
