package com.test.TUdipsaiApi.dto;

import com.test.TUdipsaiApi.Model.InstitucionEducativa;
import com.test.TUdipsaiApi.Model.Jornada;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PacienteSinImagenDTO {
    private Integer id;
    private Date fechaApertura;
    private Integer pacienteEstado;
    private String nombresApellidos;
    private String ciudad;
    private Date fechaNacimiento;
    private String edad;
    private String cedula;
    private String domicilio;
    private String telefono;
    private String celular;
    private InstitucionEducativa institucionEducativa;
    private String tipoInstitucion;
    private String direccionInstitucion;
    private String proyecto;
    private Jornada jornada;
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
