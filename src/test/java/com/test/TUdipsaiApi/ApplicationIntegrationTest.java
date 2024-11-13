package com.test.TUdipsaiApi;

import com.test.TUdipsaiApi.Model.Test;
import com.test.TUdipsaiApi.Model.Seguimiento;
import com.test.TUdipsaiApi.Model.Paciente;
import com.test.TUdipsaiApi.Model.Especialistas;
import com.test.TUdipsaiApi.Repository.TestRepositorio;
import com.test.TUdipsaiApi.Repository.SeguimientoRepositorio;
import com.test.TUdipsaiApi.Repository.PacienteRepositorio;
import com.test.TUdipsaiApi.Repository.EspecialistasRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.Date;
import java.util.List;

@SpringBootTest
@Transactional
public class ApplicationIntegrationTest {

    @Autowired
    private TestRepositorio testRepositorio;

    @Autowired
    private SeguimientoRepositorio seguimientoRepositorio;

    @Autowired
    private PacienteRepositorio pacienteRepositorio;

    @Autowired
    private EspecialistasRepositorio especialistasRepositorio;

    @org.junit.jupiter.api.Test
    public void testFullApplicationIntegration() {
        // Crear y guardar un nuevo paciente
        Paciente paciente = new Paciente();
        paciente.setFechaApertura(new Date());
        paciente.setPacienteEstado(1);
        paciente.setNombresApellidos("Juan Perez");
        paciente.setCiudad("Cuenca");
        paciente.setFechaNacimiento(new Date());
        paciente.setEdad("25");
        paciente.setCedula("0102030405");
        paciente.setDomicilio("Av. Loja 123");
        paciente.setTelefono("072222222");
        paciente.setCelular("0999999999");
        paciente = pacienteRepositorio.save(paciente);

        // Crear y guardar un nuevo especialista
        Especialistas especialista = new Especialistas();
        especialista.setCedula("0102030406");
        especialista.setPrimerNombre("Ana");
        especialista.setPrimerApellido("Gomez");
        especialista = especialistasRepositorio.save(especialista);

        // Crear y guardar un nuevo test
        Test test = new Test();
        test.setPaciente(paciente);
        test.setEspecialista(especialista);
        test.setNombreArchivo("test.pdf");
        test.setFecha(new Date());
        test.setActivo(1);
        test = testRepositorio.save(test);

        // Crear y guardar un nuevo seguimiento
        Seguimiento seguimiento = new Seguimiento();
        seguimiento.setEspecialista(especialista);
        seguimiento.setPaciente(paciente);
        seguimiento.setFecha(new Date());
        seguimiento.setObservacion("Primera observacion");
        seguimiento.setEstado(1);
        seguimiento = seguimientoRepositorio.save(seguimiento);

        // Verificar que el paciente se haya guardado correctamente
        Paciente pacienteGuardado = pacienteRepositorio.findById(paciente.getId()).orElse(null);
        assertNotNull(pacienteGuardado);
        assertEquals("Juan Perez", pacienteGuardado.getNombresApellidos());

        // Verificar que el especialista se haya guardado correctamente
        Especialistas especialistaGuardado = especialistasRepositorio.findById(especialista.getCedula()).orElse(null);
        assertNotNull(especialistaGuardado);
        assertEquals("Ana", especialistaGuardado.getPrimerNombre());

        // Verificar que el test se haya guardado correctamente
        Test testGuardado = testRepositorio.findById(test.getId()).orElse(null);
        assertNotNull(testGuardado);
        assertEquals("test.pdf", testGuardado.getNombreArchivo());

        // Verificar que el seguimiento se haya guardado correctamente
        Seguimiento seguimientoGuardado = seguimientoRepositorio.findById(seguimiento.getId()).orElse(null);
        assertNotNull(seguimientoGuardado);
        assertEquals("Primera observacion", seguimientoGuardado.getObservacion());

        // Verificar que los tests del paciente y especialista se hayan guardado correctamente
        List<Test> testsPacienteEspecialista = testRepositorio.findByPacienteIdAndEspecialistaCedulaAndActivo(paciente.getId().longValue(), especialista.getCedula(), 1);
        assertNotNull(testsPacienteEspecialista);
        assertEquals(1, testsPacienteEspecialista.size());

        // Verificar que los seguimientos del paciente se hayan guardado correctamente
        List<Seguimiento> seguimientosPaciente = seguimientoRepositorio.findByPacienteIdAndEstado(paciente.getId(), 1);
        assertNotNull(seguimientosPaciente);
        assertEquals(1, seguimientosPaciente.size());
    }


}
