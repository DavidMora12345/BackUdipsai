package com.test.TUdipsaiApi.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "InstitucionesEducativas")
@Setter
@Getter
public class InstitucionEducativa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "NombreInstitucion")
    private String nombreInstitucion;

    @Column(name = "Direccion")
    private String direccion;

    @Column(name = "Jornada")
    private String jornada;

    @Column(name = "TipoInstitucion")
    private String tipoInstitucion;

    @Column(name = "InstitucionEstado")
    private Integer institucionEstado;

    public InstitucionEducativa() {
    }

    public InstitucionEducativa(Integer id) {
        this.id = id;
    }

    public InstitucionEducativa(String nombreInstitucion) {
        this.nombreInstitucion = nombreInstitucion;
    }

    public InstitucionEducativa(String direccion, boolean isDireccion) {
        this.direccion = direccion;
    }

    public InstitucionEducativa(String jornada, int isJornada) {
        this.jornada = jornada;
    }

    public InstitucionEducativa(String tipoInstitucion, float isTipo) {
        this.tipoInstitucion = tipoInstitucion;
    }
}
