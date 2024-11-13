package com.test.TUdipsaiApi.Service;

import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

@Service
public class ImagenServiceEspecialistas {

    // Directorio para guardar las imágenes de los especialistas
    private static final String ROOT_DIRECTORY = System.getProperty("user.home") + "/UdipsaiEspecialistasImagenes/";

    // Guardar la imagen comprimida como archivo .txt.gz
    public String saveCompressedImage(String base64Image, String fileName) throws IOException {
        // Asegurarse de que el directorio de especialistas exista
        File directory = new File(ROOT_DIRECTORY);
        if (!directory.exists()) {
            boolean created = directory.mkdirs(); // Crear el directorio si no existe
            if (!created) {
                throw new IOException("No se pudo crear el directorio: " + ROOT_DIRECTORY);
            }
        }

        byte[] decodedBytes = Base64.getDecoder().decode(base64Image);

        // Guardar el archivo comprimido en el directorio
        File compressedFile = new File(ROOT_DIRECTORY + fileName + ".txt.gz");
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

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (FileInputStream fis = new FileInputStream(compressedFile);
             GZIPInputStream gzipIn = new GZIPInputStream(fis)) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = gzipIn.read(buffer)) > 0) {
                byteArrayOutputStream.write(buffer, 0, len);
            }
        }

        byte[] decompressedBytes = byteArrayOutputStream.toByteArray();
        return Base64.getEncoder().encodeToString(decompressedBytes);
    }
}
