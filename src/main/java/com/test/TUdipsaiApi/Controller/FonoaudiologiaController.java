package com.test.TUdipsaiApi.Controller;

import com.test.TUdipsaiApi.Model.Fonoaudiologia;
import com.test.TUdipsaiApi.Service.FonoaudiologiaReportService;
import com.test.TUdipsaiApi.Service.FonoaudiologiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fonoaudiologia")
@CrossOrigin(origins = "*")
public class FonoaudiologiaController {

    @Autowired
    private FonoaudiologiaService fonoaudiologiaService;

    @Autowired
    private FonoaudiologiaReportService fonoaudiologiaReportService;

    @GetMapping
    public List<Fonoaudiologia> obtenerActivos() {
        return fonoaudiologiaService.obtenerActivos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Fonoaudiologia> obtenerPorId(@PathVariable int id) {
        return fonoaudiologiaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/paciente/{idPaciente}")
    public ResponseEntity<Fonoaudiologia> obtenerPorIdPaciente(@PathVariable int idPaciente) {
        Fonoaudiologia fonoaudiologia = fonoaudiologiaService.obtenerPorIdPaciente(idPaciente);
        if (fonoaudiologia != null) {
            return ResponseEntity.ok(fonoaudiologia);
        } else {
            return ResponseEntity.notFound().build(); // Si el paciente no existe
        }
    }

    @PostMapping
    public Fonoaudiologia crear(@RequestBody Fonoaudiologia fonoaudiologia) {
        return fonoaudiologiaService.crearFonoaudiologia(fonoaudiologia);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Fonoaudiologia> actualizar(@PathVariable int id, @RequestBody Fonoaudiologia fonoaudiologia) {
        return fonoaudiologiaService.obtenerPorId(id)
                .map(fonoaudiologiaExistente -> {
                    fonoaudiologia.setId(fonoaudiologiaExistente.getId());
                    return ResponseEntity.ok(fonoaudiologiaService.actualizarFonoaudiologia(fonoaudiologia));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        fonoaudiologiaService.eliminarFonoaudiologia(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/reporte")
    public ResponseEntity<?> descargarReporteFonoaudiologia(@PathVariable int id) {
        try {
            byte[] pdfContent = fonoaudiologiaReportService.generarReporteFonoaudiologia(id);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=fonoaudiologia_" + id + ".pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfContent);
        } catch (RuntimeException e) {
            // Devolver un mensaje de error en lugar de un error 500
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
