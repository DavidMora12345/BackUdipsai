package com.test.TUdipsaiApi.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "psicologia_educativa")
@Getter
@Setter
public class PsicologiaEducativa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_paciente", referencedColumnName = "id")
    private Paciente paciente;

    @Column(name = "asignaturas_gustan")
    private String asignaturasGustan;

    @Column(name = "asignaturas_disgustan")
    private String asignaturasDisgustan;

    @Enumerated(EnumType.STRING)
    @Column(name = "relacion_docentes")
    private RelacionDocentes relacionDocentes;

    @Column(name = "causa_relacion_docentes")
    private String causaRelacionDocentes;

    @Column(name = "gusta_ir_institucion")
    private Boolean gustaIrInstitucion;

    @Column(name = "causa_gusta_ir_institucion")
    private String causaGustaIrInstitucion;

    @Enumerated(EnumType.STRING)
    @Column(name = "relacion_con_grupo")
    private RelacionConGrupo relacionConGrupo;

    @Column(name = "causa_relacion_con_grupo")
    private String causaRelacionConGrupo;

    @Column(name = "inclusion_educativa")
    private Boolean inclusionEducativa;

    @Column(name = "causa_inclusion_educativa")
    private String causaInclusionEducativa;

    @Column(name = "adaptaciones_curriculares")
    private Boolean adaptacionesCurriculares;

    @Column(name = "grado_adaptacion")
    private String gradoAdaptacion;

    @Column(name = "especifique_asignaturas")
    private String especifiqueAsignaturas;

    @Column(name = "cdi")
    private Boolean cdi;

    @Column(name = "cdi_edad")
    private Integer cdiEdad;

    @Column(name = "inicial1")
    private Boolean inicial1;

    @Column(name = "inicial1_edad")
    private Integer inicial1Edad;

    @Column(name = "inicial2")
    private Boolean inicial2;

    @Column(name = "inicial2_edad")
    private Integer inicial2Edad;

    @Column(name = "1ro_egb")
    private Boolean primerEGB;

    @Column(name = "edad_1ro_egb")
    private Integer edad1roEGB;

    @Column(name = "perdida_anio")
    private Boolean perdidaAnio;

    @Column(name = "grado_causa_perdida_anio")
    private String gradoCausaPerdidaAnio;

    @Column(name = "desercion_escolar")
    private Boolean desercionEscolar;

    @Column(name = "grado_causa_desercion_escolar")
    private String gradoCausaDesercionEscolar;

    @Column(name = "cambio_institucion")
    private Boolean cambioInstitucion;

    @Column(name = "grado_causa_cambio_institucion")
    private String gradoCausaCambioInstitucion;

    @Column(name = "problemas_aprendizaje")
    private Boolean problemasAprendizaje;

    @Lob
    @Column(name = "problemas_aprendizaje_especificar", columnDefinition = "TEXT")
    private String problemasAprendizajeEspecificar;

    @Column(name = "evaluacion_psicologica_u_otros_anterior")
    private Boolean evaluacionPsicologicaUOtrosAnterior;

    @Lob
    @Column(name = "causa_evaluacion_psicologica_u_otros_anterior", columnDefinition = "TEXT")
    private String causaEvaluacionPsicologicaUOtrosAnterior;


    @Column(name = "recibe_apoyo")
    private Boolean recibeApoyo;

    @Column(name = "causa_lugar_tiempo_recibe_apoyo")
    private String causaLugarTiempoRecibeApoyo;

    @Enumerated(EnumType.STRING)
    @Column(name = "aprovechamiento_general")
    private AprovechamientoGeneral aprovechamientoGeneral;

    @Lob
    @Column(name = "actividad_escolar", columnDefinition = "TEXT")
    private String actividadEscolar;

    @Column(name = "estado", nullable = false, columnDefinition = "int default 1")
    private int estado;

    @Lob
    @Column(name = "observaciones", columnDefinition = "TEXT") // Campo nuevo
    private String observaciones;

    // Constructor sin argumentos necesario para JPA
    public PsicologiaEducativa() {
    }

    // Constructor de copia
    public PsicologiaEducativa(PsicologiaEducativa otra) {
        this.id = otra.id;
        this.paciente = otra.paciente;
        this.asignaturasGustan = otra.asignaturasGustan;
        this.asignaturasDisgustan = otra.asignaturasDisgustan;
        this.relacionDocentes = otra.relacionDocentes;
        this.causaRelacionDocentes = otra.causaRelacionDocentes;
        this.gustaIrInstitucion = otra.gustaIrInstitucion;
        this.causaGustaIrInstitucion = otra.causaGustaIrInstitucion;
        this.relacionConGrupo = otra.relacionConGrupo;
        this.causaRelacionConGrupo = otra.causaRelacionConGrupo;
        this.inclusionEducativa = otra.inclusionEducativa;
        this.causaInclusionEducativa = otra.causaInclusionEducativa;
        this.adaptacionesCurriculares = otra.adaptacionesCurriculares;
        this.gradoAdaptacion = otra.gradoAdaptacion;
        this.especifiqueAsignaturas = otra.especifiqueAsignaturas;
        this.cdi = otra.cdi;
        this.cdiEdad = otra.cdiEdad;
        this.inicial1 = otra.inicial1;
        this.inicial1Edad = otra.inicial1Edad;
        this.inicial2 = otra.inicial2;
        this.inicial2Edad = otra.inicial2Edad;
        this.primerEGB = otra.primerEGB;
        this.edad1roEGB = otra.edad1roEGB;
        this.perdidaAnio = otra.perdidaAnio;
        this.gradoCausaPerdidaAnio = otra.gradoCausaPerdidaAnio;
        this.desercionEscolar = otra.desercionEscolar;
        this.gradoCausaDesercionEscolar = otra.gradoCausaDesercionEscolar;
        this.cambioInstitucion = otra.cambioInstitucion;
        this.gradoCausaCambioInstitucion = otra.gradoCausaCambioInstitucion;
        this.problemasAprendizaje = otra.problemasAprendizaje;
        this.problemasAprendizajeEspecificar = otra.problemasAprendizajeEspecificar;
        this.evaluacionPsicologicaUOtrosAnterior = otra.evaluacionPsicologicaUOtrosAnterior;
        this.causaEvaluacionPsicologicaUOtrosAnterior = otra.causaEvaluacionPsicologicaUOtrosAnterior;
        this.recibeApoyo = otra.recibeApoyo;
        this.causaLugarTiempoRecibeApoyo = otra.causaLugarTiempoRecibeApoyo;
        this.aprovechamientoGeneral = otra.aprovechamientoGeneral;
        this.actividadEscolar = otra.actividadEscolar;
        this.estado = otra.estado;
        this.observaciones = otra.observaciones;
    }

    // Enums anidados
    public enum RelacionDocentes {
        BUENA, MALA, REGULAR, DEFICIENTE;
    }

    public enum RelacionConGrupo {
        BUENA, MALA, REGULAR, DEFICIENTE;
    }

    public enum AprovechamientoGeneral {
        BUENO, MALO, REGULAR, DEFICIENTE;
    }
}
