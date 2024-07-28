package com.test.TUdipsaiApi.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "permisos")
@Getter
@Setter
public class Permisos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean especialistas;
    private Boolean institucionesEducativas;
    private Boolean historiaClinica;
    private Boolean fonoAudiologia;
    private Boolean psicologiaClinica;
    private Boolean psicologiaEducativa;
    private Boolean pacientes;

}
