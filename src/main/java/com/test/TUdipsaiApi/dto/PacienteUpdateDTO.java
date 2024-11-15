package com.test.TUdipsaiApi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.test.TUdipsaiApi.Model.Sede;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PacienteUpdateDTO {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date fechaApertura;

    private Integer pacienteEstado;
    private String nombresApellidos;
    private String ciudad;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date fechaNacimiento;

    private String edad;
    private String cedula;
    private String domicilio;
    private String imagen;
    private String telefono;
    private String celular;
    private Integer institucionEducativa;
    private Integer jornada;
    private String proyecto;
    private String anioEducacion;
    private String paralelo;
    private String perteneceInclusion;
    private String tieneDiscapacidad;
    private boolean portadorCarnet;
    private String diagnostico;
    private String motivoConsulta;
    private String observaciones;
    private String tipoDiscapacidad;
    private String detalleDiscapacidad;
    private Integer porcentajeDiscapacidad;
    private Boolean perteneceAProyecto;
    private Integer sede;
    private Long fichaCompromiso;

}
