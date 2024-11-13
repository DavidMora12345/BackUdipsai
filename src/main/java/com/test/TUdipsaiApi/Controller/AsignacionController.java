package com.test.TUdipsaiApi.Controller;

import com.test.TUdipsaiApi.Model.Asignacion;
import com.test.TUdipsaiApi.Service.AsignacionService;
import com.test.TUdipsaiApi.dto.PacienteSinImagenDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/asignaciones")
@CrossOrigin(origins = "*")
public class AsignacionController {

    @Autowired
    private AsignacionService asignacionService;

    @PostMapping("/asignar")
    public ResponseEntity<?> asignarPaciente(@RequestBody Map<String, Object> request) {
        try {
            Long pacienteId = ((Number) request.get("pacienteId")).longValue();
            String pasanteId = (String) request.get("pasanteId");
            Asignacion asignacion = asignacionService.asignarPaciente(pacienteId, pasanteId);
            return ResponseEntity.ok(asignacion);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/pasante/{pasanteId}")
    public ResponseEntity<List<Asignacion>> obtenerAsignacionesPorPasante(@PathVariable String pasanteId) {
        List<Asignacion> asignaciones = asignacionService.obtenerAsignacionesPorPasante(pasanteId);
        return ResponseEntity.ok(asignaciones);
    }

    @GetMapping("/pasante/{pasanteId}/sede/{sedeId}")
    public ResponseEntity<List<Asignacion>> obtenerAsignacionesPorPasanteYSede(@PathVariable String pasanteId, @PathVariable Integer sedeId) {
        List<Asignacion> asignaciones = asignacionService.obtenerAsignacionesPorPasanteYSede(pasanteId, sedeId);
        return ResponseEntity.ok(asignaciones);
    }

    @GetMapping("/todas")
    public ResponseEntity<List<Asignacion>> obtenerTodasAsignaciones() {
        List<Asignacion> asignaciones = asignacionService.obtenerTodasAsignaciones();
        return ResponseEntity.ok(asignaciones);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarAsignacion(@PathVariable Long id) {
        try {
            asignacionService.eliminarAsignacion(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error al eliminar la asignación");
        }
    }

    @PostMapping("/buscar")
    public ResponseEntity<List<PacienteSinImagenDTO>> buscarAsignaciones(
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "sedeId", required = false) Integer sedeId,
            @RequestParam(value = "pasanteId", required = true) String pasanteId) {

        List<PacienteSinImagenDTO> pacientes = asignacionService.searchAsignaciones(search, sedeId, pasanteId);
        return ResponseEntity.ok(pacientes);
    }
}
