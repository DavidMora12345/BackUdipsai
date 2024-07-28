package com.test.TUdipsaiApi.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="Jornada")
@Setter
@Getter
public class Jornada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id")
    private Integer id;

    @Column(name= "nombre_jornada")
    private String nombreJornada;

    @Column(name= "estado_jornada")
    private Integer estadoJornada;
}
