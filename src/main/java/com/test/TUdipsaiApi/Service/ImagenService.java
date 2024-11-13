package com.test.TUdipsaiApi.Service;

import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Base64;
import java.util.zip.GZIPOutputStream;
import java.util.zip.GZIPInputStream;

@Service
public class ImagenService {

    private static final String IMAGE_DIRECTORY = System.getProperty("user.home") + "/UdipsaiImagenesPacientes/";
    private static final String ESPECIALISTAS_DIRECTORY = System.getProperty("user.home") + "/UdipsaiEspecialistasImagenes/";


    public String saveCompressedImage(String base64Image, String fileName) throws IOException {
        return saveCompressedImageInDirectory(base64Image, fileName, IMAGE_DIRECTORY);
    }

    public String saveCompressedImageForEspecialistas(String base64Image, String fileName) throws IOException {
        return saveCompressedImageInDirectory(base64Image, fileName, ESPECIALISTAS_DIRECTORY);
    }

    private String saveCompressedImageInDirectory(String base64Image, String fileName, String directoryPath) throws IOException {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            boolean created = directory.mkdirs(); // Crear el directorio si no existe
            if (!created) {
                throw new IOException("No se pudo crear el directorio: " + directoryPath);
            }
        }

        // Decodificar la imagen de base64 a bytes
        byte[] decodedBytes = Base64.getDecoder().decode(base64Image);

        // Guardar el archivo comprimido en el directorio especificado
        File compressedFile = new File(directoryPath + fileName + ".txt.gz");
        try (FileOutputStream fos = new FileOutputStream(compressedFile);
             GZIPOutputStream gzipOut = new GZIPOutputStream(fos)) {
            gzipOut.write(decodedBytes);
        }

        return compressedFile.getAbsolutePath(); // Retornar la ruta del archivo comprimido
    }

    // Descomprimir el archivo y devolver la imagen en formato Base64
    public String getDecompressedImage(String compressedFilePath) throws IOException {
        File compressedFile = new File(compressedFilePath);
        if (!compressedFile.exists()) {
            throw new FileNotFoundException("El archivo comprimido no existe: " + compressedFilePath);
        }

        try (FileInputStream fis = new FileInputStream(compressedFile);
             GZIPInputStream gzipIn = new GZIPInputStream(fis);
             ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[1024];
            int len;
            while ((len = gzipIn.read(buffer)) > 0) {
                byteArrayOutputStream.write(buffer, 0, len);
            }

            byte[] decompressedBytes = byteArrayOutputStream.toByteArray();
            return Base64.getEncoder().encodeToString(decompressedBytes);

        } catch (IOException e) {
            // Loguear el error (si tienes un logger)
            System.err.println("Error al descomprimir la imagen: " + e.getMessage());
            throw new IOException("Error al descomprimir la imagen", e);
        }
    }
}
