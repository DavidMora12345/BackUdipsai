package com.test.TUdipsaiApi.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class PacienteDTO {

    private Integer id;
    private Date fechaApertura;
    private Integer pacienteEstado;
    private String nombresApellidos;
    private String ciudad;
    private Date fechaNacimiento;
    private String edad;
    private String cedula;
    private String domicilio;
    private byte[] imagen;
    private String telefono;
    private String celular;
    private Integer institucionEducativa;
    private String proyecto;
    private Integer jornada;
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
}
