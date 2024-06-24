package com.test.TUdipsaiApi.Service;

import com.test.TUdipsaiApi.Model.Documento;
import com.test.TUdipsaiApi.Repository.DocumentoRepositorio;
import com.test.TUdipsaiApi.dto.DocumentoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DocumentoService {

    @Autowired
    private DocumentoRepositorio documentoRepositorio;

    public DocumentoDTO saveDocumento(DocumentoDTO documentoDTO) {
        Documento documento = new Documento();
        documento.setContenido(documentoDTO.getContenido());
        Documento savedDocumento = documentoRepositorio.save(documento);
        DocumentoDTO responseDTO = new DocumentoDTO();
        responseDTO.setId(savedDocumento.getId());
        responseDTO.setContenido(savedDocumento.getContenido());
        return responseDTO;
    }

    public Optional<DocumentoDTO> getDocumentoById(Long id) {
        return documentoRepositorio.findById(id).map(documento -> {
            DocumentoDTO documentoDTO = new DocumentoDTO();
            documentoDTO.setId(documento.getId());
            documentoDTO.setContenido(documento.getContenido());
            return documentoDTO;
        });
    }

    public void deleteDocumento(Long id) {
        documentoRepositorio.deleteById(id);
    }
}
