package com.test.TUdipsaiApi.Service;

import com.test.TUdipsaiApi.Model.FichaMedica;
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

// En el servicio
@Service
public class FichaMedicaReportService {

    @Autowired
    private FichaMedicaService fichaMedicaService;

    @Autowired
    private ReportTransformService reportTransformService;

    // Ruta donde se almacenan las imágenes
    private static final String IMAGE_DIRECTORY = System.getProperty("user.home") + "/UdipsaiImagenesPacientes/";

    public byte[] generarReporteFichaMedica(int idFichaMedica) {
        try {
            // Obtener la ficha médica por ID
            FichaMedica fichaMedica = fichaMedicaService.obtenerFichaPorId(idFichaMedica)
                    .orElseThrow(() -> new RuntimeException("Ficha Médica no encontrada"));

            // Obtener el paciente asociado
            Paciente paciente = fichaMedica.getPaciente();
            if (paciente == null) {
                throw new RuntimeException("Paciente no encontrado para la Ficha Médica");
            }

            // Leer la imagen del paciente desde el directorio comprimido si existe
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

            // Cargar la plantilla JRXML
            ClassPathResource resource = new ClassPathResource("reportes/ficha_medica.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(resource.getInputStream());

            // Crear un datasource con la ficha médica

            // Parámetros adicionales para el reporte
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("titulo", "Reporte de Ficha Médica");
            parameters.put("backgroundImage", backgroundImage);

            // Utilizar los métodos personalizados para los valores booleanos
            parameters.put("vivenJuntos", fichaMedica.isVivenJuntos());
            parameters.put("embarazoDeseado", fichaMedica.isEmbarazoDeseado());
            parameters.put("controlEmbarazo", fichaMedica.isControlEmbarazo());
            parameters.put("presentoAmenazaAborto", fichaMedica.isPresentoAmenazaAborto());
            parameters.put("lloroAlNacer", fichaMedica.isLloroAlNacer());
            parameters.put("presentoAnoxiaAlNacer", fichaMedica.isPresentoAnoxiaAlNacer());
            parameters.put("presentoHipoxiaAlNacer", fichaMedica.isPresentoHipoxiaAlNacer());
            parameters.put("presentoIctericiaAlNacer", fichaMedica.isPresentoIctericiaAlNacer());
            parameters.put("presentoCianosisAlNacer", fichaMedica.isPresentoCianosisAlNacer());
            parameters.put("complicacionesEnElParto", fichaMedica.isComplicacionesEnElParto());
            parameters.put("estuvoEnIncubadora", fichaMedica.isEstuvoEnIncubadora());
            parameters.put("esquemaVacunacionCompleto", fichaMedica.isEsquemaVacunacionCompleto());
            parameters.put("caeOPerdeEquilibrioFacilmente", fichaMedica.isCaeOPerdeEquilibrioFacilmente());

            if (!imagenPacienteBase64.isEmpty()) {
                parameters.put("imagenPaciente", new ByteArrayInputStream(Base64.decodeBase64(imagenPacienteBase64)));
            } else {
                parameters.put("imagenPaciente", null);
            }

            if (!genogramaFamiliarBase64.isEmpty()) {
                parameters.put("genogramaFamiliar", new ByteArrayInputStream(Base64.decodeBase64(genogramaFamiliarBase64)));
            } else {
                parameters.put("genogramaFamiliar", null);
            }

            // Crear el datasource con la ficha médica
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(Collections.singletonList(fichaMedica));

            // Utilizar el servicio de transformación para generar el reporte en formato A4
            return reportTransformService.generarYRedimensionarReporte("reportes/ficha_medica.jrxml", parameters, dataSource);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al generar el reporte: " + e.getMessage());
        }
    }
}