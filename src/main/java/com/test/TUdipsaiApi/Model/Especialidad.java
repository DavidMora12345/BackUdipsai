package com.test.TUdipsaiApi.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "especialidad")
@Getter
@Setter
public class Especialidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String area;


    public Especialidad(int id) {
        this.id = (long) id;
    }

    public Especialidad() {
    }
}
