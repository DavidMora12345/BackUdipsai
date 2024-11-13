package com.test.TUdipsaiApi.dto;

import com.test.TUdipsaiApi.Model.Sede;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EspecialistasIdDTO {
    private String cedula;
    private Boolean especialistaEstado;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private Integer especialidadId;
    private Boolean esPasante;
    private String especialistaAsignado;
    private String contrasena;
    private LocalDate inicioPasantia;
    private LocalDate finPasantia;
    private String imagen;
    private Integer sedeId;
}
