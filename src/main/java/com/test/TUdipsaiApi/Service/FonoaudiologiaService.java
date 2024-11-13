package com.test.TUdipsaiApi.Service;

import com.test.TUdipsaiApi.Model.Fonoaudiologia;
import com.test.TUdipsaiApi.Model.Paciente;
import com.test.TUdipsaiApi.Repository.FonoaudiologiaRepository;
import com.test.TUdipsaiApi.Repository.PacienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FonoaudiologiaService {

    @Autowired
    private FonoaudiologiaRepository fonoaudiologiaRepository;

    @Autowired
    private PacienteRepositorio pacienteRepository;

    @Autowired
    private HistorialCambiosService historialCambiosService;


    public List<Fonoaudiologia> obtenerActivos() {
        return fonoaudiologiaRepository.findByEstado(1);
    }

    public Optional<Fonoaudiologia> obtenerPorId(int id) {
        return fonoaudiologiaRepository.findById(id);
    }

    public List<Fonoaudiologia> obtenerPorPaciente(int idPaciente) {
        return fonoaudiologiaRepository.findByPacienteIdAndEstado(idPaciente, 1);
    }

    public Fonoaudiologia crearFonoaudiologia(Fonoaudiologia fonoaudiologia) {
        fonoaudiologia.setEstado(1); // Estado activo por defecto
        Fonoaudiologia nuevaFicha = fonoaudiologiaRepository.save(fonoaudiologia);

        // Registro de cambios
        historialCambiosService.registrarCambio(
                "Fonoaudiologia",
                nuevaFicha.getId().longValue(),
                "CREATE",
                null, // No hay valor anterior en la creación
                nuevaFicha
        );

        return nuevaFicha;
    }

    public Fonoaudiologia actualizarFonoaudiologia(Fonoaudiologia fonoaudiologia) {
        Optional<Fonoaudiologia> fichaExistente = fonoaudiologiaRepository.findById(fonoaudiologia.getId());
        if (fichaExistente.isPresent()) {
            Fonoaudiologia valorAnterior = new Fonoaudiologia(fichaExistente.get());

            Fonoaudiologia fichaActualizada = fonoaudiologiaRepository.save(fonoaudiologia);

            // Registro de cambios
            historialCambiosService.registrarCambio(
                    "Fonoaudiologia",
                    fichaActualizada.getId().longValue(),
                    "UPDATE",
                    valorAnterior,
                    fichaActualizada
            );

            return fichaActualizada;
        } else {
            throw new RuntimeException("Ficha de Fonoaudiología no encontrada");
        }
    }

    public void eliminarFonoaudiologia(int id) {
        fonoaudiologiaRepository.findById(id).ifPresent(fonoaudiologia -> {
            fonoaudiologia.setEstado(0); // Cambiar estado a inactivo en lugar de eliminar
            fonoaudiologiaRepository.save(fonoaudiologia);
        });
    }

    public Fonoaudiologia obtenerPorIdPaciente(int idPaciente) {
        List<Fonoaudiologia> fichas = fonoaudiologiaRepository.findByPacienteIdAndEstado(idPaciente, 1);

        if (fichas.isEmpty()) {
            Optional<Paciente> paciente = pacienteRepository.findById(idPaciente);
            if (paciente.isPresent()) {
                Fonoaudiologia nuevaFicha = new Fonoaudiologia();
                nuevaFicha.setPaciente(paciente.get());
                nuevaFicha.setEstado(1); // Estado activo

                // Establecer los valores booleanos a false
                nuevaFicha.setDificultadPronunciarPalabras(false);
                nuevaFicha.setSeTrabaCuandoHabla(false);
                nuevaFicha.setSeEntiendeLoQueDice(false);
                nuevaFicha.setSabeComoLlamanObjetosEntorno(false);
                nuevaFicha.setComprendeLoQueSeLeDice(false);
                nuevaFicha.setReconoceFuenteSonora(false);
                nuevaFicha.setSeARealizadoExamenAudiologico(false);
                nuevaFicha.setPerdidaAuditivaConductivaNeurosensorial(false);
                nuevaFicha.setHipoacusiaConductivaBilateral(false);
                nuevaFicha.setHipoacusiaConductivaUnilateral(false);
                nuevaFicha.setHipoacusiaNeurosensorialBilateral(false);
                nuevaFicha.setHipoacusiaNeurosensorialUnilateral(false);
                nuevaFicha.setTrastornoEspecificoPronunciacion(false);
                nuevaFicha.setTrastornoLenguajeExpresivo(false);
                nuevaFicha.setAfasiaAdquiridaEpilepsia(false);
                nuevaFicha.setOtrosTrastornosDesarrolloHabla(false);
                nuevaFicha.setTrastornoDesarrolloHablaLenguaje(false);
                nuevaFicha.setTrastornoRecepcionLenguaje(false);
                nuevaFicha.setAlteracionesHabla(false);
                nuevaFicha.setDisfasiaAfasia(false);
                nuevaFicha.setDisartriaAnartria(false);
                nuevaFicha.setOtrasAlteracionesHabla(false);
                nuevaFicha.setATenidoPerdidaAudicionPasado(false);
                nuevaFicha.setInfeccionesOidoFuertes(false);
                nuevaFicha.setCreeTonoVozEstudianteApropiado(false);
                nuevaFicha.setRespiracionNormal(false);
                nuevaFicha.setRonca(false);
                nuevaFicha.setJuegoVocal(false);
                nuevaFicha.setVocalizacion(false);
                nuevaFicha.setBalbuceo(false);
                nuevaFicha.setSilabeo(false);
                nuevaFicha.setPrimerasPalabras(false);
                nuevaFicha.setOracionesDosPalabras(false);
                nuevaFicha.setOracionesTresPalabras(false);
                nuevaFicha.setFormacionLinguisticaCompleta(false);
                nuevaFicha.setPerdidaAuditiva(false);
                nuevaFicha.setUnilateral(false);
                nuevaFicha.setOidoDerecho(false);
                nuevaFicha.setOidoIzquierdo(false);
                nuevaFicha.setBilateral(false);
                nuevaFicha.setOtitis(false);
                nuevaFicha.setAntecedentesFamiliares(false);
                nuevaFicha.setExposisionRuidos(false);
                nuevaFicha.setOtotoxicos(false);
                nuevaFicha.setInfecciones(false);
                nuevaFicha.setUsoAudifonos(false);
                nuevaFicha.setImplanteCoclear(false);
                nuevaFicha.setTratamientoFonoaudiologicoPrevio(false);
                nuevaFicha.setOtalgia(false);
                nuevaFicha.setOtalgiaUnilateral(false);
                nuevaFicha.setOtalgiaOidoDerecho(false);
                nuevaFicha.setOtalgiaOidoIzquierdo(false);
                nuevaFicha.setOtalgiaBilateral(false);
                nuevaFicha.setPermanenciaOtalgiaContinua(false);
                nuevaFicha.setPermanenciaOtalgiaIntermitente(false);
                nuevaFicha.setAsociadaOtalgiaInfeccionRespiratoriaAlta(false);
                nuevaFicha.setInfeccionRespiratoriaPunzante(false);
                nuevaFicha.setInfeccionRespiratoriaPulsatil(false);
                nuevaFicha.setInfeccionRespiratoriaProgresivo(false);
                nuevaFicha.setInfeccionRespiratoriaOpresivo(false);
                nuevaFicha.setPruriginoso(false);
                nuevaFicha.setAumentaMasticar(false);
                nuevaFicha.setDisminuyeConCalorLocal(false);
                nuevaFicha.setAumentaConCalorLocal(false);
                nuevaFicha.setOtorrea(false);
                nuevaFicha.setOtorreaUnilateral(false);
                nuevaFicha.setOtorreaOidoDerecho(false);
                nuevaFicha.setOtorreaOidoIzquierdo(false);
                nuevaFicha.setOtorreaBilateral(false);
                nuevaFicha.setPermanenciaOtorreaContinua(false);
                nuevaFicha.setPermanenciaOtorreaIntermitente(false);
                nuevaFicha.setAspectoClaroOtorrea(false);
                nuevaFicha.setAspectoSerosoOtorrea(false);
                nuevaFicha.setAspectoMucosoOtorrea(false);
                nuevaFicha.setAspectoMucopurulentoOtorrea(false);
                nuevaFicha.setAspectoPurulentoOtorrea(false);
                nuevaFicha.setAspectoSanguinolentoOtorrea(false);
                nuevaFicha.setAsosiadaOtorreaInfeccionRespiratoriaAlta(false);
                nuevaFicha.setAsosiadaotorreaInfeccionAgudaOido(false);
                nuevaFicha.setPresentoOtalgia(false);
                nuevaFicha.setPresentoOtalgiaBilateral(false);
                nuevaFicha.setPresentoOtalgiaOidoDerecho(false);
                nuevaFicha.setPresentoOtalgiaOidoIzquierdo(false);
                nuevaFicha.setPresentoSensacionOidoTapado(false);
                nuevaFicha.setPresentoSensacionOidoTapadoBilateral(false);
                nuevaFicha.setPresentoSensacionOidoTapadoOidoDerecho(false);
                nuevaFicha.setPresentoSensacionOidoTapadoOidoIzquierdo(false);
                nuevaFicha.setPresentoAutofonia(false);
                nuevaFicha.setPresentoAutofoniaBilateral(false);
                nuevaFicha.setPresentoAutofoniaOidoDerecho(false);
                nuevaFicha.setPresentoAutofoniaOidoIzquierdo(false);
                nuevaFicha.setPresentoOtorrea(false);
                nuevaFicha.setPresentoOtorreaBilateral(false);
                nuevaFicha.setPresentoOtorreaOidoDerecho(false);
                nuevaFicha.setPresentoOtorreaOidoIzquierdo(false);
                nuevaFicha.setAumentaVolumenTV(false);
                nuevaFicha.setSensacionPercibirTinnitus(false);
                nuevaFicha.setExpuestoRuidosFuertes(false);
                nuevaFicha.setDificultadOidVozBaja(false);
                nuevaFicha.setHablaMasFuerteOMasDespacio(false);
                nuevaFicha.setUtilizaAyudaAuditiva(false);
                nuevaFicha.setPercibeSonidoIgualAmbosOidos(false);
                nuevaFicha.setFaltaEquilibrioCaminar(false);
                nuevaFicha.setMareos(false);
                nuevaFicha.setVertigo(false);
                nuevaFicha.setPerforacionOidoDerecho(false);
                nuevaFicha.setBurbujaOidoDerecho(false);
                nuevaFicha.setPerforacionOidoIzquierdo(false);
                nuevaFicha.setBurbujaOidoIzquierdo(false);

                // Guardar la nueva ficha en la base de datos
                return fonoaudiologiaRepository.save(nuevaFicha);
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