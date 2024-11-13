package com.test.TUdipsaiApi.Service;

import com.test.TUdipsaiApi.Model.Paciente;
import com.test.TUdipsaiApi.Model.PsicologiaEducativa;
import com.test.TUdipsaiApi.Repository.PacienteRepositorio;
import com.test.TUdipsaiApi.Repository.PsicologiaEducativaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PsicologiaEducativaService {

    @Autowired
    private PsicologiaEducativaRepository psicologiaEducativaRepository;

    @Autowired
    private PacienteRepositorio pacienteRepository;

    @Autowired
    private HistorialCambiosService historialCambiosService;

    public List<PsicologiaEducativa> obtenerActivos() {
        return psicologiaEducativaRepository.findByEstado(1);
    }

    public Optional<PsicologiaEducativa> obtenerPorId(int id) {
        return psicologiaEducativaRepository.findById(id);
    }

    public List<PsicologiaEducativa> obtenerPorPaciente(int idPaciente) {
        return psicologiaEducativaRepository.findByPacienteIdAndEstado(idPaciente, 1);
    }

    public PsicologiaEducativa crearPsicologiaEducativa(PsicologiaEducativa psicologiaEducativa) {
        psicologiaEducativa.setEstado(1); // Estado activo por defecto
        PsicologiaEducativa nuevaFicha = psicologiaEducativaRepository.save(psicologiaEducativa);

        // Registro de cambios
        historialCambiosService.registrarCambio(
                "PsicologiaEducativa",
                nuevaFicha.getId().longValue(),
                "CREATE",
                null, // No hay valor anterior en la creación
                nuevaFicha
        );

        return nuevaFicha;
    }

    public PsicologiaEducativa actualizarPsicologiaEducativa(PsicologiaEducativa psicologiaEducativa) {
        Optional<PsicologiaEducativa> fichaExistente = psicologiaEducativaRepository.findById(psicologiaEducativa.getId());
        if (fichaExistente.isPresent()) {
            PsicologiaEducativa valorAnterior = new PsicologiaEducativa(fichaExistente.get());

            PsicologiaEducativa fichaActualizada = psicologiaEducativaRepository.save(psicologiaEducativa);

            // Registro de cambios
            historialCambiosService.registrarCambio(
                    "PsicologiaEducativa",
                    psicologiaEducativa.getId().longValue(),
                    "UPDATE",
                    valorAnterior,
                    fichaActualizada
            );

            return fichaActualizada;
        } else {
            throw new RuntimeException("Psicología Educativa no encontrada");
        }
    }

    public void eliminarPsicologiaEducativa(int id) {
        psicologiaEducativaRepository.findById(id).ifPresent(psicologia -> {
            psicologia.setEstado(0); // Cambiar estado a inactivo en lugar de eliminar
            psicologiaEducativaRepository.save(psicologia);
        });
    }

    public PsicologiaEducativa obtenerPorIdPaciente(int idPaciente) {
        List<PsicologiaEducativa> fichas = psicologiaEducativaRepository.findByPacienteIdAndEstado(idPaciente, 1);

        if (fichas.isEmpty()) {
            Optional<Paciente> pacienteOptional = pacienteRepository.findById(idPaciente);
            if (pacienteOptional.isPresent()) {
                Paciente paciente = pacienteOptional.get();

                PsicologiaEducativa nuevaFicha = new PsicologiaEducativa();
                nuevaFicha.setPaciente(paciente);
                nuevaFicha.setEstado(1); // Estado activo

                // Establecer los valores booleanos a false
                nuevaFicha.setGustaIrInstitucion(false);
                nuevaFicha.setInclusionEducativa(false);
                nuevaFicha.setAdaptacionesCurriculares(false);
                nuevaFicha.setCdi(false);
                nuevaFicha.setInicial1(false);
                nuevaFicha.setInicial2(false);
                nuevaFicha.setPrimerEGB(false);
                nuevaFicha.setPerdidaAnio(false);
                nuevaFicha.setDesercionEscolar(false);
                nuevaFicha.setCambioInstitucion(false);
                nuevaFicha.setProblemasAprendizaje(false);
                nuevaFicha.setEvaluacionPsicologicaUOtrosAnterior(false);
                nuevaFicha.setRecibeApoyo(false);

                // Guardar la nueva ficha en la base de datos
                PsicologiaEducativa fichaGuardada = psicologiaEducativaRepository.save(nuevaFicha);

                // Registrar el cambio en el historial después de guardar la ficha médica
                historialCambiosService.registrarCambio(
                        "PsicologiaEducativa",
                        fichaGuardada.getId().longValue(),
                        "CREATE",
                        null,
                        fichaGuardada
                );

                return fichaGuardada;
            } else {
                // Manejar el caso donde el paciente no existe, si es necesario.
                return null;
            }
        } else {
            // Devolver la ficha existente
            return fichas.get(0); // Considerando que debería haber solo una ficha por paciente
        }
    }
}