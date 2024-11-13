package com.test.TUdipsaiApi.Service;

import com.test.TUdipsaiApi.Model.Asignacion;
import com.test.TUdipsaiApi.Model.Paciente;
import com.test.TUdipsaiApi.Model.Especialistas;
import com.test.TUdipsaiApi.Repository.AsignacionRepositorio;
import com.test.TUdipsaiApi.Repository.PacienteRepositorio;
import com.test.TUdipsaiApi.Repository.EspecialistasRepositorio;
import com.test.TUdipsaiApi.dto.PacienteSinImagenDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AsignacionService {

    @Autowired
    private AsignacionRepositorio asignacionRepositorio;

    @Autowired
    private PacienteRepositorio pacienteRepositorio;

    @Autowired
    private EspecialistasRepositorio especialistasRepositorio;

    @Autowired
    private PacienteService pacienteService;

    public Asignacion asignarPaciente(Long pacienteId, String pasanteId) {
        Optional<Paciente> pacienteOpt = pacienteRepositorio.findById(pacienteId.intValue());
        Optional<Especialistas> pasanteOpt = especialistasRepositorio.findById(pasanteId);

        if (pacienteOpt.isPresent() && pasanteOpt.isPresent()) {
            Especialistas pasante = pasanteOpt.get();
            if (!pasante.getEsPasante()) {
                throw new RuntimeException("El especialista no es un pasante");
            }
            Asignacion asignacion = new Asignacion();
            asignacion.setPaciente(pacienteOpt.get());
            asignacion.setPasante(pasante);
            return asignacionRepositorio.save(asignacion);
        } else {
            throw new RuntimeException("Paciente o Pasante no encontrado");
        }
    }

    public List<Asignacion> obtenerAsignacionesPorPasante(String pasanteId) {
        return asignacionRepositorio.findByPasante_Cedula(pasanteId);
    }

    public List<Asignacion> obtenerAsignacionesPorPasanteYSede(String pasanteId, Integer sedeId) {
        return asignacionRepositorio.findByPasante_CedulaAndPaciente_Sede_Id(pasanteId, sedeId);
    }

    public List<Asignacion> obtenerTodasAsignaciones() {
        return asignacionRepositorio.findAll();
    }

    public void eliminarAsignacion(Long id) {
        asignacionRepositorio.deleteById(id);
    }

    public List<PacienteSinImagenDTO> searchAsignaciones(String busqueda, Integer sedeId, String pasanteId) {
        List<Asignacion> asignaciones = asignacionRepositorio.searchAsignaciones(busqueda, sedeId, pasanteId, PageRequest.of(0, 100));
        return asignaciones.stream()
                .map(asignacion -> pacienteService.convertToSinImagenDTO(asignacion.getPaciente()))
                .collect(Collectors.toList());
    }
}
