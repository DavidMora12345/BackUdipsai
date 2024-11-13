package com.test.TUdipsaiApi.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.math.BigDecimal;

@Entity
@Table(name = "ficha_medica")
@Getter
@Setter
public class FichaMedica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_paciente", referencedColumnName = "id")
    private Paciente paciente;

    @Column(name = "estado", nullable = false, columnDefinition = "int default 1")
    private int estado;

    @Column(name = "fuente_de_informacion")
    private String fuenteDeInformacion;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Lob
    @Column(name = "motivo_consulta", columnDefinition = "TEXT")
    private String motivoConsulta;

    @Enumerated(EnumType.STRING)
    @Column(name = "el_estudiante_vive_con")
    private ElEstudianteViveCon elEstudianteViveCon;

    @Column(name = "el_estudiante_vive_con_otro")
    private String elEstudianteViveConOtro;

    // Campos del padre
    @Column(name = "nombre_del_padre")
    private String nombreDelPadre;

    @Column(name = "edad_padre")
    private Integer edadPadre;

    @Column(name = "ocupacion_padre")
    private String ocupacionPadre;

    @Column(name = "instruccion_padre")
    private String instruccionPadre;

    @Column(name = "estado_civil_padre")
    private String estadoCivilPadre;

    @Column(name = "lugar_residencia_padre")
    private String lugarResidenciaPadre;

    // Campos de la madre
    @Column(name = "nombre_de_la_madre")
    private String nombreDeLaMadre;

    @Column(name = "edad_madre")
    private Integer edadMadre;

    @Column(name = "ocupacion_madre")
    private String ocupacionMadre;

    @Column(name = "instruccion_madre")
    private String instruccionMadre;

    @Column(name = "estado_civil_madre")
    private String estadoCivilMadre;

    @Column(name = "lugar_residencia_madre")
    private String lugarResidenciaMadre;

    @Column(name = "numero_hermanos")
    private Integer numeroHermanos;

    @Column(name = "lugar_que_ocupa")
    private String lugarQueOcupa;

    @Column(name = "direccion_domiciliaria")
    private String direccionDomiciliaria;

    @Column(name = "viven_juntos")
    private Boolean vivenJuntos;

    @Column(name = "otros_compromisos")
    private String otrosCompromisos;

    @Lob
    @Column(name = "genograma_familiar", columnDefinition = "LONGBLOB")
    private byte[] genogramaFamiliar;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_familia")
    private TipoFamilia tipoFamilia;

    @Column(name = "hijos_otros_familiares_viven_casa")
    private String hijosOtrosFamiliaresVivenCasa;

    @Column(name = "embarazo_deseado")
    private Boolean embarazoDeseado;

    @Column(name = "control_embarazo")
    private Boolean controlEmbarazo;

    @Column(name = "causa_control_embarazo")
    private String causaControlEmbarazo;

    @Column(name = "enfermedades_madre")
    private String enfermedadesMadre;

    @Column(name = "consumo_medicamentos_o_exposicion_sustancias_toxicas")
    private String consumoMedicamentosOExposicionSustanciasToxicas;

    @Column(name = "presento_amenaza_aborto")
    private Boolean presentoAmenazaAborto;

    @Column(name = "mes_amenaza_aborto")
    private String mesAmenazaAborto;

    @Column(name = "causa_amenaza_aborto")
    private String causaAmenazaAborto;

    @Column(name = "estado_emocional")
    private String estadoEmocional;

    @Enumerated(EnumType.STRING)
    @Column(name = "donde_nacio_ninio")
    private DondeNacioNinio dondeNacioNinio;

    @Column(name = "ciudad_nacimiento_ninio")
    private String ciudadNacimientoNinio;

    @Enumerated(EnumType.STRING)
    @Column(name = "duracion_embarazo")
    private DuracionEmbarazo duracionEmbarazo;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_parto")
    private TipoParto tipoParto;

    @Enumerated(EnumType.STRING)
    @Column(name = "parto_segun_el_comienzo")
    private PartoSegunElComienzo partoSegunElComienzo;

    @Enumerated(EnumType.STRING)
    @Column(name = "parto_segun_finalizacion")
    private PartoSegunFinalizacion partoSegunFinalizacion;

    @Column(name = "lloro_al_nacer")
    private Boolean lloroAlNacer;

    @Column(name = "peso_al_nacer", precision = 5, scale = 2)
    private BigDecimal pesoAlNacer;

    @Column(name = "talla_al_nacer", precision = 5, scale = 2)
    private BigDecimal tallaAlNacer;


    @Column(name = "presentoAnoxia_al_nacer")
    private Boolean presentoAnoxiaAlNacer;

    @Column(name = "presentoHipoxia_al_nacer")
    private Boolean presentoHipoxiaAlNacer;

    @Column(name = "presentoIctericia_al_nacer")
    private Boolean presentoIctericiaAlNacer;

    @Column(name = "presentoCianosis_al_nacer")
    private Boolean presentoCianosisAlNacer;

    @Column(name = "malformacion_congenita")
    private String malformacionCongenita;

    @Column(name = "problemas_de_alimentacion")
    private String problemasDeAlimentacion;

    @Column(name = "complicaciones_en_el_parto")
    private Boolean complicacionesEnElParto;

    @Column(name = "cual_complicacion_parto")
    private String cualComplicacionParto;

    @Column(name = "estuvo_en_incubadora")
    private Boolean estuvoEnIncubadora;

    @Column(name = "tiempo_estuvo_en_incubadora")
    private String tiempoEstuvoEnIncubadora;

    @Column(name = "causa_estuvo_en_incubadora")
    private String causaEstuvoEnIncubadora;

    @Column(name = "esquema_vacunacion_completo")
    private Boolean esquemaVacunacionCompleto;

    @Column(name = "control_cefalico")
    private String controlCefalico;

    @Column(name = "sedestacion")
    private String sedestacion;

    @Column(name = "hipedestacion")
    private String hipedestacion;

    @Column(name = "camina_con_apoyo")
    private String caminaConApoyo;

    @Column(name = "camina_solo")
    private String caminaSolo;

    @Column(name = "sube_escaleras")
    private String subeEscaleras;

    @Column(name = "control_esfinteres")
    private String controlEsfinteres;

    @Column(name = "salta")
    private String salta;

    @Column(name = "corre")
    private String corre;

    @Column(name = "gateo")
    private String gateo;

    @Column(name = "prefiere_mano_izquierda_derecha")
    private String prefiereManoIzquierdaDerecha;

    @Column(name = "cae_o_perde_equilibrio_facilmente")
    private String caeOPerdeEquilibrioFacilmente;

    @Column(name = "dejo_pecho_materno")
    private String dejoPechoMaterno;

    @Column(name = "biberon")
    private String biberon;

    @Column(name = "alimento_por_si_solo_cuchara")
    private String alimentoPorSiSoloCuchara;

    @Column(name = "edad_integro_dieta_familiar")
    private String edadIntegroDietaFamiliar;

    @Lob
    @Column(name = "enfermedades_con_tratamiento", columnDefinition = "TEXT")
    private String enfermedadesConTratamiento;

    @Lob
    @Column(name = "alergias", columnDefinition = "TEXT")
    private String alergias;

    @Lob
    @Column(name = "intervenciones_quirurgicas", columnDefinition = "TEXT")
    private String intervencionesQuirurgicas;

    @Lob
    @Column(name = "medicamentos_requeridos_o_consumo", columnDefinition = "TEXT")
    private String medicamentosRequeridosOConsumo;

    @Lob
    @Column(name = "enfermedades_discapacidades_familiares", columnDefinition = "TEXT")
    private String enfermedadesDiscapacidadesFamiliares;

    @Lob
    @Column(name = "trastornos_psicologicos_familiares", columnDefinition = "TEXT")
    private String trastornosPsicologicosFamiliares;

    @Lob
    @Column(name = "problemas_aprendizaje_familiares", columnDefinition = "TEXT")
    private String problemasAprendizajeFamiliares;

    @Column(name = "parentesco")
    private String parentesco;

    @Column(name = "persona_q_deriva")
    private String personaQDeriva;

    // Métodos personalizados para devolver "Sí" o "No"
    public String isVivenJuntos() {
        return Boolean.TRUE.equals(vivenJuntos) ? "Sí" : "No";
    }

    public String isEmbarazoDeseado() {
        return Boolean.TRUE.equals(embarazoDeseado) ? "Sí" : "No";
    }

    public String isControlEmbarazo() {
        return Boolean.TRUE.equals(controlEmbarazo) ? "Sí" : "No";
    }

    public String isPresentoAmenazaAborto() {
        return Boolean.TRUE.equals(presentoAmenazaAborto) ? "Sí" : "No";
    }

    public String isLloroAlNacer() {
        return Boolean.TRUE.equals(lloroAlNacer) ? "Sí" : "No";
    }

    public String isPresentoAnoxiaAlNacer() {
        return Boolean.TRUE.equals(presentoAnoxiaAlNacer) ? "Sí" : "No";
    }

    public String isPresentoHipoxiaAlNacer() {
        return Boolean.TRUE.equals(presentoHipoxiaAlNacer) ? "Sí" : "No";
    }

    public String isPresentoIctericiaAlNacer() {
        return Boolean.TRUE.equals(presentoIctericiaAlNacer) ? "Sí" : "No";
    }

    public String isPresentoCianosisAlNacer() {
        return Boolean.TRUE.equals(presentoCianosisAlNacer) ? "Sí" : "No";
    }

    public String isComplicacionesEnElParto() {
        return Boolean.TRUE.equals(complicacionesEnElParto) ? "Sí" : "No";
    }

    public String isEstuvoEnIncubadora() {
        return Boolean.TRUE.equals(estuvoEnIncubadora) ? "Sí" : "No";
    }

    public String isEsquemaVacunacionCompleto() {
        return Boolean.TRUE.equals(esquemaVacunacionCompleto) ? "Sí" : "No";
    }

    public String isCaeOPerdeEquilibrioFacilmente() {
        return Boolean.TRUE.equals(caeOPerdeEquilibrioFacilmente) ? "Sí" : "No";
    }

    // Enums anidados
    public enum ElEstudianteViveCon {
        AMBOS_PADRES, SOLO_MAMA, SOLO_PAPA, FAMILIAR,OTRO;
    }

    public enum TipoFamilia {
        NUCLEAR, EXTENSA_O_CONSANGUINEA, MONOPARENTAL, RECONSTRUIDA, AMPLIADA_NO_CONSANGUINEA;
    }

    public enum DondeNacioNinio {
        HOSPITAL, CLINICA;
    }

    public enum DuracionEmbarazo {
        A_TERMINO, PRE_TERMINO, POSTERMICO;
    }

    public enum TipoParto {
        NATURAL, CESAREA;
    }

    public enum PartoSegunElComienzo {
        ESPONTANEO, INDUCIDO;
    }

    public enum PartoSegunFinalizacion {
        EUTOCICO, DISTOCICO;
    }

    // Constructor sin argumentos
    public FichaMedica() {
    }

    public FichaMedica(FichaMedica otra) {
        this.id = otra.id;
        this.paciente = otra.paciente;
        this.estado = otra.estado;
        this.fuenteDeInformacion = otra.fuenteDeInformacion;
        this.fecha = otra.fecha;
        this.motivoConsulta = otra.motivoConsulta;
        this.elEstudianteViveCon = otra.elEstudianteViveCon;
        this.elEstudianteViveConOtro = otra.elEstudianteViveConOtro;
        this.nombreDelPadre = otra.nombreDelPadre;
        this.edadPadre = otra.edadPadre;
        this.ocupacionPadre = otra.ocupacionPadre;
        this.instruccionPadre = otra.instruccionPadre;
        this.estadoCivilPadre = otra.estadoCivilPadre;
        this.lugarResidenciaPadre = otra.lugarResidenciaPadre;
        this.nombreDeLaMadre = otra.nombreDeLaMadre;
        this.edadMadre = otra.edadMadre;
        this.ocupacionMadre = otra.ocupacionMadre;
        this.instruccionMadre = otra.instruccionMadre;
        this.estadoCivilMadre = otra.estadoCivilMadre;
        this.lugarResidenciaMadre = otra.lugarResidenciaMadre;
        this.numeroHermanos = otra.numeroHermanos;
        this.lugarQueOcupa = otra.lugarQueOcupa;
        this.direccionDomiciliaria = otra.direccionDomiciliaria;
        this.vivenJuntos = otra.vivenJuntos;
        this.otrosCompromisos = otra.otrosCompromisos;
        this.genogramaFamiliar = otra.genogramaFamiliar;
        this.tipoFamilia = otra.tipoFamilia;
        this.hijosOtrosFamiliaresVivenCasa = otra.hijosOtrosFamiliaresVivenCasa;
        this.embarazoDeseado = otra.embarazoDeseado;
        this.controlEmbarazo = otra.controlEmbarazo;
        this.causaControlEmbarazo = otra.causaControlEmbarazo;
        this.enfermedadesMadre = otra.enfermedadesMadre;
        this.consumoMedicamentosOExposicionSustanciasToxicas = otra.consumoMedicamentosOExposicionSustanciasToxicas;
        this.presentoAmenazaAborto = otra.presentoAmenazaAborto;
        this.mesAmenazaAborto = otra.mesAmenazaAborto;
        this.causaAmenazaAborto = otra.causaAmenazaAborto;
        this.estadoEmocional = otra.estadoEmocional;
        this.dondeNacioNinio = otra.dondeNacioNinio;
        this.ciudadNacimientoNinio = otra.ciudadNacimientoNinio;
        this.duracionEmbarazo = otra.duracionEmbarazo;
        this.tipoParto = otra.tipoParto;
        this.partoSegunElComienzo = otra.partoSegunElComienzo;
        this.partoSegunFinalizacion = otra.partoSegunFinalizacion;
        this.lloroAlNacer = otra.lloroAlNacer;
        this.pesoAlNacer = otra.pesoAlNacer;
        this.tallaAlNacer = otra.tallaAlNacer;
        this.presentoAnoxiaAlNacer = otra.presentoAnoxiaAlNacer;
        this.presentoHipoxiaAlNacer = otra.presentoHipoxiaAlNacer;
        this.presentoIctericiaAlNacer = otra.presentoIctericiaAlNacer;
        this.presentoCianosisAlNacer = otra.presentoCianosisAlNacer;
        this.malformacionCongenita = otra.malformacionCongenita;
        this.problemasDeAlimentacion = otra.problemasDeAlimentacion;
        this.complicacionesEnElParto = otra.complicacionesEnElParto;
        this.cualComplicacionParto = otra.cualComplicacionParto;
        this.estuvoEnIncubadora = otra.estuvoEnIncubadora;
        this.tiempoEstuvoEnIncubadora = otra.tiempoEstuvoEnIncubadora;
        this.causaEstuvoEnIncubadora = otra.causaEstuvoEnIncubadora;
        this.esquemaVacunacionCompleto = otra.esquemaVacunacionCompleto;
        this.controlCefalico = otra.controlCefalico;
        this.sedestacion = otra.sedestacion;
        this.hipedestacion = otra.hipedestacion;
        this.caminaConApoyo = otra.caminaConApoyo;
        this.caminaSolo = otra.caminaSolo;
        this.subeEscaleras = otra.subeEscaleras;
        this.controlEsfinteres = otra.controlEsfinteres;
        this.salta = otra.salta;
        this.corre = otra.corre;
        this.gateo = otra.gateo;
        this.prefiereManoIzquierdaDerecha = otra.prefiereManoIzquierdaDerecha;
        this.caeOPerdeEquilibrioFacilmente = otra.caeOPerdeEquilibrioFacilmente;
        this.dejoPechoMaterno = otra.dejoPechoMaterno;
        this.biberon = otra.biberon;
        this.alimentoPorSiSoloCuchara = otra.alimentoPorSiSoloCuchara;
        this.edadIntegroDietaFamiliar = otra.edadIntegroDietaFamiliar;
        this.enfermedadesConTratamiento = otra.enfermedadesConTratamiento;
        this.alergias = otra.alergias;
        this.intervencionesQuirurgicas = otra.intervencionesQuirurgicas;
        this.medicamentosRequeridosOConsumo = otra.medicamentosRequeridosOConsumo;
        this.enfermedadesDiscapacidadesFamiliares = otra.enfermedadesDiscapacidadesFamiliares;
        this.trastornosPsicologicosFamiliares = otra.trastornosPsicologicosFamiliares;
        this.problemasAprendizajeFamiliares = otra.problemasAprendizajeFamiliares;
        this.parentesco = otra.parentesco;
        this.personaQDeriva = otra.personaQDeriva;
    }


}
