package com.test.TUdipsaiApi.Controller;

import com.test.TUdipsaiApi.Model.Test;
import com.test.TUdipsaiApi.Service.TestService;
import com.test.TUdipsaiApi.dto.TestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tests")
@CrossOrigin(origins = "*")
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping
    public List<TestDTO> getAllTests() {
        return testService.getAllTests();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestDTO> getTestById(@PathVariable Long id) {
        Optional<TestDTO> test = testService.getTestById(id);
        return test.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Test> createTest(@RequestBody TestDTO testDTO) {
        Test savedTest = testService.saveTest(testDTO);
        return ResponseEntity.ok(savedTest);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Test> updateTest(@PathVariable Long id, @RequestBody TestDTO testDTO) {
        Test updatedTest = testService.updateTest(id, testDTO);
        return ResponseEntity.ok(updatedTest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTest(@PathVariable Long id) {
        testService.deleteTest(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/paciente/{pacienteId}")
    public List<TestDTO> getTestsByPacienteId(@PathVariable Long pacienteId) {
        return testService.getTestsByPacienteId(pacienteId);
    }

    @GetMapping("/paciente/{pacienteId}/especialista/{cedula}")
    public List<TestDTO> getTestsByPacienteIdAndEspecialistaCedula(@PathVariable Long pacienteId, @PathVariable String cedula) {
        return testService.getTestsByPacienteIdAndEspecialistaCedula(pacienteId, cedula);
    }
}
