package com.test.TUdipsaiApi.Controller;

import com.test.TUdipsaiApi.Model.PsicologiaClinica;
import com.test.TUdipsaiApi.Service.PsicologiaClinicaReportService;
import com.test.TUdipsaiApi.Service.PsicologiaClinicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/psicologia-clinica")
@CrossOrigin(origins = "*")
public class PsicologiaClinicaController {

    @Autowired
    private PsicologiaClinicaService psicologiaClinicaService;
    @Autowired
    private PsicologiaClinicaReportService psicologiaClinicaReportService;

    @GetMapping
    public List<PsicologiaClinica> obtenerFichasActivas() {
        return psicologiaClinicaService.obtenerFichasActivas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PsicologiaClinica> obtenerFichaPorId(@PathVariable int id) {
        return psicologiaClinicaService.obtenerFichaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/paciente/{idPaciente}")
    public ResponseEntity<PsicologiaClinica> obtenerFichaPorIdPaciente(@PathVariable int idPaciente) {
        PsicologiaClinica ficha = psicologiaClinicaService.obtenerFichaPorIdPaciente(idPaciente);
        if (ficha != null) {
            return ResponseEntity.ok(ficha);
        } else {
            return ResponseEntity.notFound().build(); // Si el paciente no existe
        }
    }

    @PostMapping
    public PsicologiaClinica crearFicha(@RequestBody PsicologiaClinica psicologiaClinica) {
        return psicologiaClinicaService.crearFicha(psicologiaClinica);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PsicologiaClinica> actualizarFicha(@PathVariable int id, @RequestBody PsicologiaClinica psicologiaClinica) {
        return psicologiaClinicaService.obtenerFichaPorId(id)
                .map(fichaExistente -> {
                    psicologiaClinica.setId(fichaExistente.getId());
                    return ResponseEntity.ok(psicologiaClinicaService.actualizarFicha(psicologiaClinica));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarFicha(@PathVariable int id) {
        psicologiaClinicaService.eliminarFicha(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/reporte")
    public ResponseEntity<?> descargarReportePsicologiaClinica(@PathVariable int id) {
        try {
            byte[] pdfContent = psicologiaClinicaReportService.generarReportePsicologiaClinica(id);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=psicologia_clinica_" + id + ".pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfContent);
        } catch (RuntimeException e) {
            // Devolver un mensaje de error en lugar de un error 500
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
