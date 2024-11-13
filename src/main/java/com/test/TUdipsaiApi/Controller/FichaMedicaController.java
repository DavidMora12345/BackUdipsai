package com.test.TUdipsaiApi.Controller;

import com.test.TUdipsaiApi.Model.FichaMedica;
import com.test.TUdipsaiApi.Service.FichaMedicaReportService;
import com.test.TUdipsaiApi.Service.FichaMedicaService;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/fichas-medicas")
@CrossOrigin(origins = "*")
public class FichaMedicaController {

    @Autowired
    private FichaMedicaService fichaMedicaService;

    @Autowired
    private FichaMedicaReportService fichaMedicaReportService;

    @GetMapping
    public List<FichaMedica> obtenerFichasActivas() {
        return fichaMedicaService.obtenerFichasActivas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FichaMedica> obtenerFichaPorId(@PathVariable int id) {
        return fichaMedicaService.obtenerFichaPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/paciente/{idPaciente}")
    public ResponseEntity<FichaMedica> obtenerFichaPorIdPaciente(@PathVariable int idPaciente) {
        FichaMedica fichaMedica = fichaMedicaService.obtenerFichaPorIdPaciente(idPaciente);
        if (fichaMedica != null) {
            return ResponseEntity.ok(fichaMedica);
        } else {
            return ResponseEntity.notFound().build(); // Si el paciente no existe
        }
    }

    @PostMapping
    public ResponseEntity<FichaMedica> crearFichaMedica(
            @RequestPart("fichaMedica") FichaMedica fichaMedica,
            @RequestPart(value = "genogramaFamiliar", required = false) MultipartFile genogramaFamiliar) {
        try {
            FichaMedica nuevaFicha = fichaMedicaService.crearFichaMedica(fichaMedica, genogramaFamiliar);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevaFicha);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body((FichaMedica) null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<FichaMedica> actualizarFichaMedica(
            @PathVariable int id,
            @RequestBody FichaMedica fichaMedica,
            @RequestParam(value = "genogramaFamiliar", required = false) String genogramaFamiliarBase64) {
        try {
            return fichaMedicaService.obtenerFichaPorId(id)
                    .map(fichaExistente -> {
                        fichaMedica.setId(fichaExistente.getId());

                        if (genogramaFamiliarBase64 != null && !genogramaFamiliarBase64.isEmpty()) {
                            byte[] genogramaFamiliarBytes = Base64.decodeBase64(genogramaFamiliarBase64);
                            fichaMedica.setGenogramaFamiliar(genogramaFamiliarBytes);
                        }

                        try {
                            FichaMedica fichaActualizada = fichaMedicaService.actualizarFichaMedica(fichaMedica);
                            return ResponseEntity.ok(fichaActualizada);
                        } catch (IOException e) {
                            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).<FichaMedica>body(null);
                        }
                    })
                    .orElse(ResponseEntity.<FichaMedica>notFound().build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).<FichaMedica>body(null);
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarFichaMedica(@PathVariable int id) {
        fichaMedicaService.eliminarFichaMedica(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/reporte")
    public ResponseEntity<byte[]> descargarReporteFichaMedica(@PathVariable int id) {
        try {
            byte[] pdfContent = fichaMedicaReportService.generarReporteFichaMedica(id);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=ficha_medica_" + id + ".pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfContent);
        } catch (RuntimeException e) {
            // Log para detalles adicionales sobre el error
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
