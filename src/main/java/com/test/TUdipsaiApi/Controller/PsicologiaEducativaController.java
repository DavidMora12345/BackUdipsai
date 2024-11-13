package com.test.TUdipsaiApi.Controller;

import com.test.TUdipsaiApi.Model.PsicologiaEducativa;
import com.test.TUdipsaiApi.Service.PsicologiaEducativaReportService;
import com.test.TUdipsaiApi.Service.PsicologiaEducativaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/psicologia-educativa")
@CrossOrigin(origins = "*")
public class PsicologiaEducativaController {

    @Autowired
    private PsicologiaEducativaService psicologiaEducativaService;
    @Autowired
    private PsicologiaEducativaReportService psicologiaEducativaReportService;

    @GetMapping
    public List<PsicologiaEducativa> obtenerActivos() {
        return psicologiaEducativaService.obtenerActivos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PsicologiaEducativa> obtenerPorId(@PathVariable int id) {
        return psicologiaEducativaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/paciente/{idPaciente}")
    public ResponseEntity<PsicologiaEducativa> obtenerPorIdPaciente(@PathVariable int idPaciente) {
        PsicologiaEducativa psicologiaEducativa = psicologiaEducativaService.obtenerPorIdPaciente(idPaciente);
        if (psicologiaEducativa != null) {
            return ResponseEntity.ok(psicologiaEducativa);
        } else {
            return ResponseEntity.notFound().build(); // Si el paciente no existe
        }
    }

    @PostMapping
    public PsicologiaEducativa crear(@RequestBody PsicologiaEducativa psicologiaEducativa) {
        return psicologiaEducativaService.crearPsicologiaEducativa(psicologiaEducativa);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PsicologiaEducativa> actualizar(@PathVariable int id, @RequestBody PsicologiaEducativa psicologiaEducativa) {
        return psicologiaEducativaService.obtenerPorId(id)
                .map(psicologiaExistente -> {
                    psicologiaEducativa.setId(psicologiaExistente.getId());
                    return ResponseEntity.ok(psicologiaEducativaService.actualizarPsicologiaEducativa(psicologiaEducativa));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable int id) {
        psicologiaEducativaService.eliminarPsicologiaEducativa(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/reporte")
    public ResponseEntity<?> descargarReportePsicologiaEducativa(@PathVariable int id) {
        try {
            byte[] pdfContent = psicologiaEducativaReportService.generarReportePsicologiaEducativa(id);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=psicologia_educativa_" + id + ".pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfContent);
        } catch (RuntimeException e) {
            // Devolver un mensaje de error en lugar de un error 500
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
