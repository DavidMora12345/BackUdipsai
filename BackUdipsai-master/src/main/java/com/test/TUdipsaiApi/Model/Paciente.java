package com.test.TUdipsaiApi.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "Paciente")
@Setter
@Getter
public class Paciente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "fecha_apertura")
    private Date fechaApertura;

    @Column(name = "paciente_estado")
    private Integer pacienteEstado;

    @Column(name = "nombres_apellidos")
    private String nombresApellidos;

    @Column(name = "ciudad")
    private String ciudad;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Column(name = "fecha_nacimiento")
    private Date fechaNacimiento;

    @Column(name = "edad")
    private String edad;

    @Column(name = "cedula")
    private String cedula;

    @Column(name = "domicilio")
    private String domicilio;

    @Lob
    @Column(name = "imagen", columnDefinition = "LONGBLOB")
    private byte[] imagen;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "celular")
    private String celular;

    @ManyToOne
    @JoinColumn(name = "id_institucion_educativa")
    private InstitucionEducativa institucionEducativa;

    @Column(name = "proyecto")
    private String proyecto;

    @ManyToOne
    @JoinColumn(name = "jornada_id")
    private Jornada jornada;

    @Column(name = "anio_educacion")
    private String anioEducacion;

    @Column(name = "paralelo")
    private String paralelo;

    @Column(name = "pertenece_inclusion")
    private String perteneceInclusion;

    @Column(name = "tiene_discapacidad")
    private String tieneDiscapacidad;

    @Column(name = "portador_carnet")
    private boolean portadorCarnet;

    @Column(name = "diagnostico")
    private String diagnostico;

    @Column(name = "motivo_consulta")
    private String motivoConsulta;

    @Column(name = "observaciones")
    private String observaciones;

    @Column(name = "tipo_discapacidad")
    private String tipoDiscapacidad;

    @Column(name = "detalle_discapacidad")
    private String detalleDiscapacidad;

    @Column(name = "porcentaje_discapacidad")
    private Integer porcentajeDiscapacidad;

    @Column(name = "pertenece_a_proyecto")
    private Boolean perteneceAProyecto;

    @ManyToOne
    @JoinColumn(name = "ficha_diagnostica_id", referencedColumnName = "id")
    private Documento fichaDiagnostica;

    @Column(name = "sede")
    private String sede;
}
