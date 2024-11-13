package com.test.TUdipsaiApi.Controller;

import com.test.TUdipsaiApi.Model.Documento;
import com.test.TUdipsaiApi.Service.DocumentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

@RestController
@RequestMapping("/api/documentos")
@CrossOrigin(origins = "*")
public class DocumentoController {

    @Autowired
    private DocumentoService documentoService;

    // Guardar un documento (comprimido)
    @PostMapping
    public ResponseEntity<Documento> createDocumento(@RequestParam("file") MultipartFile file) {
        try {
            Documento savedDocumento = documentoService.saveDocumento(file);
            return ResponseEntity.ok(savedDocumento);
        } catch (IOException e) {
            return ResponseEntity.status(500).build();
        }
    }

    // Obtener documento por ID
    @GetMapping("/{id}")
    public ResponseEntity<Documento> getDocumentoById(@PathVariable Long id) {
        Optional<Documento> documento = documentoService.getDocumentoById(id);
        return documento.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/contenido")
    public ResponseEntity<String> getContenidoDocumento(@PathVariable Long id) {
        try {
            byte[] contenido = documentoService.getContenido(id);
            HttpHeaders headers = new HttpHeaders();
            headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=document_" + id + ".pdf");
            return ResponseEntity.ok().headers(headers).body(Base64.getEncoder().encodeToString(contenido));
        } catch (IOException e) {
            return ResponseEntity.status(500).build();
        }
    }

    
    // Eliminar un documento
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocumento(@PathVariable Long id) {
        documentoService.deleteDocumento(id);
        return ResponseEntity.noContent().build();
    }

    // Actualizar documento (reemplazar archivo)
    @PutMapping("/{id}")
    public ResponseEntity<Documento> updateDocumento(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            Documento updatedDocumento = documentoService.updateDocumento(id, file);
            return ResponseEntity.ok(updatedDocumento);
        } catch (IOException e) {
            return ResponseEntity.status(500).build();
        }
    }
}
