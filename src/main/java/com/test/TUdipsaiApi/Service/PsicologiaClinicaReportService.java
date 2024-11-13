package com.test.TUdipsaiApi.Service;

import com.test.TUdipsaiApi.Model.PsicologiaClinica;
import com.test.TUdipsaiApi.Model.Paciente;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

@Service
public class PsicologiaClinicaReportService {

    @Autowired
    private PsicologiaClinicaService psicologiaClinicaService;

    @Autowired
    private ReportTransformService reportTransformService;

    private static final String IMAGE_DIRECTORY = System.getProperty("user.home") + "/UdipsaiImagenesPacientes/";


    public byte[] generarReportePsicologiaClinica(int idPsicologiaClinica) {
        try {
            // Obtener el registro de Psicología Clínica por ID
            PsicologiaClinica psicologiaClinica = psicologiaClinicaService.obtenerFichaPorId(idPsicologiaClinica)
                    .orElseThrow(() -> new RuntimeException("Registro de Psicología Clínica no encontrado"));

            // Obtener el paciente asociado
            Paciente paciente = psicologiaClinica.getPaciente();
            if (paciente == null) {
                throw new RuntimeException("Paciente no encontrado para el registro de Psicología Clínica");
            }

            // Leer la imagen del paciente desde el disco local comprimida
            String nombresCedula = paciente.getNombresApellidos().replace(" ", "_") + "-" + paciente.getCedula();
            String imageFilePath = IMAGE_DIRECTORY + "imagen-" + nombresCedula + ".txt.gz";

            String imagenPacienteBase64 = "";
            File imageFile = new File(imageFilePath);
            if (imageFile.exists()) {
                try (InputStream fileInputStream = new FileInputStream(imageFile);
                     GZIPInputStream gzipInputStream = new GZIPInputStream(fileInputStream)) {
                    byte[] imageBytes = IOUtils.toByteArray(gzipInputStream);
                    imagenPacienteBase64 = Base64.encodeBase64String(imageBytes);
                }
            }

            // Establecer la URL de la imagen de fondo
            String backgroundImage = "https://upload.wikimedia.org/wikipedia/commons/6/6c/Logo_Universidad_Cat%C3%B3lica_de_Cuenca.jpg";

            // Cargar la plantilla JRXML
            ClassPathResource resource = new ClassPathResource("reportes/psicologia_clinica.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(resource.getInputStream());

            // Parámetros adicionales para el reporte
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("titulo", "Reporte de Psicología Clínica");
            parameters.put("backgroundImage", backgroundImage);

            // Utilizar los métodos personalizados para los valores booleanos
            parameters.put("hipersomnia", psicologiaClinica.getHipersomnia() ? "Sí" : "No");
            parameters.put("dificultadDeConciliarElSuenio", psicologiaClinica.getDificultadDeConciliarElSuenio() ? "Sí" : "No");
            parameters.put("despertarFrecuente", psicologiaClinica.getDespertarFrecuente() ? "Sí" : "No");
            parameters.put("despertarPrematuro", psicologiaClinica.getDespertarPrematuro() ? "Sí" : "No");
            parameters.put("sonambulismo", psicologiaClinica.getSonambulismo() ? "Sí" : "No");
            parameters.put("temores", psicologiaClinica.getTemores() ? "Sí" : "No");
            parameters.put("destructividad", psicologiaClinica.getDestructividad() ? "Sí" : "No");
            parameters.put("nerviosismo", psicologiaClinica.getNerviosismo() ? "Sí" : "No");
            parameters.put("irritabilidad", psicologiaClinica.getIrritabilidad() ? "Sí" : "No");
            parameters.put("egocentrismo", psicologiaClinica.getEgocentrismo() ? "Sí" : "No");
            parameters.put("regresiones", psicologiaClinica.getRegresiones() ? "Sí" : "No");
            parameters.put("tics", psicologiaClinica.getTics() ? "Sí" : "No");
            parameters.put("hurto", psicologiaClinica.getHurto() ? "Sí" : "No");
            parameters.put("mentira", psicologiaClinica.getMentira() ? "Sí" : "No");
            parameters.put("cuidadoPersonal", psicologiaClinica.getCuidadoPersonal() ? "Sí" : "No");
            parameters.put("palabrasRaras", psicologiaClinica.getPalabrasRaras() ? "Sí" : "No");
            parameters.put("logicoYClaro", psicologiaClinica.getLogicoYClaro() ? "Sí" : "No");
            parameters.put("vozMonotona", psicologiaClinica.getVozMonotona() ? "Sí" : "No");
            parameters.put("malHablado", psicologiaClinica.getMalHablado() ? "Sí" : "No");
            parameters.put("lentoYTeatral", psicologiaClinica.getLentoYTeatral() ? "Sí" : "No");
            parameters.put("pesimista", psicologiaClinica.getPesimista() ? "Sí" : "No");
            parameters.put("hiriente", psicologiaClinica.getHiriente() ? "Sí" : "No");
            parameters.put("charlatan", psicologiaClinica.getCharlatan() ? "Sí" : "No");
            parameters.put("incoherente", psicologiaClinica.getIncoherente() ? "Sí" : "No");
            parameters.put("verborrea", psicologiaClinica.getVerborrea() ? "Sí" : "No");
            parameters.put("abatimiento", psicologiaClinica.getAbatimiento() ? "Sí" : "No");
            parameters.put("tension", psicologiaClinica.getTension() ? "Sí" : "No");
            parameters.put("perplejidad", psicologiaClinica.getPerplejidad() ? "Sí" : "No");
            parameters.put("suspicacia", psicologiaClinica.getSuspicacia() ? "Sí" : "No");
            parameters.put("enfado", psicologiaClinica.getEnfado() ? "Sí" : "No");
            parameters.put("preocupacion", psicologiaClinica.getPreocupacion() ? "Sí" : "No");
            parameters.put("obscenidad", psicologiaClinica.getObscenidad() ? "Sí" : "No");
            parameters.put("disartria", psicologiaClinica.getDisartria() ? "Sí" : "No");
            parameters.put("afasiaExpresiva", psicologiaClinica.getAfasiaExpresiva() ? "Sí" : "No");
            parameters.put("afasiaReceptiva", psicologiaClinica.getAfasiaReceptiva() ? "Sí" : "No");
            parameters.put("afasiaAnomica", psicologiaClinica.getAfasiaAnomica() ? "Sí" : "No");
            parameters.put("afasiaGlobal", psicologiaClinica.getAfasiaGlobal() ? "Sí" : "No");
            parameters.put("ecolalia", psicologiaClinica.getEcolalia() ? "Sí" : "No");
            parameters.put("palilalia", psicologiaClinica.getPalilalia() ? "Sí" : "No");
            parameters.put("ensimismamiento", psicologiaClinica.getEnsimismamiento() ? "Sí" : "No");
            parameters.put("hayQueGuiarlo", psicologiaClinica.getHayQueGuiarlo() ? "Sí" : "No");
            parameters.put("molestoso", psicologiaClinica.getMolestoso() ? "Sí" : "No");
            parameters.put("lento", psicologiaClinica.getLento() ? "Sí" : "No");
            parameters.put("noDeseaHacerNada", psicologiaClinica.getNoDeseaHacerNada() ? "Sí" : "No");
            parameters.put("haceCosasExtranas", psicologiaClinica.getHaceCosasExtranas() ? "Sí" : "No");
            parameters.put("aislado", psicologiaClinica.getAislado() ? "Sí" : "No");
            parameters.put("participaEnGrupos", psicologiaClinica.getParticipaEnGrupos() ? "Sí" : "No");
            parameters.put("esViolento", psicologiaClinica.getEsViolento() ? "Sí" : "No");
            parameters.put("callado", psicologiaClinica.getCallado() ? "Sí" : "No");
            parameters.put("amigableYCooperador", psicologiaClinica.getAmigableYCooperador() ? "Sí" : "No");
            parameters.put("adaptable", psicologiaClinica.getAdaptable() ? "Sí" : "No");
            parameters.put("inquieto", psicologiaClinica.getInquieto() ? "Sí" : "No");
            parameters.put("nervioso", psicologiaClinica.getNervioso() ? "Sí" : "No");
            parameters.put("tieneAmigosIntimos", psicologiaClinica.getTieneAmigosIntimos() ? "Sí" : "No");
            parameters.put("confuso", psicologiaClinica.getConfuso() ? "Sí" : "No");
            parameters.put("centradoEnSiMismo", psicologiaClinica.getCentradoEnSiMismo() ? "Sí" : "No");
            parameters.put("olvidadizo", psicologiaClinica.getOlvidadizo() ? "Sí" : "No");
            parameters.put("piensaYRespondeBien", psicologiaClinica.getPiensaYRespondeBien() ? "Sí" : "No");
            parameters.put("pocosPensamientos", psicologiaClinica.getPocosPensamientos() ? "Sí" : "No");
            parameters.put("noVeLosErrores", psicologiaClinica.getNoVeLosErrores() ? "Sí" : "No");
            parameters.put("actuaInfaltilmente", psicologiaClinica.getActuaInfaltilmente() ? "Sí" : "No");
            parameters.put("desconfia", psicologiaClinica.getDesconfia() ? "Sí" : "No");
            parameters.put("hosco", psicologiaClinica.getHosco() ? "Sí" : "No");
            parameters.put("fastidiado", psicologiaClinica.getFastidiado() ? "Sí" : "No");
            parameters.put("cansado", psicologiaClinica.getCansado() ? "Sí" : "No");
            parameters.put("visteRaramente", psicologiaClinica.getVisteRaramente() ? "Sí" : "No");
            parameters.put("desordenado", psicologiaClinica.getDesordenado() ? "Sí" : "No");
            parameters.put("mugrosoYFachoso", psicologiaClinica.getMugrosoYFachoso() ? "Sí" : "No");
            parameters.put("excesoDeRopas", psicologiaClinica.getExcesoDeRopas() ? "Sí" : "No");
            parameters.put("dramaticoYTeatral", psicologiaClinica.getDramaticoYTeatral() ? "Sí" : "No");
            parameters.put("visteNormalmente", psicologiaClinica.getVisteNormalmente() ? "Sí" : "No");
            parameters.put("impecable", psicologiaClinica.getImpecable() ? "Sí" : "No");
            parameters.put("dudaDeTodos", psicologiaClinica.getDudaDeTodos() ? "Sí" : "No");
            parameters.put("pasaAislado", psicologiaClinica.getPasaAislado() ? "Sí" : "No");
            parameters.put("diceEstarBien", psicologiaClinica.getDiceEstarBien() ? "Sí" : "No");
            parameters.put("gustaDeHacerDanoALosDemas", psicologiaClinica.getGustaDeHacerDanoALosDemas() ? "Sí" : "No");
            parameters.put("tieneIniciativas", psicologiaClinica.getTieneIniciativas() ? "Sí" : "No");
            parameters.put("colabora", psicologiaClinica.getColabora() ? "Sí" : "No");
            parameters.put("reticencia", psicologiaClinica.getReticencia() ? "Sí" : "No");
            parameters.put("rechazo", psicologiaClinica.getRechazo() ? "Sí" : "No");
            parameters.put("mutismo", psicologiaClinica.getMutismo() ? "Sí" : "No");
            parameters.put("negativismo", psicologiaClinica.getNegativismo() ? "Sí" : "No");
            parameters.put("agresividad", psicologiaClinica.getAgresividad() ? "Sí" : "No");
            parameters.put("sarcasmo", psicologiaClinica.getSarcasmo() ? "Sí" : "No");
            parameters.put("pegajosidad", psicologiaClinica.getPegajosidad() ? "Sí" : "No");
            parameters.put("colaboracionExcesiva", psicologiaClinica.getColaboracionExcesiva() ? "Sí" : "No");
            parameters.put("atento", psicologiaClinica.getAtento() ? "Sí" : "No");
            parameters.put("seductor", psicologiaClinica.getSeductor() ? "Sí" : "No");
            parameters.put("evitaConversar", psicologiaClinica.getEvitaConversar() ? "Sí" : "No");
            parameters.put("impulsivo", psicologiaClinica.getImpulsivo() ? "Sí" : "No");
            parameters.put("bromista", psicologiaClinica.getBromista() ? "Sí" : "No");
            parameters.put("toscoYDescortes", psicologiaClinica.getToscoYDescortes() ? "Sí" : "No");
            parameters.put("triste", psicologiaClinica.getTriste() ? "Sí" : "No");
            parameters.put("irritable", psicologiaClinica.getIrritable() ? "Sí" : "No");
            parameters.put("popensoARinias", psicologiaClinica.getPopensoARinias() ? "Sí" : "No");
            parameters.put("suaveYAfable", psicologiaClinica.getSuaveYAfable() ? "Sí" : "No");
            parameters.put("indiferente", psicologiaClinica.getIndiferente() ? "Sí" : "No");
            parameters.put("preocupadoYPensativo", psicologiaClinica.getPreocupadoYPensativo() ? "Sí" : "No");
            parameters.put("tendenciaAlLlanto", psicologiaClinica.getTendenciaAlLlanto() ? "Sí" : "No");
            parameters.put("alegre", psicologiaClinica.getAlegre() ? "Sí" : "No");
            parameters.put("euforico", psicologiaClinica.getEuforico() ? "Sí" : "No");
            parameters.put("labilDeHumor", psicologiaClinica.getLabilDeHumor() ? "Sí" : "No");
            parameters.put("inactivo", psicologiaClinica.getInactivo() ? "Sí" : "No");
            parameters.put("perezoso", psicologiaClinica.getPerezoso() ? "Sí" : "No");
            parameters.put("soloHaceCosasIndispensables", psicologiaClinica.getSoloHaceCosasIndispensables() ? "Sí" : "No");
            parameters.put("realizaSoloUnTipoDeTrabajo", psicologiaClinica.getRealizaSoloUnTipoDeTrabajo() ? "Sí" : "No");
            parameters.put("dedicadoAVariasActividades", psicologiaClinica.getDedicadoAVariasActividades() ? "Sí" : "No");
            parameters.put("apraxia", psicologiaClinica.getApraxia() ? "Sí" : "No");
            parameters.put("catatonia", psicologiaClinica.getCatatonia() ? "Sí" : "No");
            parameters.put("agitacion", psicologiaClinica.getAgitacion() ? "Sí" : "No");
            parameters.put("amaneramiento", psicologiaClinica.getAmaneramiento() ? "Sí" : "No");
            parameters.put("estereotipias", psicologiaClinica.getEstereotipias() ? "Sí" : "No");
            parameters.put("ecopraxia", psicologiaClinica.getEcopraxia() ? "Sí" : "No");
            parameters.put("obedienciaAutomatica", psicologiaClinica.getObedienciaAutomatica() ? "Sí" : "No");
            parameters.put("negativismoActividades", psicologiaClinica.getNegativismoActividades() ? "Sí" : "No");
            parameters.put("interceptacionMotriz", psicologiaClinica.getInterceptacionMotriz() ? "Sí" : "No");
            parameters.put("dispraxias", psicologiaClinica.getDispraxias() ? "Sí" : "No");
            parameters.put("actosImpulsivos", psicologiaClinica.getActosImpulsivos() ? "Sí" : "No");
            parameters.put("actosObsesivos", psicologiaClinica.getActosObsesivos() ? "Sí" : "No");
            parameters.put("ticsActividades", psicologiaClinica.getTicsActividades() ? "Sí" : "No");
            parameters.put("liderazgo", psicologiaClinica.getLiderazgo() ? "Sí" : "No");
            parameters.put("sociabilidad", psicologiaClinica.getSociabilidad() ? "Sí" : "No");
            parameters.put("responsabilidad", psicologiaClinica.getResponsabilidad() ? "Sí" : "No");
            parameters.put("toleranciaNormal", psicologiaClinica.getToleranciaNormal() ? "Sí" : "No");
            parameters.put("baja", psicologiaClinica.getBaja() ? "Sí" : "No");
            parameters.put("colaboracion", psicologiaClinica.getColaboracion() ? "Sí" : "No");
            parameters.put("inquietud", psicologiaClinica.getInquietud() ? "Sí" : "No");
            parameters.put("acataOrdenesVerbales", psicologiaClinica.getAcataOrdenesVerbales() ? "Sí" : "No");
            parameters.put("agresivo", psicologiaClinica.getAgresivo() ? "Sí" : "No");
            parameters.put("extravagante", psicologiaClinica.getExtravagante() ? "Sí" : "No");
            parameters.put("antisocial", psicologiaClinica.getAntisocial() ? "Sí" : "No");
            parameters.put("impulsivoComportamiento", psicologiaClinica.getImpulsivoComportamiento() ? "Sí" : "No");
            parameters.put("reflexivo", psicologiaClinica.getReflexivo() ? "Sí" : "No");
            parameters.put("pasivo", psicologiaClinica.getPasivo() ? "Sí" : "No");
            parameters.put("apatico", psicologiaClinica.getApatico() ? "Sí" : "No");
            parameters.put("dependiente", psicologiaClinica.getDependiente() ? "Sí" : "No");
            parameters.put("dominante", psicologiaClinica.getDominante() ? "Sí" : "No");
            parameters.put("cauteloso", psicologiaClinica.getCauteloso() ? "Sí" : "No");
            parameters.put("quejoso", psicologiaClinica.getQuejoso() ? "Sí" : "No");
            parameters.put("temeroso", psicologiaClinica.getTemeroso() ? "Sí" : "No");
            parameters.put("teatral", psicologiaClinica.getTeatral() ? "Sí" : "No");
            parameters.put("ritualista", psicologiaClinica.getRitualista() ? "Sí" : "No");
            parameters.put("aislamiento", psicologiaClinica.getAislamiento() ? "Sí" : "No");
            parameters.put("ataquesDePanico", psicologiaClinica.getAtaquesDePanico() ? "Sí" : "No");
            parameters.put("incapacidadDeRealizacionDeActividadesProductivas", psicologiaClinica.getIncapacidadDeRealizacionDeActividadesProductivas() ? "Sí" : "No");
            parameters.put("riesgoPotencialOPotencialSuicida", psicologiaClinica.getRiesgoPotencialOPotencialSuicida() ? "Sí" : "No");
            parameters.put("inhibicion", psicologiaClinica.getInhibicion() ? "Sí" : "No");
            parameters.put("apatia", psicologiaClinica.getApatia() ? "Sí" : "No");
            parameters.put("humorVariable", psicologiaClinica.getHumorVariable() ? "Sí" : "No");
            parameters.put("altaSensibilidad", psicologiaClinica.getAltaSensibilidad() ? "Sí" : "No");
            parameters.put("agresividadAfectividad", psicologiaClinica.getAgresividadAfectividad() ? "Sí" : "No");
            parameters.put("sumision", psicologiaClinica.getSumision() ? "Sí" : "No");
            parameters.put("rabietas", psicologiaClinica.getRabietas() ? "Sí" : "No");
            parameters.put("solidaridad", psicologiaClinica.getSolidaridad() ? "Sí" : "No");
            parameters.put("generosidad", psicologiaClinica.getGenerosidad() ? "Sí" : "No");
            parameters.put("afectuoso", psicologiaClinica.getAfectuoso() ? "Sí" : "No");
            parameters.put("angustia", psicologiaClinica.getAngustia() ? "Sí" : "No");
            parameters.put("ansiedadSituacional", psicologiaClinica.getAnsiedadSituacional() ? "Sí" : "No");
            parameters.put("timidez", psicologiaClinica.getTimidez() ? "Sí" : "No");
            parameters.put("ansiedadExpectante", psicologiaClinica.getAnsiedadExpectante() ? "Sí" : "No");
            parameters.put("depresion", psicologiaClinica.getDepresion() ? "Sí" : "No");
            parameters.put("perdidaRecienteDeInteres", psicologiaClinica.getPerdidaRecienteDeInteres() ? "Sí" : "No");
            parameters.put("desesperacion", psicologiaClinica.getDesesperacion() ? "Sí" : "No");
            parameters.put("euforia", psicologiaClinica.getEuforia() ? "Sí" : "No");
            parameters.put("indiferencia", psicologiaClinica.getIndiferencia() ? "Sí" : "No");
            parameters.put("aplanamiento", psicologiaClinica.getAplanamiento() ? "Sí" : "No");
            parameters.put("ambivalencia", psicologiaClinica.getAmbivalencia() ? "Sí" : "No");
            parameters.put("irritabilidadAfectividad", psicologiaClinica.getIrritabilidadAfectividad() ? "Sí" : "No");
            parameters.put("labilidad", psicologiaClinica.getLabilidad() ? "Sí" : "No");
            parameters.put("tenacidad", psicologiaClinica.getTenacidad() ? "Sí" : "No");
            parameters.put("incontinencia", psicologiaClinica.getIncontinencia() ? "Sí" : "No");
            parameters.put("sentimientosInadecuados", psicologiaClinica.getSentimientosInadecuados() ? "Sí" : "No");
            parameters.put("neotimia", psicologiaClinica.getNeotimia() ? "Sí" : "No");
            parameters.put("disociacionIdeoAfectiva", psicologiaClinica.getDisociacionIdeoAfectiva() ? "Sí" : "No");
            parameters.put("anhedonia", psicologiaClinica.getAnhedonia() ? "Sí" : "No");
            parameters.put("lucidez", psicologiaClinica.getLucidez() ? "Sí" : "No");
            parameters.put("obnubilacion", psicologiaClinica.getObnubilacion() ? "Sí" : "No");
            parameters.put("estupor", psicologiaClinica.getEstupor() ? "Sí" : "No");
            parameters.put("coma", psicologiaClinica.getComa() ? "Sí" : "No");
            parameters.put("hipervigilancia", psicologiaClinica.getHipervigilancia() ? "Sí" : "No");
            parameters.put("confusion", psicologiaClinica.getConfusion() ? "Sí" : "No");
            parameters.put("estadoCrepuscular", psicologiaClinica.getEstadoCrepuscular() ? "Sí" : "No");
            parameters.put("onirismo", psicologiaClinica.getOnirismo() ? "Sí" : "No");
            parameters.put("sonambulismoEstadoDeConciencia", psicologiaClinica.getSonambulismoEstadoDeConciencia() ? "Sí" : "No");
            parameters.put("hipercepcion", psicologiaClinica.getHipercepcion() ? "Sí" : "No");
            parameters.put("hipoprosexia", psicologiaClinica.getHipoprosexia() ? "Sí" : "No");
            parameters.put("disprosexia", psicologiaClinica.getDisprosexia() ? "Sí" : "No");
            parameters.put("distraibilidad", psicologiaClinica.getDistraibilidad() ? "Sí" : "No");
            parameters.put("sinAlteracion", psicologiaClinica.getSinAlteracion() ? "Sí" : "No");
            parameters.put("hipercepcionSensopercepcion", psicologiaClinica.getHipercepcionSensopercepcion() ? "Sí" : "No");
            parameters.put("ilusiones", psicologiaClinica.getIlusiones() ? "Sí" : "No");
            parameters.put("seudoalucionciones", psicologiaClinica.getSeudoalucionciones() ? "Sí" : "No");
            parameters.put("alusinosis", psicologiaClinica.getAlusinosis() ? "Sí" : "No");
            parameters.put("macropsias", psicologiaClinica.getMacropsias() ? "Sí" : "No");
            parameters.put("micropsias", psicologiaClinica.getMicropsias() ? "Sí" : "No");
            parameters.put("noPresenta", psicologiaClinica.getNoPresenta() ? "Sí" : "No");
            parameters.put("alucinaiones", psicologiaClinica.getAlucinaiones() ? "Sí" : "No");
            parameters.put("hipermnecia", psicologiaClinica.getHipermnecia() ? "Sí" : "No");
            parameters.put("amnesiaDeFijacion", psicologiaClinica.getAmnesiaDeFijacion() ? "Sí" : "No");
            parameters.put("amnesiaDeEvocacion", psicologiaClinica.getAmnesiaDeEvocacion() ? "Sí" : "No");
            parameters.put("mixta", psicologiaClinica.getMixta() ? "Sí" : "No");
            parameters.put("lacunar", psicologiaClinica.getLacunar() ? "Sí" : "No");
            parameters.put("dismensia", psicologiaClinica.getDismensia() ? "Sí" : "No");
            parameters.put("paramnesias", psicologiaClinica.getParamnesias() ? "Sí" : "No");
            parameters.put("sinAlteracionMemoria", psicologiaClinica.getSinAlteracionMemoria() ? "Sí" : "No");
            parameters.put("enlentecimiento", psicologiaClinica.getEnlentecimiento() ? "Sí" : "No");
            parameters.put("excitacionPsicomotriz", psicologiaClinica.getExcitacionPsicomotriz() ? "Sí" : "No");
            parameters.put("catatoniaConductaMotora", psicologiaClinica.getCatatoniaConductaMotora() ? "Sí" : "No");
            parameters.put("actitudesAnormales", psicologiaClinica.getActitudesAnormales() ? "Sí" : "No");
            parameters.put("alteracionesDeLaMarcha", psicologiaClinica.getAlteracionesDeLaMarcha() ? "Sí" : "No");
            parameters.put("inquietudConductaMotora", psicologiaClinica.getInquietudConductaMotora() ? "Sí" : "No");

            if (!imagenPacienteBase64.isEmpty()) {
                parameters.put("imagenPaciente", new ByteArrayInputStream(Base64.decodeBase64(imagenPacienteBase64)));
            } else {
                parameters.put("imagenPaciente", null);
            }

            // Crear el datasource con el registro de Psicología Clínica
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(Collections.singletonList(psicologiaClinica));

            // Utilizar el servicio de transformación para generar el reporte en formato A4
            return reportTransformService.generarYRedimensionarReporte("reportes/psicologia_clinica.jrxml", parameters, dataSource);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al generar el reporte: " + e.getMessage());
        }
    }
}
