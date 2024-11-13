package com.test.TUdipsaiApi.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "psicologia_clinica")
@Getter
@Setter
public class PsicologiaClinica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_paciente", referencedColumnName = "id")
    private Paciente paciente;

    @Column(name = "estado", nullable = false, columnDefinition = "int default 1")
    private int estado;

    @Lob
    @Column(name = "anamnesis_familiar", columnDefinition = "TEXT")
    private String anamnesisFamiliar;

    @Lob
    @Column(name = "personal", columnDefinition = "TEXT")
    private String personal;

    @Lob
    @Column(name = "momentos_evolutivos_en_el_desarrollo", columnDefinition = "TEXT")
    private String momentosEvolutivosEnElDesarrollo;

    @Lob
    @Column(name = "habitos_en_la_oralidad", columnDefinition = "TEXT")
    private String habitosEnLaOralidad;

    @Column(name = "inicio_horario_de_suenio")
    private Integer inicioHorarioDeSuenio;

    @Column(name = "fin_horario_de_suenio")
    private Integer finHorarioDeSuenio;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_horario_de_suenio")
    private TipoHorarioDeSuenio tipoHorarioDeSuenio;

    @Enumerated(EnumType.STRING)
    @Column(name = "compania_suenio")
    private CompaniaSuenio companiaSuenio;

    @Column(name = "especificar_compania_suenio")
    private String especificarCompaniaSuenio;

    @Column(name = "edad")
    private String edad;

    @Column(name = "hipersomnia")
    private Boolean hipersomnia;

    @Column(name = "dificultad_de_conciliar_el_suenio")
    private Boolean dificultadDeConciliarElSuenio;

    @Column(name = "despertar_frecuente")
    private Boolean despertarFrecuente;

    @Column(name = "despertar_prematuro")
    private Boolean despertarPrematuro;

    @Column(name = "sonambulismo")
    private Boolean sonambulismo;

    @Lob
    @Column(name = "observaciones_habitos_de_suenio", columnDefinition = "TEXT")
    private String observacionesHabitosDeSuenio;

    @Column(name = "temores")
    private Boolean temores;

    @Column(name = "destructividad")
    private Boolean destructividad;

    @Column(name = "nerviosismo")
    private Boolean nerviosismo;

    @Column(name = "irritabilidad")
    private Boolean irritabilidad;

    @Column(name = "egocentrismo")
    private Boolean egocentrismo;

    @Column(name = "regresiones")
    private Boolean regresiones;

    @Column(name = "tics")
    private Boolean tics;

    @Column(name = "hurto")
    private Boolean hurto;

    @Column(name = "mentira")
    private Boolean mentira;

    @Column(name = "cuidado_personal")
    private Boolean cuidadoPersonal;

    @Column(name = "otros_conductas_preocupantes")
    private String otrosConductasPreocupantes;

    @Lob
    @Column(name = "observaciones_conductas_preocupantes", columnDefinition = "TEXT")
    private String observacionesConductasPreocupantes;

    @Enumerated(EnumType.STRING)
    @Column(name = "sexo_de_nacimiento")
    private SexoDeNacimiento sexoDeNacimiento;

    @Enumerated(EnumType.STRING)
    @Column(name = "genero")
    private Genero genero;

    @Column(name = "especificar_genero_otros")
    private String especificarGeneroOtros;

    @Enumerated(EnumType.STRING)
    @Column(name = "orientacion_sexual")
    private OrientacionSexual orientacionSexual;

    @Enumerated(EnumType.STRING)
    @Column(name = "curiosidad_sexual")
    private CuriosidadSexual curiosidadSexual;

    @Enumerated(EnumType.STRING)
    @Column(name = "grado_de_informacion")
    private GradoDeInformacion gradoDeInformacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "actividad_sexual")
    private ActividadSexual actividadSexual;

    @Enumerated(EnumType.STRING)
    @Column(name = "masturbacion")
    private Masturbacion masturbacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "promiscuidad")
    private Promiscuidad promiscuidad;

    @Enumerated(EnumType.STRING)
    @Column(name = "disfunciones")
    private Disfunciones disfunciones;

    @Enumerated(EnumType.STRING)
    @Column(name = "erotismo")
    private Erotismo erotismo;

    @Enumerated(EnumType.STRING)
    @Column(name = "parafilias")
    private Parafilias parafilias;

    @Lob
    @Column(name = "observaciones_aspecto_psicosexual", columnDefinition = "TEXT")
    private String observacionesAspectoPsicosexual;

    @Column(name = "palabras_raras")
    private Boolean palabrasRaras;

    @Column(name = "logico_y_claro")
    private Boolean logicoYClaro;

    @Column(name = "voz_monotona")
    private Boolean vozMonotona;

    @Column(name = "mal_hablado")
    private Boolean malHablado;

    @Column(name = "lento_y_teatral")
    private Boolean lentoYTeatral;

    @Column(name = "pesimista")
    private Boolean pesimista;

    @Column(name = "hiriente")
    private Boolean hiriente;

    @Column(name = "charlatan")
    private Boolean charlatan;

    @Column(name = "incoherente")
    private Boolean incoherente;

    @Column(name = "verborrea")
    private Boolean verborrea;

    @Column(name = "abatimiento")
    private Boolean abatimiento;

    @Column(name = "tension")
    private Boolean tension;

    @Column(name = "perplejidad")
    private Boolean perplejidad;

    @Column(name = "suspicacia")
    private Boolean suspicacia;

    @Column(name = "enfado")
    private Boolean enfado;

    @Column(name = "preocupacion")
    private Boolean preocupacion;

    @Column(name = "obscenidad")
    private Boolean obscenidad;

    @Column(name = "disartria")
    private Boolean disartria;

    @Column(name = "afasia_expresiva")
    private Boolean afasiaExpresiva;

    @Column(name = "afasia_receptiva")
    private Boolean afasiaReceptiva;

    @Column(name = "afasia_anomica")
    private Boolean afasiaAnomica;

    @Column(name = "afasia_global")
    private Boolean afasiaGlobal;

    @Column(name = "ecolalia")
    private Boolean ecolalia;

    @Column(name = "palilalia")
    private Boolean palilalia;

    @Column(name = "ensimismamiento")
    private Boolean ensimismamiento;

    @Column(name = "hay_que_guiarlo")
    private Boolean hayQueGuiarlo;

    @Column(name = "molestoso")
    private Boolean molestoso;

    @Column(name = "lento")
    private Boolean lento;

    @Column(name = "no_desea_hacer_nada")
    private Boolean noDeseaHacerNada;

    @Column(name = "hace_cosas_extranas")
    private Boolean haceCosasExtranas;

    @Column(name = "aislado")
    private Boolean aislado;

    @Column(name = "participa_en_grupos")
    private Boolean participaEnGrupos;

    @Column(name = "es_violento")
    private Boolean esViolento;

    @Column(name = "callado")
    private Boolean callado;

    @Column(name = "amigable_y_cooperador")
    private Boolean amigableYCooperador;

    @Column(name = "adaptable")
    private Boolean adaptable;

    @Column(name = "inquieto")
    private Boolean inquieto;

    @Column(name = "nervioso")
    private Boolean nervioso;

    @Column(name = "tiene_amigos_intimos")
    private Boolean tieneAmigosIntimos;

    @Column(name = "confuso")
    private Boolean confuso;

    @Column(name = "centrado_en_si_mismo")
    private Boolean centradoEnSiMismo;

    @Column(name = "olvidadizo")
    private Boolean olvidadizo;

    @Column(name = "piensa_y_responde_bien")
    private Boolean piensaYRespondeBien;

    @Column(name = "pocos_pensamientos")
    private Boolean pocosPensamientos;

    @Column(name = "no_ve_los_errores")
    private Boolean noVeLosErrores;

    @Column(name = "actua_infantilmente")
    private Boolean actuaInfaltilmente;

    @Column(name = "desconfia")
    private Boolean desconfia;

    @Column(name = "hosco")
    private Boolean hosco;

    @Column(name = "fastidiado")
    private Boolean fastidiado;

    @Column(name = "cansado")
    private Boolean cansado;

    @Column(name = "viste_raramente")
    private Boolean visteRaramente;

    @Column(name = "desordenado")
    private Boolean desordenado;

    @Column(name = "mugroso_y_fachoso")
    private Boolean mugrosoYFachoso;

    @Column(name = "exceso_de_ropas")
    private Boolean excesoDeRopas;

    @Column(name = "dramatico_y_teatral")
    private Boolean dramaticoYTeatral;

    @Column(name = "viste_normalmente")
    private Boolean visteNormalmente;

    @Column(name = "impecable")
    private Boolean impecable;

    @Column(name = "duda_de_todos")
    private Boolean dudaDeTodos;

    @Column(name = "pasa_aislado")
    private Boolean pasaAislado;

    @Column(name = "dice_estar_bien")
    private Boolean diceEstarBien;

    @Column(name = "gusta_de_hacer_dano_a_los_demas")
    private Boolean gustaDeHacerDanoALosDemas;

    @Column(name = "tiene_iniciativas")
    private Boolean tieneIniciativas;

    @Column(name = "colabora")
    private Boolean colabora;

    @Column(name = "reticencia")
    private Boolean reticencia;

    @Column(name = "rechazo")
    private Boolean rechazo;

    @Column(name = "mutismo")
    private Boolean mutismo;

    @Column(name = "negativismo")
    private Boolean negativismo;

    @Column(name = "agresividad")
    private Boolean agresividad;

    @Column(name = "sarcasmo")
    private Boolean sarcasmo;

    @Column(name = "pegajosidad")
    private Boolean pegajosidad;

    @Column(name = "colaboracion_excesiva")
    private Boolean colaboracionExcesiva;

    @Column(name = "atento")
    private Boolean atento;

    @Column(name = "seductor")
    private Boolean seductor;

    @Column(name = "evita_conversar")
    private Boolean evitaConversar;

    @Column(name = "impulsivo")
    private Boolean impulsivo;

    @Column(name = "bromista")
    private Boolean bromista;

    @Column(name = "tosco_y_descortes")
    private Boolean toscoYDescortes;

    @Column(name = "triste")
    private Boolean triste;

    @Column(name = "irritable")
    private Boolean irritable;

    @Column(name = "popenso_a_rinias")
    private Boolean popensoARinias;

    @Column(name = "suave_y_afable")
    private Boolean suaveYAfable;

    @Column(name = "indiferente")
    private Boolean indiferente;

    @Column(name = "preocupado_y_pensativo")
    private Boolean preocupadoYPensativo;

    @Column(name = "tendencia_al_llanto")
    private Boolean tendenciaAlLlanto;

    @Column(name = "alegre")
    private Boolean alegre;

    @Column(name = "euforico")
    private Boolean euforico;

    @Column(name = "labil_de_humor")
    private Boolean labilDeHumor;

    @Column(name = "inactivo")
    private Boolean inactivo;

    @Column(name = "perezoso")
    private Boolean perezoso;

    @Column(name = "solo_hace_cosas_indispensables")
    private Boolean soloHaceCosasIndispensables;

    @Column(name = "realiza_solo_un_tipo_de_trabajo")
    private Boolean realizaSoloUnTipoDeTrabajo;

    @Column(name = "dedicado_a_varias_actividades")
    private Boolean dedicadoAVariasActividades;

    @Column(name = "apraxia")
    private Boolean apraxia;

    @Column(name = "catatonia")
    private Boolean catatonia;

    @Column(name = "agitacion")
    private Boolean agitacion;

    @Column(name = "amaneramiento")
    private Boolean amaneramiento;

    @Column(name = "estereotipias")
    private Boolean estereotipias;

    @Column(name = "ecopraxia")
    private Boolean ecopraxia;

    @Column(name = "obediencia_automatica")
    private Boolean obedienciaAutomatica;

    @Column(name = "negativismo_actividades")
    private Boolean negativismoActividades;

    @Column(name = "interceptacion_motriz")
    private Boolean interceptacionMotriz;

    @Column(name = "dispraxias")
    private Boolean dispraxias;

    @Column(name = "actos_impulsivos")
    private Boolean actosImpulsivos;

    @Column(name = "actos_obsesivos")
    private Boolean actosObsesivos;

    @Column(name = "tics_actividades")
    private Boolean ticsActividades;

    @Column(name = "liderazgo")
    private Boolean liderazgo;

    @Column(name = "sociabilidad")
    private Boolean sociabilidad;

    @Column(name = "responsabilidad")
    private Boolean responsabilidad;

    @Column(name = "tolerancia_normal")
    private Boolean toleranciaNormal;

    @Column(name = "baja")
    private Boolean baja;

    @Column(name = "colaboracion")
    private Boolean colaboracion;

    @Column(name = "inquietud")
    private Boolean inquietud;

    @Column(name = "acata_ordenes_verbales")
    private Boolean acataOrdenesVerbales;

    @Column(name = "agresivo")
    private Boolean agresivo;

    @Column(name = "extravagante")
    private Boolean extravagante;

    @Column(name = "antisocial")
    private Boolean antisocial;

    @Column(name = "impulsivo_comportamiento")
    private Boolean impulsivoComportamiento;

    @Column(name = "reflexivo")
    private Boolean reflexivo;

    @Column(name = "pasivo")
    private Boolean pasivo;

    @Column(name = "apatico")
    private Boolean apatico;

    @Column(name = "dependiente")
    private Boolean dependiente;

    @Column(name = "dominante")
    private Boolean dominante;

    @Column(name = "cauteloso")
    private Boolean cauteloso;

    @Column(name = "quejoso")
    private Boolean quejoso;

    @Column(name = "temeroso")
    private Boolean temeroso;

    @Column(name = "teatral")
    private Boolean teatral;

    @Column(name = "ritualista")
    private Boolean ritualista;

    @Column(name = "aislamiento")
    private Boolean aislamiento;

    @Column(name = "ataques_de_panico")
    private Boolean ataquesDePanico;

    @Column(name = "incapacidad_de_realizacion_de_actividades_productivas")
    private Boolean incapacidadDeRealizacionDeActividadesProductivas;

    @Column(name = "riesgo_potencial_o_potencial_suicida")
    private Boolean riesgoPotencialOPotencialSuicida;

    @Column(name = "inhibicion")
    private Boolean inhibicion;

    @Column(name = "apatia")
    private Boolean apatia;

    @Column(name = "humor_variable")
    private Boolean humorVariable;

    @Column(name = "alta_sensibilidad")
    private Boolean altaSensibilidad;

    @Column(name = "agresividad_afectividad")
    private Boolean agresividadAfectividad;

    @Column(name = "sumision")
    private Boolean sumision;

    @Column(name = "rabietas")
    private Boolean rabietas;

    @Column(name = "solidaridad")
    private Boolean solidaridad;

    @Column(name = "generosidad")
    private Boolean generosidad;

    @Column(name = "afectuoso")
    private Boolean afectuoso;

    @Column(name = "angustia")
    private Boolean angustia;

    @Column(name = "ansiedad_situacional")
    private Boolean ansiedadSituacional;

    @Column(name = "timidez")
    private Boolean timidez;

    @Column(name = "ansiedad_expectante")
    private Boolean ansiedadExpectante;

    @Column(name = "depresion")
    private Boolean depresion;

    @Column(name = "perdida_reciente_de_interes")
    private Boolean perdidaRecienteDeInteres;

    @Column(name = "desesperacion")
    private Boolean desesperacion;

    @Column(name = "euforia")
    private Boolean euforia;

    @Column(name = "indiferencia")
    private Boolean indiferencia;

    @Column(name = "aplanamiento")
    private Boolean aplanamiento;

    @Column(name = "ambivalencia")
    private Boolean ambivalencia;

    @Column(name = "irritabilidad_afectividad")
    private Boolean irritabilidadAfectividad;

    @Column(name = "labilidad")
    private Boolean labilidad;

    @Column(name = "tenacidad")
    private Boolean tenacidad;

    @Column(name = "incontinencia")
    private Boolean incontinencia;

    @Column(name = "sentimientos_inadecuados")
    private Boolean sentimientosInadecuados;

    @Column(name = "neotimia")
    private Boolean neotimia;

    @Column(name = "disociacion_ideo_afectiva")
    private Boolean disociacionIdeoAfectiva;

    @Column(name = "anhedonia")
    private Boolean anhedonia;

    @Lob
    @Column(name = "observaciones_guia_de_observacion", columnDefinition = "TEXT")
    private String observacionesGuiaDeObservacion;

    @Column(name = "lucidez")
    private Boolean lucidez;

    @Column(name = "obnubilacion")
    private Boolean obnubilacion;

    @Column(name = "estupor")
    private Boolean estupor;

    @Column(name = "coma")
    private Boolean coma;

    @Column(name = "hipervigilancia")
    private Boolean hipervigilancia;

    @Column(name = "confusion")
    private Boolean confusion;

    @Column(name = "estado_crepuscular")
    private Boolean estadoCrepuscular;

    @Column(name = "onirismo")
    private Boolean onirismo;

    @Column(name = "sonambulismo_estado_de_conciencia")
    private Boolean sonambulismoEstadoDeConciencia;

    @Column(name = "hipercepcion")
    private Boolean hipercepcion;

    @Column(name = "hipoprosexia")
    private Boolean hipoprosexia;

    @Column(name = "disprosexia")
    private Boolean disprosexia;

    @Column(name = "distraibilidad")
    private Boolean distraibilidad;

    @Column(name = "sin_alteracion")
    private Boolean sinAlteracion;

    @Column(name = "hipercepcion_sensopercepcion")
    private Boolean hipercepcionSensopercepcion;

    @Column(name = "ilusiones")
    private Boolean ilusiones;

    @Column(name = "seudoalucionciones")
    private Boolean seudoalucionciones;

    @Column(name = "alusinosis")
    private Boolean alusinosis;

    @Column(name = "macropsias")
    private Boolean macropsias;

    @Column(name = "micropsias")
    private Boolean micropsias;

    @Column(name = "no_presenta")
    private Boolean noPresenta;

    @Column(name = "alucinaiones")
    private Boolean alucinaiones;

    @Column(name = "hipermnecia")
    private Boolean hipermnecia;

    @Column(name = "amnesia_de_fijacion")
    private Boolean amnesiaDeFijacion;

    @Column(name = "amnesia_de_evocacion")
    private Boolean amnesiaDeEvocacion;

    @Column(name = "mixta")
    private Boolean mixta;

    @Column(name = "lacunar")
    private Boolean lacunar;

    @Column(name = "dismensia")
    private Boolean dismensia;

    @Column(name = "paramnesias")
    private Boolean paramnesias;

    @Column(name = "sin_alteracion_memoria")
    private Boolean sinAlteracionMemoria;

    @Column(name = "enlentecimiento")
    private Boolean enlentecimiento;

    @Column(name = "excitacion_psicomotriz")
    private Boolean excitacionPsicomotriz;

    @Column(name = "catatonia_conducta_motora")
    private Boolean catatoniaConductaMotora;

    @Column(name = "actitudes_anormales")
    private Boolean actitudesAnormales;

    @Column(name = "alteraciones_de_la_marcha")
    private Boolean alteracionesDeLaMarcha;

    @Column(name = "inquietud_conducta_motora")
    private Boolean inquietudConductaMotora;

    @Enumerated(EnumType.STRING)
    @Column(name = "incoherencia_estructura_del_pensamiento")
    private Incoherencia incoherencia;

    @Enumerated(EnumType.STRING)
    @Column(name = "bloqueos")
    private Bloqueos bloqueos;

    @Enumerated(EnumType.STRING)
    @Column(name = "preservacion")
    private Preservacion preservacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "prolijidad")
    private Prolijidad prolijidad;

    @Enumerated(EnumType.STRING)
    @Column(name = "desgragacion")
    private Desgragacion desgragacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estereotipias_estructura_del_pensamiento")
    private Estereotipias estereotipiasEstructuraDelPensamiento;

    @Enumerated(EnumType.STRING)
    @Column(name = "neologismos")
    private Neologismos neologismos;

    @Enumerated(EnumType.STRING)
    @Column(name = "musitacion")
    private Musitacion musitacion;

    @Column(name = "retardo")
    private Boolean retardo;

    @Column(name = "aceleracion")
    private Boolean aceleracion;

    @Column(name = "fuga_de_ideas")
    private Boolean fugaDeIdeas;

    @Column(name = "mutismo_curso_del_pensamiento")
    private Boolean mutismoCursoDelPensamiento;

    @Column(name = "grandeza")
    private Boolean grandeza;

    @Column(name = "suicidio")
    private Boolean suicidio;

    @Column(name = "autocompasion")
    private Boolean autocompasion;

    @Column(name = "inferioridad")
    private Boolean inferioridad;

    @Column(name = "perdida_de_interes")
    private Boolean perdidaDeInteres;

    @Column(name = "indecision")
    private Boolean indecision;

    @Column(name = "necesidad_de_ayuda")
    private Boolean necesidadDeAyuda;

    @Column(name = "fracaso")
    private Boolean fracaso;

    @Column(name = "ruina")
    private Boolean ruina;

    @Column(name = "autoacusacion")
    private Boolean autoacusacion;

    @Column(name = "resentimiento")
    private Boolean resentimiento;

    @Column(name = "muerte")
    private Boolean muerte;

    @Column(name = "danio")
    private Boolean danio;

    @Column(name = "enfermedad")
    private Boolean enfermedad;

    @Column(name = "grave")
    private Boolean grave;

    @Column(name = "hipocondrias")
    private Boolean hipocondrias;

    @Column(name = "nihilistas")
    private Boolean nihilistas;

    @Column(name = "no_tener_sentimientos")
    private Boolean noTenerSentimientos;

    @Column(name = "el_mundo_ha_dejado_de_existir")
    private Boolean elMundoHaDejadoDeExistir;

    @Column(name = "referencia")
    private Boolean referencia;

    @Column(name = "extravagantes")
    private Boolean extravagantes;

    @Column(name = "fobicas")
    private Boolean fobicas;

    @Column(name = "compulsivas")
    private Boolean compulsivas;

    @Column(name = "obsesivas")
    private Boolean obsesivas;

    @Column(name = "desconfianzas")
    private Boolean desconfianzas;

    @Column(name = "des_relacion")
    private Boolean desRelacion;

    @Column(name = "perdida_de_control")
    private Boolean perdidaDeControl;

    @Column(name = "ser_calumniado")
    private Boolean serCalumniado;

    @Column(name = "delirios_paranoides")
    private Boolean deliriosParanoides;

    @Column(name = "depresivos")
    private Boolean depresivos;

    @Column(name = "mistico_religioso")
    private Boolean misticoReligioso;

    @Column(name = "sexuales")
    private Boolean sexuales;

    @Column(name = "difusos")
    private Boolean difusos;

    @Column(name = "otros_contenido_del_pensamiento")
    private String otrosContenidoDelPensamiento;

    @Column(name = "capacidad_de_autocritica")
    private Boolean capacidadDeAutocritica;

    @Column(name = "heterocritica")
    private Boolean heterocritica;

    @Column(name = "proyectos_futuros")
    private Boolean proyectosFuturos;

    @Column(name = "conciencia_de_la_enfermedad")
    private Boolean concienciaDeLaEnfermedad;

    @Enumerated(EnumType.STRING)
    @Column(name = "desorientacion_en_tiempo")
    private Desorientacion desorientacionEnTiempo;

    @Enumerated(EnumType.STRING)
    @Column(name = "espacio")
    private Espacio espacio;

    @Enumerated(EnumType.STRING)
    @Column(name = "respecto_a_si_mismo")
    private RespectoASiMismo respectoASiMismo;

    @Enumerated(EnumType.STRING)
    @Column(name = "respecto_a_otras_personas")
    private RespectoAOtrasPersonas respectoAOtrasPersonas;

    @Lob
    @Column(name = "impresion_diagnostica", columnDefinition = "TEXT")
    private String impresionDiagnostica;

    @Lob
    @Column(name = "derivacion_interconsulta", columnDefinition = "TEXT")
    private String derivacionInterconsulta;

    @Lob
    @Column(name = "objetivo_plan_tratamiento_individual", columnDefinition = "TEXT")
    private String objetivoPlanTratamientoIndividual;

    @Lob
    @Column(name = "estrategia_de_intervencion", columnDefinition = "TEXT")
    private String estrategiaDeIntervencion;

    @Lob
    @Column(name = "indicador_de_logro", columnDefinition = "TEXT")
    private String indicadorDeLogro;

    @Column(name = "tiempo_estimado")
    private String tiempoEstimado;

    @Lob
    @Column(name = "evaluacion", columnDefinition = "TEXT")
    private String evaluacion;

    public PsicologiaClinica() {
    }

    // Constructor de copia
    public PsicologiaClinica(PsicologiaClinica otra) {
        this.id = otra.id;
        this.paciente = otra.paciente;
        this.estado = otra.estado;
        this.anamnesisFamiliar = otra.anamnesisFamiliar;
        this.personal = otra.personal;
        this.momentosEvolutivosEnElDesarrollo = otra.momentosEvolutivosEnElDesarrollo;
        this.habitosEnLaOralidad = otra.habitosEnLaOralidad;
        this.inicioHorarioDeSuenio = otra.inicioHorarioDeSuenio;
        this.finHorarioDeSuenio = otra.finHorarioDeSuenio;
        this.tipoHorarioDeSuenio = otra.tipoHorarioDeSuenio;
        this.companiaSuenio = otra.companiaSuenio;
        this.especificarCompaniaSuenio = otra.especificarCompaniaSuenio;
        this.edad = otra.edad;
        this.hipersomnia = otra.hipersomnia;
        this.dificultadDeConciliarElSuenio = otra.dificultadDeConciliarElSuenio;
        this.despertarFrecuente = otra.despertarFrecuente;
        this.despertarPrematuro = otra.despertarPrematuro;
        this.sonambulismo = otra.sonambulismo;
        this.observacionesHabitosDeSuenio = otra.observacionesHabitosDeSuenio;
        this.temores = otra.temores;
        this.destructividad = otra.destructividad;
        this.nerviosismo = otra.nerviosismo;
        this.irritabilidad = otra.irritabilidad;
        this.egocentrismo = otra.egocentrismo;
        this.regresiones = otra.regresiones;
        this.tics = otra.tics;
        this.hurto = otra.hurto;
        this.mentira = otra.mentira;
        this.cuidadoPersonal = otra.cuidadoPersonal;
        this.otrosConductasPreocupantes = otra.otrosConductasPreocupantes;
        this.observacionesConductasPreocupantes = otra.observacionesConductasPreocupantes;
        this.sexoDeNacimiento = otra.sexoDeNacimiento;
        this.genero = otra.genero;
        this.especificarGeneroOtros = otra.especificarGeneroOtros;
        this.orientacionSexual = otra.orientacionSexual;
        this.curiosidadSexual = otra.curiosidadSexual;
        this.gradoDeInformacion = otra.gradoDeInformacion;
        this.actividadSexual = otra.actividadSexual;
        this.masturbacion = otra.masturbacion;
        this.promiscuidad = otra.promiscuidad;
        this.disfunciones = otra.disfunciones;
        this.erotismo = otra.erotismo;
        this.parafilias = otra.parafilias;
        this.observacionesAspectoPsicosexual = otra.observacionesAspectoPsicosexual;
        this.palabrasRaras = otra.palabrasRaras;
        this.logicoYClaro = otra.logicoYClaro;
        this.vozMonotona = otra.vozMonotona;
        this.malHablado = otra.malHablado;
        this.lentoYTeatral = otra.lentoYTeatral;
        this.pesimista = otra.pesimista;
        this.hiriente = otra.hiriente;
        this.charlatan = otra.charlatan;
        this.incoherente = otra.incoherente;
        this.verborrea = otra.verborrea;
        this.abatimiento = otra.abatimiento;
        this.tension = otra.tension;
        this.perplejidad = otra.perplejidad;
        this.suspicacia = otra.suspicacia;
        this.enfado = otra.enfado;
        this.preocupacion = otra.preocupacion;
        this.obscenidad = otra.obscenidad;
        this.disartria = otra.disartria;
        this.afasiaExpresiva = otra.afasiaExpresiva;
        this.afasiaReceptiva = otra.afasiaReceptiva;
        this.afasiaAnomica = otra.afasiaAnomica;
        this.afasiaGlobal = otra.afasiaGlobal;
        this.ecolalia = otra.ecolalia;
        this.palilalia = otra.palilalia;
        this.ensimismamiento = otra.ensimismamiento;
        this.hayQueGuiarlo = otra.hayQueGuiarlo;
        this.molestoso = otra.molestoso;
        this.lento = otra.lento;
        this.noDeseaHacerNada = otra.noDeseaHacerNada;
        this.haceCosasExtranas = otra.haceCosasExtranas;
        this.aislado = otra.aislado;
        this.participaEnGrupos = otra.participaEnGrupos;
        this.esViolento = otra.esViolento;
        this.callado = otra.callado;
        this.amigableYCooperador = otra.amigableYCooperador;
        this.adaptable = otra.adaptable;
        this.inquieto = otra.inquieto;
        this.nervioso = otra.nervioso;
        this.tieneAmigosIntimos = otra.tieneAmigosIntimos;
        this.confuso = otra.confuso;
        this.centradoEnSiMismo = otra.centradoEnSiMismo;
        this.olvidadizo = otra.olvidadizo;
        this.piensaYRespondeBien = otra.piensaYRespondeBien;
        this.pocosPensamientos = otra.pocosPensamientos;
        this.noVeLosErrores = otra.noVeLosErrores;
        this.actuaInfaltilmente = otra.actuaInfaltilmente;
        this.desconfia = otra.desconfia;
        this.hosco = otra.hosco;
        this.fastidiado = otra.fastidiado;
        this.cansado = otra.cansado;
        this.visteRaramente = otra.visteRaramente;
        this.desordenado = otra.desordenado;
        this.mugrosoYFachoso = otra.mugrosoYFachoso;
        this.excesoDeRopas = otra.excesoDeRopas;
        this.dramaticoYTeatral = otra.dramaticoYTeatral;
        this.visteNormalmente = otra.visteNormalmente;
        this.impecable = otra.impecable;
        this.dudaDeTodos = otra.dudaDeTodos;
        this.pasaAislado = otra.pasaAislado;
        this.diceEstarBien = otra.diceEstarBien;
        this.gustaDeHacerDanoALosDemas = otra.gustaDeHacerDanoALosDemas;
        this.tieneIniciativas = otra.tieneIniciativas;
        this.colabora = otra.colabora;
        this.reticencia = otra.reticencia;
        this.rechazo = otra.rechazo;
        this.mutismo = otra.mutismo;
        this.negativismo = otra.negativismo;
        this.agresividad = otra.agresividad;
        this.sarcasmo = otra.sarcasmo;
        this.pegajosidad = otra.pegajosidad;
        this.colaboracionExcesiva = otra.colaboracionExcesiva;
        this.atento = otra.atento;
        this.seductor = otra.seductor;
        this.evitaConversar = otra.evitaConversar;
        this.impulsivo = otra.impulsivo;
        this.bromista = otra.bromista;
        this.toscoYDescortes = otra.toscoYDescortes;
        this.triste = otra.triste;
        this.irritable = otra.irritable;
        this.popensoARinias = otra.popensoARinias;
        this.suaveYAfable = otra.suaveYAfable;
        this.indiferente = otra.indiferente;
        this.preocupadoYPensativo = otra.preocupadoYPensativo;
        this.tendenciaAlLlanto = otra.tendenciaAlLlanto;
        this.alegre = otra.alegre;
        this.euforico = otra.euforico;
        this.labilDeHumor = otra.labilDeHumor;
        this.inactivo = otra.inactivo;
        this.perezoso = otra.perezoso;
        this.soloHaceCosasIndispensables = otra.soloHaceCosasIndispensables;
        this.realizaSoloUnTipoDeTrabajo = otra.realizaSoloUnTipoDeTrabajo;
        this.dedicadoAVariasActividades = otra.dedicadoAVariasActividades;
        this.apraxia = otra.apraxia;
        this.catatonia = otra.catatonia;
        this.agitacion = otra.agitacion;
        this.amaneramiento = otra.amaneramiento;
        this.estereotipias = otra.estereotipias;
        this.ecopraxia = otra.ecopraxia;
        this.obedienciaAutomatica = otra.obedienciaAutomatica;
        this.negativismoActividades = otra.negativismoActividades;
        this.interceptacionMotriz = otra.interceptacionMotriz;
        this.dispraxias = otra.dispraxias;
        this.actosImpulsivos = otra.actosImpulsivos;
        this.actosObsesivos = otra.actosObsesivos;
        this.ticsActividades = otra.ticsActividades;
        this.liderazgo = otra.liderazgo;
        this.sociabilidad = otra.sociabilidad;
        this.responsabilidad = otra.responsabilidad;
        this.toleranciaNormal = otra.toleranciaNormal;
        this.baja = otra.baja;
        this.colaboracion = otra.colaboracion;
        this.inquietud = otra.inquietud;
        this.acataOrdenesVerbales = otra.acataOrdenesVerbales;
        this.agresivo = otra.agresivo;
        this.extravagante = otra.extravagante;
        this.antisocial = otra.antisocial;
        this.impulsivoComportamiento = otra.impulsivoComportamiento;
        this.reflexivo = otra.reflexivo;
        this.pasivo = otra.pasivo;
        this.apatico = otra.apatico;
        this.dependiente = otra.dependiente;
        this.dominante = otra.dominante;
        this.cauteloso = otra.cauteloso;
        this.quejoso = otra.quejoso;
        this.temeroso = otra.temeroso;
        this.teatral = otra.teatral;
        this.ritualista = otra.ritualista;
        this.aislamiento = otra.aislamiento;
        this.ataquesDePanico = otra.ataquesDePanico;
        this.incapacidadDeRealizacionDeActividadesProductivas = otra.incapacidadDeRealizacionDeActividadesProductivas;
        this.riesgoPotencialOPotencialSuicida = otra.riesgoPotencialOPotencialSuicida;
        this.inhibicion = otra.inhibicion;
        this.apatia = otra.apatia;
        this.humorVariable = otra.humorVariable;
        this.altaSensibilidad = otra.altaSensibilidad;
        this.agresividadAfectividad = otra.agresividadAfectividad;
        this.sumision = otra.sumision;
        this.rabietas = otra.rabietas;
        this.solidaridad = otra.solidaridad;
        this.generosidad = otra.generosidad;
        this.afectuoso = otra.afectuoso;
        this.angustia = otra.angustia;
        this.ansiedadSituacional = otra.ansiedadSituacional;
        this.timidez = otra.timidez;
        this.ansiedadExpectante = otra.ansiedadExpectante;
        this.depresion = otra.depresion;
        this.perdidaRecienteDeInteres = otra.perdidaRecienteDeInteres;
        this.desesperacion = otra.desesperacion;
        this.euforia = otra.euforia;
        this.indiferencia = otra.indiferencia;
        this.aplanamiento = otra.aplanamiento;
        this.ambivalencia = otra.ambivalencia;
        this.irritabilidadAfectividad = otra.irritabilidadAfectividad;
        this.labilidad = otra.labilidad;
        this.tenacidad = otra.tenacidad;
        this.incontinencia = otra.incontinencia;
        this.sentimientosInadecuados = otra.sentimientosInadecuados;
        this.neotimia = otra.neotimia;
        this.disociacionIdeoAfectiva = otra.disociacionIdeoAfectiva;
        this.anhedonia = otra.anhedonia;
        this.observacionesGuiaDeObservacion = otra.observacionesGuiaDeObservacion;
        this.lucidez = otra.lucidez;
        this.obnubilacion = otra.obnubilacion;
        this.estupor = otra.estupor;
        this.coma = otra.coma;
        this.hipervigilancia = otra.hipervigilancia;
        this.confusion = otra.confusion;
        this.estadoCrepuscular = otra.estadoCrepuscular;
        this.onirismo = otra.onirismo;
        this.sonambulismoEstadoDeConciencia = otra.sonambulismoEstadoDeConciencia;
        this.hipercepcion = otra.hipercepcion;
        this.hipoprosexia = otra.hipoprosexia;
        this.disprosexia = otra.disprosexia;
        this.distraibilidad = otra.distraibilidad;
        this.sinAlteracion = otra.sinAlteracion;
        this.hipercepcionSensopercepcion = otra.hipercepcionSensopercepcion;
        this.ilusiones = otra.ilusiones;
        this.seudoalucionciones = otra.seudoalucionciones;
        this.alusinosis = otra.alusinosis;
        this.macropsias = otra.macropsias;
        this.micropsias = otra.micropsias;
        this.noPresenta = otra.noPresenta;
        this.alucinaiones = otra.alucinaiones;
        this.hipermnecia = otra.hipermnecia;
        this.amnesiaDeFijacion = otra.amnesiaDeFijacion;
        this.amnesiaDeEvocacion = otra.amnesiaDeEvocacion;
        this.mixta = otra.mixta;
        this.lacunar = otra.lacunar;
        this.dismensia = otra.dismensia;
        this.paramnesias = otra.paramnesias;
        this.sinAlteracionMemoria = otra.sinAlteracionMemoria;
        this.enlentecimiento = otra.enlentecimiento;
        this.excitacionPsicomotriz = otra.excitacionPsicomotriz;
        this.catatoniaConductaMotora = otra.catatoniaConductaMotora;
        this.actitudesAnormales = otra.actitudesAnormales;
        this.alteracionesDeLaMarcha = otra.alteracionesDeLaMarcha;
        this.inquietudConductaMotora = otra.inquietudConductaMotora;
        this.incoherencia = otra.incoherencia;
        this.bloqueos = otra.bloqueos;
        this.preservacion = otra.preservacion;
        this.prolijidad = otra.prolijidad;
        this.desgragacion = otra.desgragacion;
        this.estereotipiasEstructuraDelPensamiento = otra.estereotipiasEstructuraDelPensamiento;
        this.neologismos = otra.neologismos;
        this.musitacion = otra.musitacion;
        this.retardo = otra.retardo;
        this.aceleracion = otra.aceleracion;
        this.fugaDeIdeas = otra.fugaDeIdeas;
        this.mutismoCursoDelPensamiento = otra.mutismoCursoDelPensamiento;
        this.grandeza = otra.grandeza;
        this.suicidio = otra.suicidio;
        this.autocompasion = otra.autocompasion;
        this.inferioridad = otra.inferioridad;
        this.perdidaDeInteres = otra.perdidaDeInteres;
        this.indecision = otra.indecision;
        this.necesidadDeAyuda = otra.necesidadDeAyuda;
        this.fracaso = otra.fracaso;
        this.ruina = otra.ruina;
        this.autoacusacion = otra.autoacusacion;
        this.resentimiento = otra.resentimiento;
        this.muerte = otra.muerte;
        this.danio = otra.danio;
        this.enfermedad = otra.enfermedad;
        this.grave = otra.grave;
        this.hipocondrias = otra.hipocondrias;
        this.nihilistas = otra.nihilistas;
        this.noTenerSentimientos = otra.noTenerSentimientos;
        this.elMundoHaDejadoDeExistir = otra.elMundoHaDejadoDeExistir;
        this.referencia = otra.referencia;
        this.extravagantes = otra.extravagantes;
        this.fobicas = otra.fobicas;
        this.compulsivas = otra.compulsivas;
        this.obsesivas = otra.obsesivas;
        this.desconfianzas = otra.desconfianzas;
        this.desRelacion = otra.desRelacion;
        this.perdidaDeControl = otra.perdidaDeControl;
        this.serCalumniado = otra.serCalumniado;
        this.deliriosParanoides = otra.deliriosParanoides;
        this.depresivos = otra.depresivos;
        this.misticoReligioso = otra.misticoReligioso;
        this.sexuales = otra.sexuales;
        this.difusos = otra.difusos;
        this.otrosContenidoDelPensamiento = otra.otrosContenidoDelPensamiento;
        this.capacidadDeAutocritica = otra.capacidadDeAutocritica;
        this.heterocritica = otra.heterocritica;
        this.proyectosFuturos = otra.proyectosFuturos;
        this.concienciaDeLaEnfermedad = otra.concienciaDeLaEnfermedad;
        this.desorientacionEnTiempo = otra.desorientacionEnTiempo;
        this.espacio = otra.espacio;
        this.respectoASiMismo = otra.respectoASiMismo;
        this.respectoAOtrasPersonas = otra.respectoAOtrasPersonas;
        this.impresionDiagnostica = otra.impresionDiagnostica;
        this.derivacionInterconsulta = otra.derivacionInterconsulta;
        this.objetivoPlanTratamientoIndividual = otra.objetivoPlanTratamientoIndividual;
        this.estrategiaDeIntervencion = otra.estrategiaDeIntervencion;
        this.indicadorDeLogro = otra.indicadorDeLogro;
        this.tiempoEstimado = otra.tiempoEstimado;
        this.evaluacion = otra.evaluacion;
    }


    // Enum types used in this class
    public enum TipoHorarioDeSuenio {
        NOCTURNO, DIURNO, MIXTO
    }

    public enum CompaniaSuenio {
        SOLO, ACOMPANIADO
    }

    public enum SexoDeNacimiento {
        MASCULINO, FEMENINO
    }

    public enum Genero {
        MASCULINO, FEMENINO, OTROS
    }

    public enum OrientacionSexual {
        HETEROSEXUAL, HOMOSEXUAL, BISEXUAL, ASEXUAL, OTROS
    }

    public enum CuriosidadSexual {
        AUSENTE, MEDIA, ABUNDANTE
    }

    public enum GradoDeInformacion {
        AUSENTE, MEDIA, ABUNDANTE
    }

    public enum ActividadSexual {
        AUSENTE, MEDIA, ABUNDANTE
    }

    public enum Masturbacion {
        AUSENTE, MEDIA, ABUNDANTE
    }

    public enum Promiscuidad {
        AUSENTE, MEDIA, ABUNDANTE
    }

    public enum Disfunciones {
        AUSENTE, MEDIA, ABUNDANTE
    }

    public enum Erotismo {
        AUSENTE, MEDIA, ABUNDANTE
    }

    public enum Parafilias {
        AUSENTE, MEDIA, ABUNDANTE
    }

    public enum Incoherencia {
        AUSENTE, LEVE, MODERADO, GRAVE
    }

    public enum Bloqueos {
        AUSENTE, LEVE, MODERADO, GRAVE
    }

    public enum Preservacion {
        AUSENTE, LEVE, MODERADO, GRAVE
    }

    public enum Prolijidad {
        AUSENTE, LEVE, MODERADO, GRAVE
    }

    public enum Desgragacion {
        AUSENTE, LEVE, MODERADO, GRAVE
    }

    public enum Estereotipias {
        AUSENTE, LEVE, MODERADO, GRAVE
    }

    public enum Neologismos {
        AUSENTE, LEVE, MODERADO, GRAVE
    }

    public enum Musitacion {
        AUSENTE, LEVE, MODERADO, GRAVE
    }

    public enum Desorientacion {
        AUSENTE, LEVE, MODERADO, GRAVE
    }

    public enum Espacio {
        AUSENTE, LEVE, MODERADO, GRAVE
    }

    public enum RespectoASiMismo {
        AUSENTE, LEVE, MODERADO, GRAVE
    }

    public enum RespectoAOtrasPersonas {
        AUSENTE, LEVE, MODERADO, GRAVE
    }

}
