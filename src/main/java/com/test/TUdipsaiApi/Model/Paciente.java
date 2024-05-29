package com.test.TUdipsaiApi.Model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Lob;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

/**
 * Class for managing a detailed entity with various attributes.
 * Author: Ing.David Esteban Mora Cabrera
 * Date: 14/12/2023
 * Country: Cuenca-Ecuador
 */
@Entity
@Table(name="Paciente")
@Setter
@Getter


public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id")
    private Integer id;

    @Column(name= "fecha_apertura")
    private Date fechaApertura;

    @Column(name= "paciente_estado")
    private Integer pacienteEstado;

    @Column(name= "nombres_apellidos")
    private String nombresApellidos;

    @Column(name= "ciudad")
    private String ciudad;

    @Column(name= "fecha_nacimiento")
    private Date fechaNacimiento;

    @Column(name= "edad")
    private String edad;

    @Column(name= "cedula")
    private String cedula;

    @Column(name= "domicilio")
    private String domicilio;

    @Lob
    @Column(name = "imagen", columnDefinition = "LONGBLOB")
    private byte[] imagen;


    @Column(name= "telefono")
    private String telefono;

    @Column(name= "celular")
    private String celular;

    @Column(name= "institucion_educativa")
    private String institucionEducativa;

    @Column(name= "tipo_institucion")
    private String tipoInstitucion;

    @Column(name= "proyecto")
    private String proyecto;

    @Column(name= "jornada")
    private String jornada;

    @Column(name= "direccion_institucion")
    private String direccionInstitucion;

    @Column(name= "anio_educacion")
    private String anioEducacion;

    @Column(name= "paralelo")
    private String paralelo;

    @Column(name= "pertenece_inclusion")
    private String perteneceInclusion;

    @Column(name= "tiene_discapacidad")
    private String tieneDiscapacidad;

    @Column(name= "portador_carnet")
    private boolean portadorCarnet;

    @Column(name= "diagnostico")
    private String diagnostico;

    @Column(name= "motivo_consulta")
    private String motivoConsulta;

    @Column(name= "observaciones")
    private String observaciones;
}
