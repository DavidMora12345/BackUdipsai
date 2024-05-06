/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.test.TUdipsaiApi.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.FetchType;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Ing.David Esteban Mora Cabrera
 * Date:14/12/2023
 * Country: Cuenca-Ecuador
 * Class for the management of specialists
 */
@Entity
@Table(name="especialista")
@Setter
@Getter
public class Especialista {
    @Id
    @Column(name= "cedula")
    private String cedula;

    @Column(name= "primerNombre")
    private String primerNombre;

    @Column(name= "segundoNombre")
    private String segundoNombre;

    @Column(name= "primerApellido")
    private String primerApellido;

    @Column(name= "segundoApellido")
    private String segundoApellido;

    // Esta columna es una clave foránea que hace referencia a una entidad de especialidad
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_especialidad", referencedColumnName = "id")
    private Especialidad especialidad;

    @Column(name= "esPasante")
    private Boolean esPasante;

    // Esta relación indica que un especialista puede ser asignado a otro especialista
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "especialistaAsignado", referencedColumnName = "cedula")
    private Especialista especialistaAsignado;

    @Column(name= "contrasena")
    private String contrasena;
}
