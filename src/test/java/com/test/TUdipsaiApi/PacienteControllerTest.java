package com.test.TUdipsaiApi;


import com.test.TUdipsaiApi.Controller.PacienteController;
import com.test.TUdipsaiApi.Model.Paciente;
import com.test.TUdipsaiApi.Service.PacienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PacienteControllerTest {

    private PacienteController pacienteController;
    private PacienteService pacienteService;

    @BeforeEach
    public void setup() {

        pacienteService = new PacienteService() {
            @Override
            public Paciente createPaciente(Paciente paciente) {

                paciente.setId(1);
                return paciente;
            }
        };


        pacienteController = new PacienteController();
        pacienteController.setPacienteService(pacienteService);
    }

    @Test
    public void testCreatePaciente() {
        Paciente paciente = new Paciente();
        paciente.setCedula("1234567890");
        paciente.setNombresApellidos("Juan Perez");
        paciente.setEdad("30");
        paciente.setDomicilio("Calle 123");
        paciente.setCiudad("Cuenca");
        paciente.setTelefono("1234567");
        paciente.setCelular("0987654321");

        ResponseEntity<Paciente> response = pacienteController.createPaciente(paciente);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("1234567890", response.getBody().getCedula());
        assertEquals("Juan Perez", response.getBody().getNombresApellidos());
        assertEquals(1, response.getBody().getId());
    }
}
