package com.test.TUdipsaiApi.Controller;

import com.test.TUdipsaiApi.Model.HistorialCambios;
import com.test.TUdipsaiApi.Service.HistorialCambiosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historial-cambios")
@CrossOrigin(origins = "*")
public class HistorialCambiosController {

    @Autowired
    private HistorialCambiosService historialCambiosService;

    @GetMapping("/listar")
    public ResponseEntity<List<HistorialCambios>> listarHistorialCambios() {
        List<HistorialCambios> historialCambios = historialCambiosService.findAll();
        return ResponseEntity.ok(historialCambios);
    }

    @GetMapping("/listar/{entidadId}")
    public ResponseEntity<List<HistorialCambios>> listarHistorialCambiosPorEntidadId(@PathVariable Long entidadId) {
        List<HistorialCambios> historialCambios = historialCambiosService.findByEntidadId(entidadId);
        return ResponseEntity.ok(historialCambios);
    }

    @GetMapping("/listar/ficha-medica/{fichaMedicaId}")
    public ResponseEntity<List<HistorialCambios>> listarHistorialCambiosPorFichaMedicaId(@PathVariable Long fichaMedicaId) {
        List<HistorialCambios> historialCambios = historialCambiosService.findByEntidadIdAndEntidad(fichaMedicaId, "FichaMedica");
        return ResponseEntity.ok(historialCambios);
    }

    @GetMapping("/listar/psicologia-educativa/{psicologiaEducativaId}")
    public ResponseEntity<List<HistorialCambios>> listarHistorialCambiosPorPsicologiaEducativaId(@PathVariable Long psicologiaEducativaId) {
        List<HistorialCambios> historialCambios = historialCambiosService.findByEntidadIdAndEntidad(psicologiaEducativaId, "PsicologiaEducativa");
        return ResponseEntity.ok(historialCambios);
    }

    @GetMapping("/listar/psicologia-clinica/{psicologiaClinicaId}")
    public ResponseEntity<List<HistorialCambios>> listarHistorialCambiosPorPsicologiaClinicaId(@PathVariable Long psicologiaClinicaId) {
        List<HistorialCambios> historialCambios = historialCambiosService.findByEntidadIdAndEntidad(psicologiaClinicaId, "PsicologiaClinica");
        return ResponseEntity.ok(historialCambios);
    }

    @GetMapping("/listar/historial/{fonoaudiologiaId}")
    public ResponseEntity<List<HistorialCambios>> listarHistorialCambiosPorFonoaudiologiaId(@PathVariable Long fonoaudiologiaId) {
        List<HistorialCambios> historialCambios = historialCambiosService.findByEntidadIdAndEntidad(fonoaudiologiaId, "Fonoaudiologia");
        return ResponseEntity.ok(historialCambios);
    }

    @GetMapping("/listar/todos/{pacienteId}")
    public ResponseEntity<List<HistorialCambios>> listarTodosLosHistorialesPorPaciente(@PathVariable Long pacienteId) {
        List<HistorialCambios> historialCambios = historialCambiosService.findAllHistorialesPorPacienteOrdenadosPorFecha(pacienteId);
        return ResponseEntity.ok(historialCambios);
    }

}