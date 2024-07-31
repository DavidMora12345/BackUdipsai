package com.test.TUdipsaiApi.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "especialista")
@Getter
@Setter
public class Especialistas {

    @Id
    @Column(name = "cedula")
    private String cedula;

    @Column(name = "especialista_estado")
    private Boolean especialistaEstado;

    @Column(name = "primerNombre")
    private String primerNombre;

    @Column(name = "segundoNombre")
    private String segundoNombre;

    @Column(name = "primerApellido")
    private String primerApellido;

    @Column(name = "segundoApellido")
    private String segundoApellido;

    @ManyToOne
    @JoinColumn(name = "id_especialidad")
    private Especialidad especialidad;

    @Column(name = "esPasante")
    private Boolean esPasante;

    @Column(name = "especialistaAsignado")
    private String especialistaAsignado;

    @Column(name = "contrasena")
    private String contrasena;

    @Column(name = "inicioPasantia")
    private LocalDate inicioPasantia;

    @Column(name = "finPasantia")
    private LocalDate finPasantia;

    @Lob
    @Column(name = "imagen", columnDefinition = "LONGBLOB")
    private byte[] imagen;

    @ManyToOne
    @JoinColumn(name = "sede_id", referencedColumnName = "id")
    private Sede sede;

}
