package com.test.TUdipsaiApi.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.test.TUdipsaiApi.Model.Especialidad;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name="especialista")
@Setter
@Getter
public class Especialista {
    @Id
    @Column(name= "cedula")
    private String cedula;

    @Column(name= "especialista_estado")
    private Integer especialistaEstado;

    @Column(name= "primerNombre")
    private String primerNombre;

    @Column(name= "segundoNombre")
    private String segundoNombre;

    @Column(name= "primerApellido")
    private String primerApellido;

    @Column(name= "segundoApellido")
    private String segundoApellido;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_especialidad", referencedColumnName = "id")
    private Especialidad especialidad;

    @Column(name= "esPasante")
    private Boolean esPasante;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "especialistaAsignado", referencedColumnName = "cedula")
    private Especialista especialistaAsignado;

    @Column(name= "contrasena")
    private String contrasena;

    @Column(name= "inicioPasantia")
    private LocalDate inicioPasantia;

    @Column(name= "finPasantia")
    private LocalDate finPasantia;

    @Lob
    @Column(name= "imagen", columnDefinition="LONGBLOB")
    private byte[] imagen;
}
