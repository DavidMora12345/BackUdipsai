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

    @ElementCollection
    @CollectionTable(name = "InstitucionJornadas", joinColumns = @JoinColumn(name = "institucion_id"))
    @Column(name = "jornada")
    private List<Integer> jornadas = new ArrayList<>();

    @Column(name = "TipoInstitucion")
    private String tipoInstitucion;

    @Column(name = "InstitucionEstado")
    private Integer institucionEstado;

    public InstitucionEducativa() {
    }

    public void addJornada(Integer jornada) {
        this.jornadas.add(jornada);
    }

    public void removeJornada(Integer jornada) {
        this.jornadas.remove(jornada);
    }

    public List<Integer> getJornadas() {
        return this.jornadas;
    }

    public void setJornadas(List<Integer> jornadas) {
        this.jornadas = jornadas;
    }
}
