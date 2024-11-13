package com.test.TUdipsaiApi.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "fonoaudiologia")
@Getter
@Setter
public class Fonoaudiologia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_paciente", referencedColumnName = "id")
    private Paciente paciente;

    @Column(name = "estado", nullable = false, columnDefinition = "int default 1")
    private int estado;

    // Características del habla
    @Column(name = "dificultad_pronunciar_palabras")
    private Boolean dificultadPronunciarPalabras;

    @Column(name = "se_traba_cuando_habla")
    private Boolean seTrabaCuandoHabla;

    @Column(name = "se_entiende_lo_que_dice")
    private Boolean seEntiendeLoQueDice;

    @Column(name = "sabe_como_llaman_objetos_entorno")
    private Boolean sabeComoLlamanObjetosEntorno;

    @Column(name = "comprende_lo_que_se_le_dice")
    private Boolean comprendeLoQueSeLeDice;

    @Column(name = "reconoce_fuente_sonora")
    private Boolean reconoceFuenteSonora;

    @Enumerated(EnumType.STRING)
    @Column(name = "comunicacion_preferentemente_forma")
    private ComunicacionPreferentementeForma comunicacionPreferentementeForma;

    @Column(name = "se_a_realizado_examen_audiologico")
    private Boolean seARealizadoExamenAudiologico;

    // Diagnóstico audiológico
    @Column(name = "perdida_auditiva_conductiva_neurosensorial")
    private Boolean perdidaAuditivaConductivaNeurosensorial;

    @Column(name = "hipoacusia_conductiva_bilateral")
    private Boolean hipoacusiaConductivaBilateral;

    @Column(name = "hipoacusia_conductiva_unilateral")
    private Boolean hipoacusiaConductivaUnilateral;

    @Column(name = "hipoacusia_neurosensorial_bilateral")
    private Boolean hipoacusiaNeurosensorialBilateral;

    @Column(name = "hipoacusia_neurosensorial_unilateral")
    private Boolean hipoacusiaNeurosensorialUnilateral;

    // Diagnóstico fonoaudiológico previo
    @Column(name = "trastorno_especifico_pronunciacion")
    private Boolean trastornoEspecificoPronunciacion;

    @Column(name = "trastorno_lenguaje_expresivo")
    private Boolean trastornoLenguajeExpresivo;

    @Column(name = "afasia_adquirida_epilepsia")
    private Boolean afasiaAdquiridaEpilepsia;

    @Column(name = "otros_trastornos_desarrollo_habla")
    private Boolean otrosTrastornosDesarrolloHabla;

    @Column(name = "trastorno_desarrollo_habla_lenguaje")
    private Boolean trastornoDesarrolloHablaLenguaje;

    @Column(name = "trastorno_recepcion_lenguaje")
    private Boolean trastornoRecepcionLenguaje;

    @Column(name = "alteraciones_habla")
    private Boolean alteracionesHabla;

    @Column(name = "disfasia_afasia")
    private Boolean disfasiaAfasia;

    @Column(name = "disartria_anartria")
    private Boolean disartriaAnartria;

    @Column(name = "otras_alteraciones_habla")
    private Boolean otrasAlteracionesHabla;

    @Column(name = "a_tenido_perdida_audicion_pasado")
    private Boolean aTenidoPerdidaAudicionPasado;

    @Column(name = "infecciones_oido_fuertes")
    private Boolean infeccionesOidoFuertes;

    @Column(name = "cual_infecciones_oido_fuertes")
    private String cualInfeccionesOidoFuertes;

    @Column(name = "edad_infecciones_oido_fuertes")
    private Integer edadInfeccionesOidoFuertes;

    // Fonación
    @Column(name = "cree_tono_voz_estudiante_apropiado")
    private Boolean creeTonoVozEstudianteApropiado;

    @Column(name = "respiracion_normal")
    private Boolean respiracionNormal;

    @Column(name = "situaciones_altera_tono_voz")
    private String situacionesAlteraTonoVoz;

    @Column(name = "desde_cuando_alteraciones_voz")
    private String desdeCuandoAlteracionesVoz;

    @Column(name = "tono_de_voz")
    private String tonoDeVoz;

    @Column(name = "respiracion")
    private String respiracion;

    @Column(name = "ronca")
    private Boolean ronca;

    @Column(name = "juego_vocal")
    private Boolean juegoVocal;

    @Column(name = "vocalizacion")
    private Boolean vocalizacion;

    @Column(name = "balbuceo")
    private Boolean balbuceo;

    @Column(name = "silabeo")
    private Boolean silabeo;

    @Column(name = "primeras_palabras")
    private Boolean primerasPalabras;

    @Column(name = "oraciones_dos_palabras")
    private Boolean oracionesDosPalabras;

    @Column(name = "oraciones_tres_palabras")
    private Boolean oracionesTresPalabras;

    @Column(name = "formacion_linguistica_completa")
    private Boolean formacionLinguisticaCompleta;

    @Column(name = "numero_total_palabras")
    private Integer numeroTotalPalabras;

    // Historia clínica Audición
    @Column(name = "perdida_auditiva")
    private Boolean perdidaAuditiva;

    @Column(name = "unilateral")
    private Boolean unilateral;

    @Column(name = "oido_derecho")
    private Boolean oidoDerecho;

    @Column(name = "oido_izquierdo")
    private Boolean oidoIzquierdo;

    @Column(name = "bilateral")
    private Boolean bilateral;

    @Enumerated(EnumType.STRING)
    @Column(name = "grado_perdida")
    private GradoPerdida gradoPerdida;

    @Enumerated(EnumType.STRING)
    @Column(name = "permanecia")
    private Permanecia permanecia;

    @Column(name = "otitis")
    private Boolean otitis;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_otitis")
    private TipoOtitis tipoOtitis;

    @Column(name = "duracion_otitis_inicio")
    private Date duracionOtitisInicio;

    @Column(name = "duracion_otitis_fin")
    private Date duracionOtitisFin;

    @Column(name = "antecedentes_familiares")
    private Boolean antecedentesFamiliares;

    @Column(name = "exposision_ruidos")
    private Boolean exposisionRuidos;

    @Column(name = "duracion_exposision_ruidos_inicio")
    private Date duracionExposisionRuidosInicio;

    @Column(name = "duracion_exposision_ruidos_fin")
    private Date duracionExposisionRuidosFin;

    @Column(name = "ototoxicos")
    private Boolean ototoxicos;

    @Column(name = "infecciones")
    private Boolean infecciones;

    @Column(name = "uso_audifonos")
    private Boolean usoAudifonos;

    @Column(name = "inicio_uso_audifonos")
    private Date inicioUsoAudifonos;

    @Column(name = "fin_uso_audifonos")
    private Date finUsoAudifonos;

    @Column(name = "implante_coclear")
    private Boolean implanteCoclear;

    @Column(name = "tratamiento_fonoaudiologico_previo")
    private Boolean tratamientoFonoaudiologicoPrevio;

    @Column(name = "otalgia")
    private Boolean otalgia;

    @Column(name = "otalgia_unilateral")
    private Boolean otalgiaUnilateral;

    @Column(name = "otalgia_oido_derecho")
    private Boolean otalgiaOidoDerecho;

    @Column(name = "otalgia_oido_izquierdo")
    private Boolean otalgiaOidoIzquierdo;

    @Column(name = "otalgia_bilateral")
    private Boolean otalgiaBilateral;

    @Column(name = "permanencia_otalgia_continua")
    private Boolean permanenciaOtalgiaContinua;

    @Column(name = "permanencia_otalgia_intermitente")
    private Boolean permanenciaOtalgiaIntermitente;

    @Enumerated(EnumType.STRING)
    @Column(name = "grado_permanencia_otalgia")
    private GradoPermanenciaOtalgia gradoPermanenciaOtalgia;

    @Column(name = "asociada_otalgia_infeccion_respiratoria_alta")
    private Boolean asociadaOtalgiaInfeccionRespiratoriaAlta;

    @Column(name = "infeccion_respiratoria_punzante")
    private Boolean infeccionRespiratoriaPunzante;

    @Column(name = "infeccion_respiratoria_pulsatil")
    private Boolean infeccionRespiratoriaPulsatil;

    @Column(name = "infeccion_respiratoria_progresivo")
    private Boolean infeccionRespiratoriaProgresivo;

    @Column(name = "infeccion_respiratoria_opresivo")
    private Boolean infeccionRespiratoriaOpresivo;

    @Column(name = "pruriginoso")
    private Boolean pruriginoso;

    @Column(name = "aumenta_masticar")
    private Boolean aumentaMasticar;

    @Column(name = "disminuye_con_calor_local")
    private Boolean disminuyeConCalorLocal;

    @Column(name = "aumenta_con_calor_local")
    private Boolean aumentaConCalorLocal;

    @Column(name = "otorrea")
    private Boolean otorrea;

    @Column(name = "otorrea_unilateral")
    private Boolean otorreaUnilateral;

    @Column(name = "otorrea_oido_derecho")
    private Boolean otorreaOidoDerecho;

    @Column(name = "otorrea_oido_izquierdo")
    private Boolean otorreaOidoIzquierdo;

    @Column(name = "otorrea_bilateral")
    private Boolean otorreaBilateral;

    @Column(name = "permanencia_otorrea_continua")
    private Boolean permanenciaOtorreaContinua;

    @Column(name = "permanencia_otorrea_intermitente")
    private Boolean permanenciaOtorreaIntermitente;

    @Enumerated(EnumType.STRING)
    @Column(name = "grado_permanencia_otorrea")
    private GradoPermanenciaOtorrea gradoPermanenciaOtorrea;

    @Column(name = "aspecto_claro_otorrea")
    private Boolean aspectoClaroOtorrea;

    @Column(name = "aspecto_seroso_otorrea")
    private Boolean aspectoSerosoOtorrea;

    @Column(name = "aspecto_mucoso_otorrea")
    private Boolean aspectoMucosoOtorrea;

    @Column(name = "aspecto_mucopurulento_otorrea")
    private Boolean aspectoMucopurulentoOtorrea;

    @Column(name = "aspecto_purulento_otorrea")
    private Boolean aspectoPurulentoOtorrea;

    @Column(name = "aspecto_sanguinolento_otorrea")
    private Boolean aspectoSanguinolentoOtorrea;

    @Column(name = "asosiada_otorrea_infeccion_respiratoria_alta")
    private Boolean asosiadaOtorreaInfeccionRespiratoriaAlta;

    @Column(name = "asosiada_otorrea_infeccion_aguda_oido")
    private Boolean asosiadaotorreaInfeccionAgudaOido;

    // Antecedentes auditivos
    @Column(name = "presento_otalgia")
    private Boolean presentoOtalgia;

    @Column(name = "presento_otalgia_bilateral")
    private Boolean presentoOtalgiaBilateral;

    @Column(name = "presento_otalgia_oido_derecho")
    private Boolean presentoOtalgiaOidoDerecho;

    @Column(name = "presento_otalgia_oido_izquierdo")
    private Boolean presentoOtalgiaOidoIzquierdo;

    @Column(name = "presento_sensacion_oido_tapado")
    private Boolean presentoSensacionOidoTapado;

    @Column(name = "presento_sensacion_oido_tapado_bilateral")
    private Boolean presentoSensacionOidoTapadoBilateral;

    @Column(name = "presento_sensacion_oido_tapado_oido_derecho")
    private Boolean presentoSensacionOidoTapadoOidoDerecho;

    @Column(name = "presento_sensacion_oido_tapado_oido_izquierdo")
    private Boolean presentoSensacionOidoTapadoOidoIzquierdo;

    @Column(name = "presento_autofonia")
    private Boolean presentoAutofonia;

    @Column(name = "presento_autofonia_bilateral")
    private Boolean presentoAutofoniaBilateral;

    @Column(name = "presento_autofonia_oido_derecho")
    private Boolean presentoAutofoniaOidoDerecho;

    @Column(name = "presento_autofonia_oido_izquierdo")
    private Boolean presentoAutofoniaOidoIzquierdo;

    @Column(name = "presento_otorrea")
    private Boolean presentoOtorrea;

    @Column(name = "presento_otorrea_bilateral")
    private Boolean presentoOtorreaBilateral;

    @Column(name = "presento_otorrea_oido_derecho")
    private Boolean presentoOtorreaOidoDerecho;

    @Column(name = "presento_otorrea_oido_izquierdo")
    private Boolean presentoOtorreaOidoIzquierdo;

    @Column(name = "aumenta_volumen_tv")
    private Boolean aumentaVolumenTV;

    @Column(name = "sensacion_percibir_tinnitus")
    private Boolean sensacionPercibirTinnitus;

    @Column(name = "expuesto_ruidos_fuertes")
    private Boolean expuestoRuidosFuertes;

    @Column(name = "dificultad_oid_voz_baja")
    private Boolean dificultadOidVozBaja;

    @Column(name = "habla_mas_fuerte_o_mas_despacio")
    private Boolean hablaMasFuerteOMasDespacio;

    @Column(name = "utiliza_ayuda_auditiva")
    private Boolean utilizaAyudaAuditiva;

    @Column(name = "especificar_ayuda_auditiva")
    private String especficarAyudaAuditiva;

    @Column(name = "percibe_sonido_igual_ambos_oidos")
    private Boolean percibeSonidoIgualAmbosOidos;

    @Enumerated(EnumType.STRING)
    @Column(name = "con_que_oido_escucha_mejor")
    private ConQueOidoEscuchaMejor conQueOidoEscuchaMejor;

    @Enumerated(EnumType.STRING)
    @Column(name = "hace_cuanto_tiempo_presenta_sintomas_auditivos")
    private HaceCuantoTiempoPresentaSintomasAuditivos haceCuantoTiempoPresentaSintomasAuditivos;

    // Antecedentes vestibulares
    @Column(name = "falta_equilibrio_caminar")
    private Boolean faltaEquilibrioCaminar;

    @Column(name = "mareos")
    private Boolean mareos;

    @Enumerated(EnumType.STRING)
    @Column(name = "cuando_mareos")
    private CuandoMareos cuandoMareos;

    @Column(name = "vertigo")
    private Boolean vertigo;

    // Descripción de otoscopia oído derecho
    @Enumerated(EnumType.STRING)
    @Column(name = "palpacion_pabellon_oido_derecho")
    private PalpacionOido palpacionPabellonOidoDerecho;

    @Enumerated(EnumType.STRING)
    @Column(name = "palpacion_mastoides_oido_derecho")
    private PalpacionOido palpacionMastoidesOidoDerecho;

    @Enumerated(EnumType.STRING)
    @Column(name = "cae_oido_derecho")
    private CAEOido caeOidoDerecho;

    @Enumerated(EnumType.STRING)
    @Column(name = "obstruccion_oido_derecho")
    private ObstruccionOido obstruccionOidoDerecho;

    @Enumerated(EnumType.STRING)
    @Column(name = "apariencia_menbrana_timpanica_oido_derecho")
    private AparienciaMenbranaTimpanica aparienciaMenbranaTimpanicaOidoDerecho;

    @Column(name = "perforacion_oido_derecho")
    private Boolean perforacionOidoDerecho;

    @Column(name = "burbuja_oido_derecho")
    private Boolean burbujaOidoDerecho;

    @Enumerated(EnumType.STRING)
    @Column(name = "coloracion_oido_derecho")
    private ColoracionOido coloracionOidoDerecho;

    // Descripción de otoscopia oído izquierdo
    @Enumerated(EnumType.STRING)
    @Column(name = "palpacion_pabellon_oido_izquierdo")
    private PalpacionOido palpacionPabellonOidoIzquierdo;

    @Enumerated(EnumType.STRING)
    @Column(name = "palpacion_mastoides_oido_izquierdo")
    private PalpacionOido palpacionMastoidesOidoIzquierdo;

    @Enumerated(EnumType.STRING)
    @Column(name = "cae_oido_izquierdo")
    private CAEOido caeOidoIzquierdo;

    @Enumerated(EnumType.STRING)
    @Column(name = "obstruccion_oido_izquierdo")
    private ObstruccionOido obstruccionOidoIzquierdo;

    @Enumerated(EnumType.STRING)
    @Column(name = "apariencia_menbrana_timpanica_oido_izquierdo")
    private AparienciaMenbranaTimpanica aparienciaMenbranaTimpanicaOidoIzquierdo;

    @Column(name = "perforacion_oido_izquierdo")
    private Boolean perforacionOidoIzquierdo;

    @Column(name = "burbuja_oido_izquierdo")
    private Boolean burbujaOidoIzquierdo;

    @Enumerated(EnumType.STRING)
    @Column(name = "coloracion_oido_izquierdo")
    private ColoracionOido coloracionOidoIzquierdo;

    public Boolean getaTenidoPerdidaAudicionPasado() {
        return aTenidoPerdidaAudicionPasado;
    }

    public Fonoaudiologia(){

    }

    // Constructor de copia
    public Fonoaudiologia(Fonoaudiologia otra) {
        this.id = otra.id;
        this.paciente = otra.paciente;
        this.estado = otra.estado;
        this.dificultadPronunciarPalabras = otra.dificultadPronunciarPalabras;
        this.seTrabaCuandoHabla = otra.seTrabaCuandoHabla;
        this.seEntiendeLoQueDice = otra.seEntiendeLoQueDice;
        this.sabeComoLlamanObjetosEntorno = otra.sabeComoLlamanObjetosEntorno;
        this.comprendeLoQueSeLeDice = otra.comprendeLoQueSeLeDice;
        this.reconoceFuenteSonora = otra.reconoceFuenteSonora;
        this.comunicacionPreferentementeForma = otra.comunicacionPreferentementeForma;
        this.seARealizadoExamenAudiologico = otra.seARealizadoExamenAudiologico;
        this.perdidaAuditivaConductivaNeurosensorial = otra.perdidaAuditivaConductivaNeurosensorial;
        this.hipoacusiaConductivaBilateral = otra.hipoacusiaConductivaBilateral;
        this.hipoacusiaConductivaUnilateral = otra.hipoacusiaConductivaUnilateral;
        this.hipoacusiaNeurosensorialBilateral = otra.hipoacusiaNeurosensorialBilateral;
        this.hipoacusiaNeurosensorialUnilateral = otra.hipoacusiaNeurosensorialUnilateral;
        this.trastornoEspecificoPronunciacion = otra.trastornoEspecificoPronunciacion;
        this.trastornoLenguajeExpresivo = otra.trastornoLenguajeExpresivo;
        this.afasiaAdquiridaEpilepsia = otra.afasiaAdquiridaEpilepsia;
        this.otrosTrastornosDesarrolloHabla = otra.otrosTrastornosDesarrolloHabla;
        this.trastornoDesarrolloHablaLenguaje = otra.trastornoDesarrolloHablaLenguaje;
        this.trastornoRecepcionLenguaje = otra.trastornoRecepcionLenguaje;
        this.alteracionesHabla = otra.alteracionesHabla;
        this.disfasiaAfasia = otra.disfasiaAfasia;
        this.disartriaAnartria = otra.disartriaAnartria;
        this.otrasAlteracionesHabla = otra.otrasAlteracionesHabla;
        this.aTenidoPerdidaAudicionPasado = otra.aTenidoPerdidaAudicionPasado;
        this.infeccionesOidoFuertes = otra.infeccionesOidoFuertes;
        this.cualInfeccionesOidoFuertes = otra.cualInfeccionesOidoFuertes;
        this.edadInfeccionesOidoFuertes = otra.edadInfeccionesOidoFuertes;
        this.creeTonoVozEstudianteApropiado = otra.creeTonoVozEstudianteApropiado;
        this.respiracionNormal = otra.respiracionNormal;
        this.situacionesAlteraTonoVoz = otra.situacionesAlteraTonoVoz;
        this.desdeCuandoAlteracionesVoz = otra.desdeCuandoAlteracionesVoz;
        this.tonoDeVoz = otra.tonoDeVoz;
        this.respiracion = otra.respiracion;
        this.ronca = otra.ronca;
        this.juegoVocal = otra.juegoVocal;
        this.vocalizacion = otra.vocalizacion;
        this.balbuceo = otra.balbuceo;
        this.silabeo = otra.silabeo;
        this.primerasPalabras = otra.primerasPalabras;
        this.oracionesDosPalabras = otra.oracionesDosPalabras;
        this.oracionesTresPalabras = otra.oracionesTresPalabras;
        this.formacionLinguisticaCompleta = otra.formacionLinguisticaCompleta;
        this.numeroTotalPalabras = otra.numeroTotalPalabras;
        this.perdidaAuditiva = otra.perdidaAuditiva;
        this.unilateral = otra.unilateral;
        this.oidoDerecho = otra.oidoDerecho;
        this.oidoIzquierdo = otra.oidoIzquierdo;
        this.bilateral = otra.bilateral;
        this.gradoPerdida = otra.gradoPerdida;
        this.permanecia = otra.permanecia;
        this.otitis = otra.otitis;
        this.tipoOtitis = otra.tipoOtitis;
        this.duracionOtitisInicio = otra.duracionOtitisInicio;
        this.duracionOtitisFin = otra.duracionOtitisFin;
        this.antecedentesFamiliares = otra.antecedentesFamiliares;
        this.exposisionRuidos = otra.exposisionRuidos;
        this.duracionExposisionRuidosInicio = otra.duracionExposisionRuidosInicio;
        this.duracionExposisionRuidosFin = otra.duracionExposisionRuidosFin;
        this.ototoxicos = otra.ototoxicos;
        this.infecciones = otra.infecciones;
        this.usoAudifonos = otra.usoAudifonos;
        this.inicioUsoAudifonos = otra.inicioUsoAudifonos;
        this.finUsoAudifonos = otra.finUsoAudifonos;
        this.implanteCoclear = otra.implanteCoclear;
        this.tratamientoFonoaudiologicoPrevio = otra.tratamientoFonoaudiologicoPrevio;
        this.otalgia = otra.otalgia;
        this.otalgiaUnilateral = otra.otalgiaUnilateral;
        this.otalgiaOidoDerecho = otra.otalgiaOidoDerecho;
        this.otalgiaOidoIzquierdo = otra.otalgiaOidoIzquierdo;
        this.otalgiaBilateral = otra.otalgiaBilateral;
        this.permanenciaOtalgiaContinua = otra.permanenciaOtalgiaContinua;
        this.permanenciaOtalgiaIntermitente = otra.permanenciaOtalgiaIntermitente;
        this.gradoPermanenciaOtalgia = otra.gradoPermanenciaOtalgia;
        this.asociadaOtalgiaInfeccionRespiratoriaAlta = otra.asociadaOtalgiaInfeccionRespiratoriaAlta;
        this.infeccionRespiratoriaPunzante = otra.infeccionRespiratoriaPunzante;
        this.infeccionRespiratoriaPulsatil = otra.infeccionRespiratoriaPulsatil;
        this.infeccionRespiratoriaProgresivo = otra.infeccionRespiratoriaProgresivo;
        this.infeccionRespiratoriaOpresivo = otra.infeccionRespiratoriaOpresivo;
        this.pruriginoso = otra.pruriginoso;
        this.aumentaMasticar = otra.aumentaMasticar;
        this.disminuyeConCalorLocal = otra.disminuyeConCalorLocal;
        this.aumentaConCalorLocal = otra.aumentaConCalorLocal;
        this.otorrea = otra.otorrea;
        this.otorreaUnilateral = otra.otorreaUnilateral;
        this.otorreaOidoDerecho = otra.otorreaOidoDerecho;
        this.otorreaOidoIzquierdo = otra.otorreaOidoIzquierdo;
        this.otorreaBilateral = otra.otorreaBilateral;
        this.permanenciaOtorreaContinua = otra.permanenciaOtorreaContinua;
        this.permanenciaOtorreaIntermitente = otra.permanenciaOtorreaIntermitente;
        this.gradoPermanenciaOtorrea = otra.gradoPermanenciaOtorrea;
        this.aspectoClaroOtorrea = otra.aspectoClaroOtorrea;
        this.aspectoSerosoOtorrea = otra.aspectoSerosoOtorrea;
        this.aspectoMucosoOtorrea = otra.aspectoMucosoOtorrea;
        this.aspectoMucopurulentoOtorrea = otra.aspectoMucopurulentoOtorrea;
        this.aspectoPurulentoOtorrea = otra.aspectoPurulentoOtorrea;
        this.aspectoSanguinolentoOtorrea = otra.aspectoSanguinolentoOtorrea;
        this.asosiadaOtorreaInfeccionRespiratoriaAlta = otra.asosiadaOtorreaInfeccionRespiratoriaAlta;
        this.asosiadaotorreaInfeccionAgudaOido = otra.asosiadaotorreaInfeccionAgudaOido;
        this.presentoOtalgia = otra.presentoOtalgia;
        this.presentoOtalgiaBilateral = otra.presentoOtalgiaBilateral;
        this.presentoOtalgiaOidoDerecho = otra.presentoOtalgiaOidoDerecho;
        this.presentoOtalgiaOidoIzquierdo = otra.presentoOtalgiaOidoIzquierdo;
        this.presentoSensacionOidoTapado = otra.presentoSensacionOidoTapado;
        this.presentoSensacionOidoTapadoBilateral = otra.presentoSensacionOidoTapadoBilateral;
        this.presentoSensacionOidoTapadoOidoDerecho = otra.presentoSensacionOidoTapadoOidoDerecho;
        this.presentoSensacionOidoTapadoOidoIzquierdo = otra.presentoSensacionOidoTapadoOidoIzquierdo;
        this.presentoAutofonia = otra.presentoAutofonia;
        this.presentoAutofoniaBilateral = otra.presentoAutofoniaBilateral;
        this.presentoAutofoniaOidoDerecho = otra.presentoAutofoniaOidoDerecho;
        this.presentoAutofoniaOidoIzquierdo = otra.presentoAutofoniaOidoIzquierdo;
        this.presentoOtorrea = otra.presentoOtorrea;
        this.presentoOtorreaBilateral = otra.presentoOtorreaBilateral;
        this.presentoOtorreaOidoDerecho = otra.presentoOtorreaOidoDerecho;
        this.presentoOtorreaOidoIzquierdo = otra.presentoOtorreaOidoIzquierdo;
        this.aumentaVolumenTV = otra.aumentaVolumenTV;
        this.sensacionPercibirTinnitus = otra.sensacionPercibirTinnitus;
        this.expuestoRuidosFuertes = otra.expuestoRuidosFuertes;
        this.dificultadOidVozBaja = otra.dificultadOidVozBaja;
        this.hablaMasFuerteOMasDespacio = otra.hablaMasFuerteOMasDespacio;
        this.utilizaAyudaAuditiva = otra.utilizaAyudaAuditiva;
        this.especficarAyudaAuditiva = otra.especficarAyudaAuditiva;
        this.percibeSonidoIgualAmbosOidos = otra.percibeSonidoIgualAmbosOidos;
        this.conQueOidoEscuchaMejor = otra.conQueOidoEscuchaMejor;
        this.haceCuantoTiempoPresentaSintomasAuditivos = otra.haceCuantoTiempoPresentaSintomasAuditivos;
        this.faltaEquilibrioCaminar = otra.faltaEquilibrioCaminar;
        this.mareos = otra.mareos;
        this.cuandoMareos = otra.cuandoMareos;
        this.vertigo = otra.vertigo;
        this.palpacionPabellonOidoDerecho = otra.palpacionPabellonOidoDerecho;
        this.palpacionMastoidesOidoDerecho = otra.palpacionMastoidesOidoDerecho;
        this.caeOidoDerecho = otra.caeOidoDerecho;
        this.obstruccionOidoDerecho = otra.obstruccionOidoDerecho;
        this.aparienciaMenbranaTimpanicaOidoDerecho = otra.aparienciaMenbranaTimpanicaOidoDerecho;
        this.perforacionOidoDerecho = otra.perforacionOidoDerecho;
        this.burbujaOidoDerecho = otra.burbujaOidoDerecho;
        this.coloracionOidoDerecho = otra.coloracionOidoDerecho;
        this.palpacionPabellonOidoIzquierdo = otra.palpacionPabellonOidoIzquierdo;
        this.palpacionMastoidesOidoIzquierdo = otra.palpacionMastoidesOidoIzquierdo;
        this.caeOidoIzquierdo = otra.caeOidoIzquierdo;
        this.obstruccionOidoIzquierdo = otra.obstruccionOidoIzquierdo;
        this.aparienciaMenbranaTimpanicaOidoIzquierdo = otra.aparienciaMenbranaTimpanicaOidoIzquierdo;
        this.perforacionOidoIzquierdo = otra.perforacionOidoIzquierdo;
        this.burbujaOidoIzquierdo = otra.burbujaOidoIzquierdo;
        this.coloracionOidoIzquierdo = otra.coloracionOidoIzquierdo;
    }



    // Enums
    public enum ComunicacionPreferentementeForma {
        VERBAL, GESTUAL, MIXTA
    }

    public enum GradoPerdida {
        SÚBITA, PROGRESIVA
    }

    public enum Permanecia {
        TEMPORAL, FLUCTUANTE, PERMANENTE
    }

    public enum TipoOtitis {
        MEDIO, AGUDO
    }

    public enum GradoPermanenciaOtalgia {
        MEDIA, AGUDA, CRÓNICA
    }

    public enum GradoPermanenciaOtorrea {
        MEDIA, AGUDA, CRÓNICA
    }

    public enum ConQueOidoEscuchaMejor {
        AMBOS, DERECHO, IZQUIERDO
    }

    public enum HaceCuantoTiempoPresentaSintomasAuditivos {
        DÍAS, SEMANAS, MESES, AÑOS
    }

    public enum CuandoMareos {
        SIEMPRE, A_VECES, AL_CAMINAR, AL_PARARSE
    }

    public enum PalpacionOido {
        NORMAL, DOLOR, INFLAMADA
    }

    public enum CAEOido {
        NORMAL, IRRITADO, SUPURACION, INFLAMADO
    }

    public enum ObstruccionOido {
        SI, NO, TOTAL, PARCIAL
    }

    public enum AparienciaMenbranaTimpanica {
        NORMAL, IRRITADO, SUPURACION, INFLAMADA
    }

    public enum ColoracionOido {
        NORMAL, AZUL, ERITEMATOSA, OPACA
    }
}
