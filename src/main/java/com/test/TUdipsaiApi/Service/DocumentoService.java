package com.test.TUdipsaiApi.Service;

import com.test.TUdipsaiApi.Model.Documento;
import com.test.TUdipsaiApi.Repository.DocumentoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

@Service
public class DocumentoService {

    @Autowired
    private DocumentoRepositorio documentoRepositorio;

    private final String DOCUMENTOS_DIR = System.getProperty("user.home") + "/DocumentosUdipsai/";


    // Método para obtener un documento por su ID
    public Optional<Documento> getDocumentoById(Long id) {
        return documentoRepositorio.findById(id);
    }


    // Método para comprimir un archivo con la máxima compresión
    public byte[] comprimirArchivo(byte[] data) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(byteArrayOutputStream)) {
            zipOutputStream.setLevel(ZipOutputStream.DEFLATED); // Nivel de compresión predeterminado
            zipOutputStream.setLevel(9); // Nivel de compresión máximo (0 = sin compresión, 9 = máxima compresión)

            ZipEntry zipEntry = new ZipEntry("documento");
            zipOutputStream.putNextEntry(zipEntry);
            zipOutputStream.write(data);
            zipOutputStream.closeEntry();
        }
        return byteArrayOutputStream.toByteArray();
    }


    // Método para descomprimir un archivo
    private byte[] descomprimirArchivo(byte[] compressedData) throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(compressedData);
        try (ZipInputStream zipInputStream = new ZipInputStream(byteArrayInputStream)) {
            zipInputStream.getNextEntry();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = zipInputStream.read(buffer)) > 0) {
                byteArrayOutputStream.write(buffer, 0, len);
            }
            return byteArrayOutputStream.toByteArray();
        }
    }

    // Método para guardar un documento comprimido
    public Documento saveDocumento(MultipartFile file) throws IOException {
        // Crear directorio si no existe
        File directorio = new File(DOCUMENTOS_DIR);
        if (!directorio.exists()) {
            directorio.mkdirs();
        }

        // Comprimir el archivo
        byte[] fileData = file.getBytes();
        byte[] compressedData = comprimirArchivo(fileData);

        // Guardar archivo comprimido en disco duro
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename() + ".zip";
        Path filePath = Paths.get(DOCUMENTOS_DIR, fileName);
        Files.write(filePath, compressedData);

        // Guardar URL en la base de datos
        Documento documento = new Documento();
        documento.setUrl(filePath.toString());
        return documentoRepositorio.save(documento);
    }

    // Método para obtener el contenido descomprimido
    public byte[] getContenido(Long id) throws IOException {
        Optional<Documento> optionalDocumento = documentoRepositorio.findById(id);
        if (optionalDocumento.isPresent()) {
            Documento documento = optionalDocumento.get();
            Path filePath = Paths.get(documento.getUrl());
            byte[] compressedData = Files.readAllBytes(filePath);
            return descomprimirArchivo(compressedData);
        } else {
            throw new RuntimeException("Documento no encontrado con ID: " + id);
        }
    }

    // Método para eliminar el documento
    public void deleteDocumento(Long id) {
        Optional<Documento> optionalDocumento = documentoRepositorio.findById(id);
        if (optionalDocumento.isPresent()) {
            Documento documento = optionalDocumento.get();
            File archivo = new File(documento.getUrl());
            if (archivo.exists()) {
                archivo.delete();  // Eliminar el archivo físico
            }
            documentoRepositorio.deleteById(id);  // Eliminar el registro de la base de datos
        }
    }

    // Método para actualizar el documento (reemplazando el archivo)
    public Documento updateDocumento(Long id, MultipartFile file) throws IOException {
        Optional<Documento> optionalDocumento = documentoRepositorio.findById(id);
        if (optionalDocumento.isPresent()) {
            Documento documento = optionalDocumento.get();

            // Eliminar el archivo anterior
            File archivoAnterior = new File(documento.getUrl());
            if (archivoAnterior.exists()) {
                archivoAnterior.delete();
            }

            // Comprimir el nuevo archivo
            byte[] fileData = file.getBytes();
            byte[] compressedData = comprimirArchivo(fileData);

            // Guardar el nuevo archivo comprimido
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename() + ".zip";
            Path filePath = Paths.get(DOCUMENTOS_DIR, fileName);
            Files.write(filePath, compressedData);

            documento.setUrl(filePath.toString());
            return documentoRepositorio.save(documento);
        } else {
            throw new RuntimeException("Documento no encontrado con ID: " + id);
        }
    }

    // Método para guardar el archivo en el disco y devolver la URL
    public String guardarArchivoEnDisco(byte[] contenido) throws IOException {
        // Ruta dinámica según el sistema operativo
        String directoryPath = System.getProperty("os.name").toLowerCase().contains("win") ?
                "C:/Documentos Udipsai/" : System.getProperty("user.home") + "/DocumentosUdipsai/";

        File directory = new File(directoryPath);

        if (!directory.exists()) {
            directory.mkdirs(); // Crear la carpeta si no existe
        }

        // Generar un nombre único para el archivo
        String fileName = "documento_" + UUID.randomUUID() + ".pdf";
        Path filePath = Paths.get(directoryPath, fileName);

        // Guardar el archivo en el disco
        Files.write(filePath, contenido);

        // Devolver la ruta completa del archivo
        return filePath.toString();
    }

}
