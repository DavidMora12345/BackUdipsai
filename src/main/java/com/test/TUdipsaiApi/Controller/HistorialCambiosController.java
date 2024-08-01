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
}
