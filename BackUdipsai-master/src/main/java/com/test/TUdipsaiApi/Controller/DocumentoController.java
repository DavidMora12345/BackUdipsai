package com.test.TUdipsaiApi.Controller;

import com.test.TUdipsaiApi.Model.Documento;
import com.test.TUdipsaiApi.Service.DocumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api/documentos")
@CrossOrigin(origins = "*")
public class DocumentoController {

    @Autowired
    private DocumentoService documentoService;

    @PostMapping
    public ResponseEntity<Documento> createDocumento(@RequestParam("file") MultipartFile file) {
        try {
            Documento savedDocumento = documentoService.saveDocumento(file);
            return ResponseEntity.ok(savedDocumento);
        } catch (IOException e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Documento> getDocumentoById(@PathVariable Long id) {
        Optional<Documento> documento = documentoService.getDocumentoById(id);
        return documento.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocumento(@PathVariable Long id) {
        documentoService.deleteDocumento(id);
        return ResponseEntity.noContent().build();
    }
}
