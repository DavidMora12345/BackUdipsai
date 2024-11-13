package com.test.TUdipsaiApi.Service;

import com.test.TUdipsaiApi.Model.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

@Service
public class ReporteGeneralService {

    @Autowired
    private FichaMedicaService fichaMedicaService;

    @Autowired
    private PsicologiaEducativaService psicologiaEducativaService;

    @Autowired
    private PsicologiaClinicaService psicologiaClinicaService;

    @Autowired FonoaudiologiaService fonoaudiologiaService;

    @Autowired
    private ReportTransformService reportTransformService;

    // Directorio donde se almacenan las imágenes
    private static final String IMAGE_DIRECTORY = System.getProperty("user.home") + "/UdipsaiImagenesPacientes/";


    public byte[] generarReporteGeneralPorPacienteId(Integer idPaciente) {
        try {
            // Obtener la Ficha Médica y Psicología Educativa por ID de Paciente
            FichaMedica fichaMedica = fichaMedicaService.obtenerFichaPorIdPaciente(idPaciente);
            if (fichaMedica == null) {
                throw new RuntimeException("Ficha Médica no encontrada para el paciente con ID: " + idPaciente);
            }

            PsicologiaEducativa psicologiaEducativa = psicologiaEducativaService.obtenerPorIdPaciente(idPaciente);
            if (psicologiaEducativa == null) {
                throw new RuntimeException("Psicología Educativa no encontrada para el paciente con ID: " + idPaciente);
            }

            PsicologiaClinica psicologiaClinica = psicologiaClinicaService.obtenerFichaPorIdPaciente(idPaciente);
            if(psicologiaClinica == null){
                throw new RuntimeException("Psicologia Clínica no encontrada para el paciente con ID:"+idPaciente);
            }

            Fonoaudiologia fonoaudiologia = fonoaudiologiaService.obtenerPorIdPaciente(idPaciente);
            if(fonoaudiologia == null){
                throw new RuntimeException("Fonoaudiología no encontrada para el paciente con ID:"+idPaciente);
            }

            Paciente paciente = fichaMedica.getPaciente(); // Asumimos que el paciente es el mismo para ambos registros

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


            // Obtener el genograma familiar en Base64 (si existe)
            byte[] genogramaFamiliar = fichaMedica.getGenogramaFamiliar();
            String genogramaFamiliarBase64 = (genogramaFamiliar != null) ? Base64.encodeBase64String(genogramaFamiliar) : "";


            // Establecer la URL de la imagen de fondo
            String backgroundImage = "https://upload.wikimedia.org/wikipedia/commons/6/6c/Logo_Universidad_Cat%C3%B3lica_de_Cuenca.jpg";

            // Configurar parámetros para ficha_medica
            Map<String, Object> parametrosFichaMedica = new HashMap<>();
            parametrosFichaMedica.put("backgroundImage", backgroundImage);
            parametrosFichaMedica.put("imagenPaciente", !imagenPacienteBase64.isEmpty() ? new ByteArrayInputStream(Base64.decodeBase64(imagenPacienteBase64)) : null);
            parametrosFichaMedica.put("genogramaFamiliar",!genogramaFamiliarBase64.isEmpty()? new ByteArrayInputStream(Base64.decodeBase64(genogramaFamiliarBase64)):null);

            parametrosFichaMedica.put("vivenJuntos", fichaMedica.isVivenJuntos());
            parametrosFichaMedica.put("embarazoDeseado", fichaMedica.isEmbarazoDeseado());
            parametrosFichaMedica.put("controlEmbarazo", fichaMedica.isControlEmbarazo());
            parametrosFichaMedica.put("presentoAmenazaAborto", fichaMedica.isPresentoAmenazaAborto());
            parametrosFichaMedica.put("lloroAlNacer", fichaMedica.isLloroAlNacer());
            parametrosFichaMedica.put("presentoAnoxiaAlNacer", fichaMedica.isPresentoAnoxiaAlNacer());
            parametrosFichaMedica.put("presentoHipoxiaAlNacer", fichaMedica.isPresentoHipoxiaAlNacer());
            parametrosFichaMedica.put("presentoIctericiaAlNacer", fichaMedica.isPresentoIctericiaAlNacer());
            parametrosFichaMedica.put("presentoCianosisAlNacer", fichaMedica.isPresentoCianosisAlNacer());
            parametrosFichaMedica.put("complicacionesEnElParto", fichaMedica.isComplicacionesEnElParto());
            parametrosFichaMedica.put("estuvoEnIncubadora", fichaMedica.isEstuvoEnIncubadora());
            parametrosFichaMedica.put("esquemaVacunacionCompleto", fichaMedica.isEsquemaVacunacionCompleto());

            // Agregar otros parámetros específicos a Ficha Medica según sea necesario

            // Configurar parámetros para psicologia_educativa
            Map<String, Object> parametrosPsicologiaEducativa = new HashMap<>();
            parametrosPsicologiaEducativa.put("backgroundImage", backgroundImage);
            parametrosPsicologiaEducativa.put("imagenPaciente", !imagenPacienteBase64.isEmpty() ? new ByteArrayInputStream(Base64.decodeBase64(imagenPacienteBase64)) : null);

            Map<String, Object> parametrosPsicologiaClinica = new HashMap<>();
            parametrosPsicologiaClinica.put("backgroundImage", backgroundImage);
            parametrosPsicologiaClinica.put("imagenPaciente", !imagenPacienteBase64.isEmpty() ? new ByteArrayInputStream(Base64.decodeBase64(imagenPacienteBase64)) : null);

            Map<String, Object> parametrosFonoaudiologia = new HashMap<>();
            parametrosFonoaudiologia.put("backgroundImage", backgroundImage);
            parametrosFonoaudiologia.put("imagenPaciente", !imagenPacienteBase64.isEmpty() ? new ByteArrayInputStream(Base64.decodeBase64(imagenPacienteBase64)) : null);

            // Cargar las plantillas de los reportes
            ClassPathResource resourceFichaMedica = new ClassPathResource("reportes/ficha_medica.jrxml");
            JasperReport reporteFichaMedica = JasperCompileManager.compileReport(resourceFichaMedica.getInputStream());

            ClassPathResource resourcePsicologiaEducativa = new ClassPathResource("reportes/psicologia_educativa.jrxml");
            JasperReport reportePsicologiaEducativa = JasperCompileManager.compileReport(resourcePsicologiaEducativa.getInputStream());

            ClassPathResource resourcePsicologiaClinica = new ClassPathResource("reportes/psicologia_clinica.jrxml");
            JasperReport reportePsicologiaClinica = JasperCompileManager.compileReport(resourcePsicologiaClinica.getInputStream());

            ClassPathResource resourceFonoaudiologia = new ClassPathResource("reportes/fonoaudiologia.jrxml");
            JasperReport reporteFonoaudiologia = JasperCompileManager.compileReport(resourceFonoaudiologia.getInputStream());

            // Crear las fuentes de datos
            JRBeanCollectionDataSource dataSourceFichaMedica = new JRBeanCollectionDataSource(Collections.singletonList(fichaMedica));
            JRBeanCollectionDataSource dataSourcePsicologiaEducativa = new JRBeanCollectionDataSource(Collections.singletonList(psicologiaEducativa));
            JRBeanCollectionDataSource dataSourcePsicologiaClinica = new JRBeanCollectionDataSource(Collections.singletonList(psicologiaClinica));
            JRBeanCollectionDataSource dataSourceFonoaudiologia = new JRBeanCollectionDataSource(Collections.singletonList(fonoaudiologia));



            // Rellenar los reportes con sus respectivos parámetros y fuentes de datos
            JasperPrint printFichaMedica = JasperFillManager.fillReport(reporteFichaMedica, parametrosFichaMedica, dataSourceFichaMedica);
            JasperPrint printPsicologiaEducativa = JasperFillManager.fillReport(reportePsicologiaEducativa, parametrosPsicologiaEducativa, dataSourcePsicologiaEducativa);
            JasperPrint printPsicologiaClinica = JasperFillManager.fillReport(reportePsicologiaClinica, parametrosPsicologiaClinica, dataSourcePsicologiaClinica);
            JasperPrint printFonoaudiologia = JasperFillManager.fillReport(reporteFonoaudiologia, parametrosFonoaudiologia, dataSourceFonoaudiologia);


            // Unir los reportes en uno solo
            List<JasperPrint> reportesAUnir = new ArrayList<>();
            reportesAUnir.add(printFichaMedica);
            reportesAUnir.add(printPsicologiaEducativa);
            reportesAUnir.add(printPsicologiaClinica);
            reportesAUnir.add(printFonoaudiologia);



            JasperPrint reporteGeneral = reportesAUnir.get(0);
            for (int i = 1; i < reportesAUnir.size(); i++) {
                reporteGeneral.getPages().addAll(reportesAUnir.get(i).getPages());
            }

            // Exportar el reporte unificado a PDF
            return JasperExportManager.exportReportToPdf(reporteGeneral);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al generar el reporte general: " + e.getMessage());
        }
    }
}
