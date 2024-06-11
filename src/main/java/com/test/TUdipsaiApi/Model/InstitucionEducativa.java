package com.test.TUdipsaiApi.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

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


    @Column(name = "TipoInstitucion")
    private String tipoInstitucion;

    @Column(name = "InstitucionEstado")
    private Integer institucionEstado;

    public InstitucionEducativa() {
    }


}
