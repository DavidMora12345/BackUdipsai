package com.test.TUdipsaiApi.Service;

import com.test.TUdipsaiApi.Model.Fonoaudiologia;
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
public class FonoaudiologiaReportService {

    @Autowired
    private FonoaudiologiaService fonoaudiologiaService;

    @Autowired
    private ReportTransformService reportTransformService;

    private static final String IMAGE_DIRECTORY = System.getProperty("user.home") + "/UdipsaiImagenesPacientes/";


    public byte[] generarReporteFonoaudiologia(int idFonoaudiologia) {
        try {
            // Obtener el registro de Fonoaudiología por ID
            Fonoaudiologia fonoaudiologia = fonoaudiologiaService.obtenerPorId(idFonoaudiologia)
                    .orElseThrow(() -> new RuntimeException("Registro de Fonoaudiología no encontrado"));

            // Obtener el paciente asociado
            Paciente paciente = fonoaudiologia.getPaciente();
            if (paciente == null) {
                throw new RuntimeException("Paciente no encontrado para el registro de Fonoaudiología");
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
            ClassPathResource resource = new ClassPathResource("reportes/fonoaudiologia.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(resource.getInputStream());

            // Crear un datasource con el registro de Fonoaudiología

            // Parámetros adicionales para el reporte
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("titulo", "Reporte de Fonoaudiología");
            parameters.put("backgroundImage", backgroundImage);

            // Características del habla
            parameters.put("dificultadPronunciarPalabras", fonoaudiologia.getDificultadPronunciarPalabras() ? "Sí" : "No");
            parameters.put("seTrabaCuandoHabla", fonoaudiologia.getSeTrabaCuandoHabla() ? "Sí" : "No");
            parameters.put("seEntiendeLoQueDice", fonoaudiologia.getSeEntiendeLoQueDice() ? "Sí" : "No");
            parameters.put("sabeComoLlamanObjetosEntorno", fonoaudiologia.getSabeComoLlamanObjetosEntorno() ? "Sí" : "No");
            parameters.put("comprendeLoQueSeLeDice", fonoaudiologia.getComprendeLoQueSeLeDice() ? "Sí" : "No");
            parameters.put("reconoceFuenteSonora", fonoaudiologia.getReconoceFuenteSonora() ? "Sí" : "No");
            parameters.put("seARealizadoExamenAudiologico", fonoaudiologia.getSeARealizadoExamenAudiologico() ? "Sí" : "No");

            // Diagnóstico audiológico
            parameters.put("perdidaAuditivaConductivaNeurosensorial", fonoaudiologia.getPerdidaAuditivaConductivaNeurosensorial() ? "Sí" : "No");
            parameters.put("hipoacusiaConductivaBilateral", fonoaudiologia.getHipoacusiaConductivaBilateral() ? "Sí" : "No");
            parameters.put("hipoacusiaConductivaUnilateral", fonoaudiologia.getHipoacusiaConductivaUnilateral() ? "Sí" : "No");
            parameters.put("hipoacusiaNeurosensorialBilateral", fonoaudiologia.getHipoacusiaNeurosensorialBilateral() ? "Sí" : "No");
            parameters.put("hipoacusiaNeurosensorialUnilateral", fonoaudiologia.getHipoacusiaNeurosensorialUnilateral() ? "Sí" : "No");

            // Diagnóstico fonoaudiológico previo
            parameters.put("trastornoEspecificoPronunciacion", fonoaudiologia.getTrastornoEspecificoPronunciacion() ? "Sí" : "No");
            parameters.put("trastornoLenguajeExpresivo", fonoaudiologia.getTrastornoLenguajeExpresivo() ? "Sí" : "No");
            parameters.put("afasiaAdquiridaEpilepsia", fonoaudiologia.getAfasiaAdquiridaEpilepsia() ? "Sí" : "No");
            parameters.put("otrosTrastornosDesarrolloHabla", fonoaudiologia.getOtrosTrastornosDesarrolloHabla() ? "Sí" : "No");
            parameters.put("trastornoDesarrolloHablaLenguaje", fonoaudiologia.getTrastornoDesarrolloHablaLenguaje() ? "Sí" : "No");
            parameters.put("trastornoRecepcionLenguaje", fonoaudiologia.getTrastornoRecepcionLenguaje() ? "Sí" : "No");
            parameters.put("alteracionesHabla", fonoaudiologia.getAlteracionesHabla() ? "Sí" : "No");
            parameters.put("disfasiaAfasia", fonoaudiologia.getDisfasiaAfasia() ? "Sí" : "No");
            parameters.put("disartriaAnartria", fonoaudiologia.getDisartriaAnartria() ? "Sí" : "No");
            parameters.put("otrasAlteracionesHabla", fonoaudiologia.getOtrasAlteracionesHabla() ? "Sí" : "No");
            parameters.put("aTenidoPerdidaAudicionPasado", fonoaudiologia.getaTenidoPerdidaAudicionPasado() != null && fonoaudiologia.getaTenidoPerdidaAudicionPasado() ? "Sí" : "No");
            parameters.put("infeccionesOidoFuertes", fonoaudiologia.getInfeccionesOidoFuertes() ? "Sí" : "No");

            // Fonación
            parameters.put("creeTonoVozEstudianteApropiado", fonoaudiologia.getCreeTonoVozEstudianteApropiado() ? "Sí" : "No");
            parameters.put("respiracionNormal", fonoaudiologia.getRespiracionNormal() ? "Sí" : "No");
            parameters.put("ronca", fonoaudiologia.getRonca() ? "Sí" : "No");
            parameters.put("juegoVocal", fonoaudiologia.getJuegoVocal() ? "Sí" : "No");
            parameters.put("vocalizacion", fonoaudiologia.getVocalizacion() ? "Sí" : "No");
            parameters.put("balbuceo", fonoaudiologia.getBalbuceo() ? "Sí" : "No");
            parameters.put("silabeo", fonoaudiologia.getSilabeo() ? "Sí" : "No");
            parameters.put("primerasPalabras", fonoaudiologia.getPrimerasPalabras() ? "Sí" : "No");
            parameters.put("oracionesDosPalabras", fonoaudiologia.getOracionesDosPalabras() ? "Sí" : "No");
            parameters.put("oracionesTresPalabras", fonoaudiologia.getOracionesTresPalabras() ? "Sí" : "No");
            parameters.put("formacionLinguisticaCompleta", fonoaudiologia.getFormacionLinguisticaCompleta() ? "Sí" : "No");

            // Historia clínica Audición
            parameters.put("perdidaAuditiva", fonoaudiologia.getPerdidaAuditiva() ? "Sí" : "No");
            parameters.put("unilateral", fonoaudiologia.getUnilateral() ? "Sí" : "No");
            parameters.put("oidoDerecho", fonoaudiologia.getOidoDerecho() ? "Sí" : "No");
            parameters.put("oidoIzquierdo", fonoaudiologia.getOidoIzquierdo() ? "Sí" : "No");
            parameters.put("bilateral", fonoaudiologia.getBilateral() ? "Sí" : "No");
            parameters.put("otitis", fonoaudiologia.getOtitis() ? "Sí" : "No");
            parameters.put("antecedentesFamiliares", fonoaudiologia.getAntecedentesFamiliares() ? "Sí" : "No");
            parameters.put("exposisionRuidos", fonoaudiologia.getExposisionRuidos() ? "Sí" : "No");
            parameters.put("ototoxicos", fonoaudiologia.getOtotoxicos() ? "Sí" : "No");
            parameters.put("infecciones", fonoaudiologia.getInfecciones() ? "Sí" : "No");
            parameters.put("usoAudifonos", fonoaudiologia.getUsoAudifonos() ? "Sí" : "No");
            parameters.put("implanteCoclear", fonoaudiologia.getImplanteCoclear() ? "Sí" : "No");
            parameters.put("tratamientoFonoaudiologicoPrevio", fonoaudiologia.getTratamientoFonoaudiologicoPrevio() ? "Sí" : "No");

            // Otalgia
            parameters.put("otalgia", fonoaudiologia.getOtalgia() ? "Sí" : "No");
            parameters.put("otalgiaUnilateral", fonoaudiologia.getOtalgiaUnilateral() ? "Sí" : "No");
            parameters.put("otalgiaOidoDerecho", fonoaudiologia.getOtalgiaOidoDerecho() ? "Sí" : "No");
            parameters.put("otalgiaOidoIzquierdo", fonoaudiologia.getOtalgiaOidoIzquierdo() ? "Sí" : "No");
            parameters.put("otalgiaBilateral", fonoaudiologia.getOtalgiaBilateral() ? "Sí" : "No");
            parameters.put("permanenciaOtalgiaContinua", fonoaudiologia.getPermanenciaOtalgiaContinua() ? "Sí" : "No");
            parameters.put("permanenciaOtalgiaIntermitente", fonoaudiologia.getPermanenciaOtalgiaIntermitente() ? "Sí" : "No");
            parameters.put("asociadaOtalgiaInfeccionRespiratoriaAlta", fonoaudiologia.getAsociadaOtalgiaInfeccionRespiratoriaAlta() ? "Sí" : "No");

            // Otorrinolaringología
            parameters.put("infeccionRespiratoriaPunzante", fonoaudiologia.getInfeccionRespiratoriaPunzante() ? "Sí" : "No");
            parameters.put("infeccionRespiratoriaPulsatil", fonoaudiologia.getInfeccionRespiratoriaPulsatil() ? "Sí" : "No");
            parameters.put("infeccionRespiratoriaProgresivo", fonoaudiologia.getInfeccionRespiratoriaProgresivo() ? "Sí" : "No");
            parameters.put("infeccionRespiratoriaOpresivo", fonoaudiologia.getInfeccionRespiratoriaOpresivo() ? "Sí" : "No");
            parameters.put("pruriginoso", fonoaudiologia.getPruriginoso() ? "Sí" : "No");
            parameters.put("aumentaMasticar", fonoaudiologia.getAumentaMasticar() ? "Sí" : "No");
            parameters.put("disminuyeConCalorLocal", fonoaudiologia.getDisminuyeConCalorLocal() ? "Sí" : "No");
            parameters.put("aumentaConCalorLocal", fonoaudiologia.getAumentaConCalorLocal() ? "Sí" : "No");

            // Otoscopia
            parameters.put("otorrea", fonoaudiologia.getOtorrea() ? "Sí" : "No");
            parameters.put("otorreaUnilateral", fonoaudiologia.getOtorreaUnilateral() ? "Sí" : "No");
            parameters.put("otorreaOidoDerecho", fonoaudiologia.getOtorreaOidoDerecho() ? "Sí" : "No");
            parameters.put("otorreaOidoIzquierdo", fonoaudiologia.getOtorreaOidoIzquierdo() ? "Sí" : "No");
            parameters.put("otorreaBilateral", fonoaudiologia.getOtorreaBilateral() ? "Sí" : "No");
            parameters.put("permanenciaOtorreaContinua", fonoaudiologia.getPermanenciaOtorreaContinua() ? "Sí" : "No");
            parameters.put("permanenciaOtorreaIntermitente", fonoaudiologia.getPermanenciaOtorreaIntermitente() ? "Sí" : "No");
            parameters.put("aspectoClaroOtorrea", fonoaudiologia.getAspectoClaroOtorrea() ? "Sí" : "No");
            parameters.put("aspectoSerosoOtorrea", fonoaudiologia.getAspectoSerosoOtorrea() ? "Sí" : "No");
            parameters.put("aspectoMucosoOtorrea", fonoaudiologia.getAspectoMucosoOtorrea() ? "Sí" : "No");
            parameters.put("aspectoMucopurulentoOtorrea", fonoaudiologia.getAspectoMucopurulentoOtorrea() ? "Sí" : "No");
            parameters.put("aspectoPurulentoOtorrea", fonoaudiologia.getAspectoPurulentoOtorrea() ? "Sí" : "No");
            parameters.put("aspectoSanguinolentoOtorrea", fonoaudiologia.getAspectoSanguinolentoOtorrea() ? "Sí" : "No");
            parameters.put("asosiadaOtorreaInfeccionRespiratoriaAlta", fonoaudiologia.getAsosiadaOtorreaInfeccionRespiratoriaAlta() ? "Sí" : "No");
            parameters.put("asosiadaotorreaInfeccionAgudaOido", fonoaudiologia.getAsosiadaotorreaInfeccionAgudaOido() ? "Sí" : "No");

            // Antecedentes auditivos
            parameters.put("presentoOtalgia", fonoaudiologia.getPresentoOtalgia() ? "Sí" : "No");
            parameters.put("presentoOtalgiaBilateral", fonoaudiologia.getPresentoOtalgiaBilateral() ? "Sí" : "No");
            parameters.put("presentoOtalgiaOidoDerecho", fonoaudiologia.getPresentoOtalgiaOidoDerecho() ? "Sí" : "No");
            parameters.put("presentoOtalgiaOidoIzquierdo", fonoaudiologia.getPresentoOtalgiaOidoIzquierdo() ? "Sí" : "No");
            parameters.put("presentoSensacionOidoTapado", fonoaudiologia.getPresentoSensacionOidoTapado() ? "Sí" : "No");
            parameters.put("presentoSensacionOidoTapadoBilateral", fonoaudiologia.getPresentoSensacionOidoTapadoBilateral() ? "Sí" : "No");
            parameters.put("presentoSensacionOidoTapadoOidoDerecho", fonoaudiologia.getPresentoSensacionOidoTapadoOidoDerecho() ? "Sí" : "No");
            parameters.put("presentoSensacionOidoTapadoOidoIzquierdo", fonoaudiologia.getPresentoSensacionOidoTapadoOidoIzquierdo() ? "Sí" : "No");
            parameters.put("presentoAutofonia", fonoaudiologia.getPresentoAutofonia() ? "Sí" : "No");
            parameters.put("presentoAutofoniaBilateral", fonoaudiologia.getPresentoAutofoniaBilateral() ? "Sí" : "No");
            parameters.put("presentoAutofoniaOidoDerecho", fonoaudiologia.getPresentoAutofoniaOidoDerecho() ? "Sí" : "No");
            parameters.put("presentoAutofoniaOidoIzquierdo", fonoaudiologia.getPresentoAutofoniaOidoIzquierdo() ? "Sí" : "No");
            parameters.put("presentoOtorrea", fonoaudiologia.getPresentoOtorrea() ? "Sí" : "No");
            parameters.put("presentoOtorreaBilateral", fonoaudiologia.getPresentoOtorreaBilateral() ? "Sí" : "No");
            parameters.put("presentoOtorreaOidoDerecho", fonoaudiologia.getPresentoOtorreaOidoDerecho() ? "Sí" : "No");
            parameters.put("presentoOtorreaOidoIzquierdo", fonoaudiologia.getPresentoOtorreaOidoIzquierdo() ? "Sí" : "No");
            parameters.put("aumentaVolumenTV", fonoaudiologia.getAumentaVolumenTV() ? "Sí" : "No");
            parameters.put("sensacionPercibirTinnitus", fonoaudiologia.getSensacionPercibirTinnitus() ? "Sí" : "No");
            parameters.put("expuestoRuidosFuertes", fonoaudiologia.getExpuestoRuidosFuertes() ? "Sí" : "No");
            parameters.put("dificultadOidVozBaja", fonoaudiologia.getDificultadOidVozBaja() ? "Sí" : "No");
            parameters.put("hablaMasFuerteOMasDespacio", fonoaudiologia.getHablaMasFuerteOMasDespacio() ? "Sí" : "No");
            parameters.put("utilizaAyudaAuditiva", fonoaudiologia.getUtilizaAyudaAuditiva() ? "Sí" : "No");
            parameters.put("especficarAyudaAuditiva", fonoaudiologia.getEspecficarAyudaAuditiva());
            parameters.put("percibeSonidoIgualAmbosOidos", fonoaudiologia.getPercibeSonidoIgualAmbosOidos() ? "Sí" : "No");

            // Antecedentes vestibulares
            parameters.put("faltaEquilibrioCaminar", fonoaudiologia.getFaltaEquilibrioCaminar() ? "Sí" : "No");
            parameters.put("mareos", fonoaudiologia.getMareos() ? "Sí" : "No");
            parameters.put("vertigo", fonoaudiologia.getVertigo() ? "Sí" : "No");

            // Otoscopia: oído derecho
            parameters.put("perforacionOidoDerecho", fonoaudiologia.getPerforacionOidoDerecho() ? "Sí" : "No");
            parameters.put("burbujaOidoDerecho", fonoaudiologia.getBurbujaOidoDerecho() ? "Sí" : "No");

            // Otoscopia: oído izquierdo
            parameters.put("perforacionOidoIzquierdo", fonoaudiologia.getPerforacionOidoIzquierdo() ? "Sí" : "No");
            parameters.put("burbujaOidoIzquierdo", fonoaudiologia.getBurbujaOidoIzquierdo() ? "Sí" : "No");

            if (!imagenPacienteBase64.isEmpty()) {
                parameters.put("imagenPaciente", new ByteArrayInputStream(Base64.decodeBase64(imagenPacienteBase64)));
            } else {
                parameters.put("imagenPaciente", null);
            }

            // Crear el datasource con el registro de Fonoaudiología
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(Collections.singletonList(fonoaudiologia));

            // Utilizar el servicio de transformación para generar el reporte en formato A4
            return reportTransformService.generarYRedimensionarReporte("reportes/fonoaudiologia.jrxml", parameters, dataSource);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al generar el reporte: " + e.getMessage());
        }
    }
}
