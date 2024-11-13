package com.test.TUdipsaiApi.Model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "institucioneseducativas")
@Getter
@Setter
public class InstitucionEducativa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "NombreInstitucion")
    private String nombreInstitucion;

    @Column(name = "Direccion")
    private String direccion;

    @Column(name = "TipoInstitucion")
    private String tipoInstitucion;

    @Column(name = "InstitucionEstado")
    private Integer institucionEstado;

    @Column(name = "Jornada")
    private String jornada;


}
