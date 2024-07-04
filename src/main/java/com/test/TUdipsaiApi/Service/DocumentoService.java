package com.test.TUdipsaiApi.Service;

import com.test.TUdipsaiApi.Model.Documento;
import com.test.TUdipsaiApi.Repository.DocumentoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class DocumentoService {

    @Autowired
    private DocumentoRepositorio documentoRepositorio;

    public Documento saveDocumento(MultipartFile file) throws IOException {
        Documento documento = new Documento();
        documento.setContenido(file.getBytes());
        return documentoRepositorio.save(documento);
    }

    public Optional<Documento> findById(Long id) {
        return documentoRepositorio.findById(id);
    }

    public void deleteDocumento(Long id) {
        documentoRepositorio.deleteById(id);
    }

    public Documento updateDocumento(Long id, MultipartFile file) throws IOException {
        Optional<Documento> optionalDocumento = documentoRepositorio.findById(id);
        if (optionalDocumento.isPresent()) {
            Documento documento = optionalDocumento.get();
            documento.setContenido(file.getBytes());
            return documentoRepositorio.save(documento);
        } else {
            throw new RuntimeException("Documento not found with ID: " + id);
        }
    }

    public Optional<Documento> getDocumentoById(Long id) {
        return documentoRepositorio.findById(id);
    }
}
