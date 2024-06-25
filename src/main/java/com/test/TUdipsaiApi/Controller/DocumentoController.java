package com.test.TUdipsaiApi.Controller;

import com.test.TUdipsaiApi.Service.DocumentoService;
import com.test.TUdipsaiApi.dto.DocumentoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/documentos")
@CrossOrigin(origins = "*")
public class DocumentoController {

    @Autowired
    private DocumentoService documentoService;

    @PostMapping
    public ResponseEntity<DocumentoDTO> createDocumento(@RequestBody DocumentoDTO documentoDTO) {
        DocumentoDTO savedDocumento = documentoService.saveDocumento(documentoDTO);
        return ResponseEntity.ok(savedDocumento);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentoDTO> getDocumentoById(@PathVariable Long id) {
        Optional<DocumentoDTO> documento = documentoService.getDocumentoById(id);
        return documento.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocumento(@PathVariable Long id) {
        documentoService.deleteDocumento(id);
        return ResponseEntity.noContent().build();
    }
}
