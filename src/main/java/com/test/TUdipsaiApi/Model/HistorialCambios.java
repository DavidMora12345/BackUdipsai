package com.test.TUdipsaiApi.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "historial_cambios")
@Getter
@Setter
public class HistorialCambios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "entidad")
    private String entidad;

    @Column(name = "entidadId")
    private Long entidadId;

    @Column(name = "fechaCambio")
    private LocalDateTime fechaCambio;

    @Column(name = "operacion")
    private String operacion;

    @Column(name = "valorAnterior")
    private String valorAnterior;

    @Column(name = "valorNuevo")
    private String valorNuevo;
}
