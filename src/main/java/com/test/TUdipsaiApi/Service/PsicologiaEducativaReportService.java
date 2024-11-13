package com.test.TUdipsaiApi.Service;

import com.test.TUdipsaiApi.Model.PsicologiaEducativa;
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
public class PsicologiaEducativaReportService {

    @Autowired
    private PsicologiaEducativaService psicologiaEducativaService;

    @Autowired
    private ReportTransformService reportTransformService;

    private static final String IMAGE_DIRECTORY = System.getProperty("user.home") + "/UdipsaiImagenesPacientes/";

    public byte[] generarReportePsicologiaEducativa(int idPsicologiaEducativa) {
        try {
            // Obtener el registro de Psicología Educativa por ID
            PsicologiaEducativa psicologiaEducativa = psicologiaEducativaService.obtenerPorId(idPsicologiaEducativa)
                    .orElseThrow(() -> new RuntimeException("Registro de Psicología Educativa no encontrado"));

            // Obtener el paciente asociado
            Paciente paciente = psicologiaEducativa.getPaciente();
            if (paciente == null) {
                throw new RuntimeException("Paciente no encontrado para el registro de Psicología Educativa");
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

            // Parámetros adicionales para el reporte
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("titulo", "Reporte de Psicología Educativa");
            parameters.put("backgroundImage", backgroundImage);

            // Utilizar los métodos personalizados para los valores booleanos
            parameters.put("gustaIrInstitucion", psicologiaEducativa.getGustaIrInstitucion() ? "Sí" : "No");
            parameters.put("inclusionEducativa", psicologiaEducativa.getInclusionEducativa() ? "Sí" : "No");
            parameters.put("adaptacionesCurriculares", psicologiaEducativa.getAdaptacionesCurriculares() ? "Sí" : "No");
            parameters.put("cdi", psicologiaEducativa.getCdi() ? "Sí" : "No");
            parameters.put("inicial1", psicologiaEducativa.getInicial1() ? "Sí" : "No");
            parameters.put("inicial2", psicologiaEducativa.getInicial2() ? "Sí" : "No");
            parameters.put("primerEGB", psicologiaEducativa.getPrimerEGB() ? "Sí" : "No");
            parameters.put("perdidaAnio", psicologiaEducativa.getPerdidaAnio() ? "Sí" : "No");
            parameters.put("desercionEscolar", psicologiaEducativa.getDesercionEscolar() ? "Sí" : "No");
            parameters.put("cambioInstitucion", psicologiaEducativa.getCambioInstitucion() ? "Sí" : "No");
            parameters.put("problemasAprendizaje", psicologiaEducativa.getProblemasAprendizaje() ? "Sí" : "No");
            parameters.put("evaluacionPsicologicaUOtrosAnterior", psicologiaEducativa.getEvaluacionPsicologicaUOtrosAnterior() ? "Sí" : "No");
            parameters.put("recibeApoyo", psicologiaEducativa.getRecibeApoyo() ? "Sí" : "No");

            // Agregar imagen del paciente si está disponible
            if (!imagenPacienteBase64.isEmpty()) {
                parameters.put("imagenPaciente", new ByteArrayInputStream(Base64.decodeBase64(imagenPacienteBase64)));
            } else {
                parameters.put("imagenPaciente", null);
            }

            // En este punto se crea y se utiliza el `dataSource`
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(Collections.singletonList(psicologiaEducativa));

            // Utilizar el servicio de transformación para generar el reporte en formato A4
            return reportTransformService.generarYRedimensionarReporte("reportes/psicologia_educativa.jrxml", parameters, dataSource);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al generar el reporte: " + e.getMessage());
        }
    }
}

