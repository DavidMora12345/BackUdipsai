package com.test.TUdipsaiApi.Service;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.type.PdfVersionEnum;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.util.Matrix;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

@Service
public class ReportTransformService {

    public byte[] generarYRedimensionarReporte(String reportPath, Map<String, Object> parameters, JRBeanCollectionDataSource dataSource) {
        try {
            // Generar un nombre temporal usando UUID
            String uuid = UUID.randomUUID().toString();
            Path tempDir = Files.createTempDirectory("temp-reports");
            File tempPdf = new File(tempDir.toFile(), uuid + ".pdf");

            // 1. Generar PDF con JasperReports
            // Usar ClassPathResource para obtener el archivo del classpath
            InputStream reportStream = new ClassPathResource(reportPath).getInputStream();
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            // Exportar el reporte a PDF temporalmente
            JRPdfExporter exporter = new JRPdfExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(tempPdf));

            SimplePdfExporterConfiguration config = new SimplePdfExporterConfiguration();
            config.setPdfVersion(PdfVersionEnum.VERSION_1_7);
            exporter.setConfiguration(config);
            exporter.exportReport();

            // 2. Redimensionar PDF con PDFBox
            File resizedPdf = redimensionarPdf(tempPdf);

            // 3. Eliminar el archivo PDF original después de haberlo redimensionado
            Files.delete(tempPdf.toPath());

            // 4. Leer el archivo redimensionado como byte[]
            byte[] pdfBytes = Files.readAllBytes(resizedPdf.toPath());

            // Eliminar el archivo redimensionado
            Files.delete(resizedPdf.toPath());

            return pdfBytes;
        } catch (Exception e) {
            throw new RuntimeException("Error al generar y redimensionar el reporte", e);
        }
    }

    private File redimensionarPdf(File pdfFile) throws IOException {
        // Crear un archivo temporal para el PDF redimensionado
        File resizedPdf = new File(pdfFile.getParent(), "resized_" + pdfFile.getName());

        try (PDDocument document = PDDocument.load(pdfFile)) {
            // Iterar sobre cada página del documento
            for (PDPage page : document.getPages()) {
                PDRectangle originalMediaBox = page.getMediaBox();
                PDRectangle newMediaBox = PDRectangle.A4;

                // Calcular el escalado proporcional y aumentar ligeramente el contenido
                float scaleX = newMediaBox.getWidth() / originalMediaBox.getWidth();
                float scaleY = newMediaBox.getHeight() / originalMediaBox.getHeight();

                // Incrementar el contenido un 10% manteniendo la proporción
                float scale = Math.min(scaleX, scaleY) * 1f;  // Ajuste pequeño de 10%

                // Ajustar márgenes mínimos
                float marginX = (newMediaBox.getWidth() - originalMediaBox.getWidth() * scale) / 2;
                float marginY = (newMediaBox.getHeight() - originalMediaBox.getHeight() * scale) / 2;

                // Establecer el nuevo tamaño de página
                page.setMediaBox(newMediaBox);

                // Escalar y centrar el contenido
                try (PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.PREPEND, true, true)) {
                    Matrix scalingMatrix = new Matrix(scale, 0, 0, scale, marginX, marginY);
                    contentStream.transform(scalingMatrix);
                }
            }

            // Guardar el PDF redimensionado
            document.save(resizedPdf);
        }

        return resizedPdf;
    }
}
