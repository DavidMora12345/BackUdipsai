package com.test.TUdipsaiApi.Service;

import com.test.TUdipsaiApi.Model.PsicologiaClinica;
import com.test.TUdipsaiApi.Model.Paciente;
import com.test.TUdipsaiApi.Repository.PsicologiaClinicaRepository;
import com.test.TUdipsaiApi.Repository.PacienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PsicologiaClinicaService {

    @Autowired
    private PsicologiaClinicaRepository psicologiaClinicaRepository;

    @Autowired
    private PacienteRepositorio pacienteRepository;

    @Autowired
    private HistorialCambiosService historialCambiosService;

    public List<PsicologiaClinica> obtenerFichasActivas() {
        return psicologiaClinicaRepository.findByEstado(1);
    }

    public Optional<PsicologiaClinica> obtenerFichaPorId(int id) {
        return psicologiaClinicaRepository.findById(id);
    }

    public List<PsicologiaClinica> obtenerFichasPorPaciente(int idPaciente) {
        return psicologiaClinicaRepository.findByPacienteId(idPaciente);
    }

    public PsicologiaClinica crearFicha(PsicologiaClinica psicologiaClinica) {
        psicologiaClinica.setEstado(1); // Estado activo por defecto
        PsicologiaClinica nuevaFicha = psicologiaClinicaRepository.save(psicologiaClinica);

        // Registro de cambios
        historialCambiosService.registrarCambio(
                "PsicologiaClinica",
                nuevaFicha.getId().longValue(),
                "CREATE",
                null, // No hay valor anterior en la creación
                nuevaFicha
        );

        return nuevaFicha;
    }

    public PsicologiaClinica actualizarFicha(PsicologiaClinica psicologiaClinica) {
        Optional<PsicologiaClinica> fichaExistente = psicologiaClinicaRepository.findById(psicologiaClinica.getId());
        if (fichaExistente.isPresent()) {
            PsicologiaClinica valorAnterior = new PsicologiaClinica(fichaExistente.get());

            PsicologiaClinica fichaActualizada = psicologiaClinicaRepository.save(psicologiaClinica);

            // Registro de cambios
            historialCambiosService.registrarCambio(
                    "PsicologiaClinica",
                    psicologiaClinica.getId().longValue(),
                    "UPDATE",
                    valorAnterior,
                    fichaActualizada
            );

            return fichaActualizada;
        } else {
            throw new RuntimeException("Ficha médica no encontrada");
        }
    }

    public void eliminarFicha(int id) {
        psicologiaClinicaRepository.findById(id).ifPresent(ficha -> {
            ficha.setEstado(0); // Cambiar estado a inactivo en lugar de eliminar
            psicologiaClinicaRepository.save(ficha);
        });
    }

    public PsicologiaClinica obtenerFichaPorIdPaciente(int idPaciente) {
        List<PsicologiaClinica> fichas = psicologiaClinicaRepository.findByPacienteId(idPaciente);

        if (fichas.isEmpty()) {
            Optional<Paciente> paciente = pacienteRepository.findById(idPaciente);
            if (paciente.isPresent()) {
                PsicologiaClinica nuevaFicha = new PsicologiaClinica();
                nuevaFicha.setPaciente(paciente.get());
                nuevaFicha.setEstado(1); // Estado activo

                // Establecer los valores booleanos a false
                establecerValoresBooleanosPorDefecto(nuevaFicha);

                PsicologiaClinica fichaGuardada = psicologiaClinicaRepository.save(nuevaFicha);

                // Registro de cambios en la creación de la ficha asociada al paciente
                historialCambiosService.registrarCambio(
                        "PsicologiaClinica",
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
            return fichas.get(0); // Devolver la ficha existente
        }
    }

    private void establecerValoresBooleanosPorDefecto(PsicologiaClinica psicologiaClinica) {
        psicologiaClinica.setHipersomnia(false);
        psicologiaClinica.setDificultadDeConciliarElSuenio(false);
        psicologiaClinica.setDespertarFrecuente(false);
        psicologiaClinica.setDespertarPrematuro(false);
        psicologiaClinica.setSonambulismo(false);
        psicologiaClinica.setTemores(false);
        psicologiaClinica.setDestructividad(false);
        psicologiaClinica.setNerviosismo(false);
        psicologiaClinica.setIrritabilidad(false);
        psicologiaClinica.setEgocentrismo(false);
        psicologiaClinica.setRegresiones(false);
        psicologiaClinica.setTics(false);
        psicologiaClinica.setHurto(false);
        psicologiaClinica.setMentira(false);
        psicologiaClinica.setCuidadoPersonal(false);
        psicologiaClinica.setPalabrasRaras(false);
        psicologiaClinica.setLogicoYClaro(false);
        psicologiaClinica.setVozMonotona(false);
        psicologiaClinica.setMalHablado(false);
        psicologiaClinica.setLentoYTeatral(false);
        psicologiaClinica.setPesimista(false);
        psicologiaClinica.setHiriente(false);
        psicologiaClinica.setCharlatan(false);
        psicologiaClinica.setIncoherente(false);
        psicologiaClinica.setVerborrea(false);
        psicologiaClinica.setAbatimiento(false);
        psicologiaClinica.setTension(false);
        psicologiaClinica.setPerplejidad(false);
        psicologiaClinica.setSuspicacia(false);
        psicologiaClinica.setEnfado(false);
        psicologiaClinica.setPreocupacion(false);
        psicologiaClinica.setObscenidad(false);
        psicologiaClinica.setDisartria(false);
        psicologiaClinica.setAfasiaExpresiva(false);
        psicologiaClinica.setAfasiaReceptiva(false);
        psicologiaClinica.setAfasiaAnomica(false);
        psicologiaClinica.setAfasiaGlobal(false);
        psicologiaClinica.setEcolalia(false);
        psicologiaClinica.setPalilalia(false);
        psicologiaClinica.setEnsimismamiento(false);
        psicologiaClinica.setHayQueGuiarlo(false);
        psicologiaClinica.setMolestoso(false);
        psicologiaClinica.setLento(false);
        psicologiaClinica.setNoDeseaHacerNada(false);
        psicologiaClinica.setHaceCosasExtranas(false);
        psicologiaClinica.setAislado(false);
        psicologiaClinica.setParticipaEnGrupos(false);
        psicologiaClinica.setEsViolento(false);
        psicologiaClinica.setCallado(false);
        psicologiaClinica.setAmigableYCooperador(false);
        psicologiaClinica.setAdaptable(false);
        psicologiaClinica.setInquieto(false);
        psicologiaClinica.setNervioso(false);
        psicologiaClinica.setTieneAmigosIntimos(false);
        psicologiaClinica.setConfuso(false);
        psicologiaClinica.setCentradoEnSiMismo(false);
        psicologiaClinica.setOlvidadizo(false);
        psicologiaClinica.setPiensaYRespondeBien(false);
        psicologiaClinica.setPocosPensamientos(false);
        psicologiaClinica.setNoVeLosErrores(false);
        psicologiaClinica.setActuaInfaltilmente(false);
        psicologiaClinica.setDesconfia(false);
        psicologiaClinica.setHosco(false);
        psicologiaClinica.setFastidiado(false);
        psicologiaClinica.setCansado(false);
        psicologiaClinica.setVisteRaramente(false);
        psicologiaClinica.setDesordenado(false);
        psicologiaClinica.setMugrosoYFachoso(false);
        psicologiaClinica.setExcesoDeRopas(false);
        psicologiaClinica.setDramaticoYTeatral(false);
        psicologiaClinica.setVisteNormalmente(false);
        psicologiaClinica.setImpecable(false);
        psicologiaClinica.setDudaDeTodos(false);
        psicologiaClinica.setPasaAislado(false);
        psicologiaClinica.setDiceEstarBien(false);
        psicologiaClinica.setGustaDeHacerDanoALosDemas(false);
        psicologiaClinica.setTieneIniciativas(false);
        psicologiaClinica.setColabora(false);
        psicologiaClinica.setReticencia(false);
        psicologiaClinica.setRechazo(false);
        psicologiaClinica.setMutismo(false);
        psicologiaClinica.setNegativismo(false);
        psicologiaClinica.setAgresividad(false);
        psicologiaClinica.setSarcasmo(false);
        psicologiaClinica.setPegajosidad(false);
        psicologiaClinica.setColaboracionExcesiva(false);
        psicologiaClinica.setAtento(false);
        psicologiaClinica.setSeductor(false);
        psicologiaClinica.setEvitaConversar(false);
        psicologiaClinica.setImpulsivo(false);
        psicologiaClinica.setBromista(false);
        psicologiaClinica.setToscoYDescortes(false);
        psicologiaClinica.setTriste(false);
        psicologiaClinica.setIrritable(false);
        psicologiaClinica.setPopensoARinias(false);
        psicologiaClinica.setSuaveYAfable(false);
        psicologiaClinica.setIndiferente(false);
        psicologiaClinica.setPreocupadoYPensativo(false);
        psicologiaClinica.setTendenciaAlLlanto(false);
        psicologiaClinica.setAlegre(false);
        psicologiaClinica.setEuforico(false);
        psicologiaClinica.setLabilDeHumor(false);
        psicologiaClinica.setInactivo(false);
        psicologiaClinica.setPerezoso(false);
        psicologiaClinica.setSoloHaceCosasIndispensables(false);
        psicologiaClinica.setRealizaSoloUnTipoDeTrabajo(false);
        psicologiaClinica.setDedicadoAVariasActividades(false);
        psicologiaClinica.setApraxia(false);
        psicologiaClinica.setCatatonia(false);
        psicologiaClinica.setAgitacion(false);
        psicologiaClinica.setAmaneramiento(false);
        psicologiaClinica.setEstereotipias(false);
        psicologiaClinica.setEcopraxia(false);
        psicologiaClinica.setObedienciaAutomatica(false);
        psicologiaClinica.setNegativismoActividades(false);
        psicologiaClinica.setInterceptacionMotriz(false);
        psicologiaClinica.setDispraxias(false);
        psicologiaClinica.setActosImpulsivos(false);
        psicologiaClinica.setActosObsesivos(false);
        psicologiaClinica.setTicsActividades(false);
        psicologiaClinica.setLiderazgo(false);
        psicologiaClinica.setSociabilidad(false);
        psicologiaClinica.setResponsabilidad(false);
        psicologiaClinica.setToleranciaNormal(false);
        psicologiaClinica.setBaja(false);
        psicologiaClinica.setColaboracion(false);
        psicologiaClinica.setInquietud(false);
        psicologiaClinica.setAcataOrdenesVerbales(false);
        psicologiaClinica.setAgresivo(false);
        psicologiaClinica.setExtravagante(false);
        psicologiaClinica.setAntisocial(false);
        psicologiaClinica.setImpulsivoComportamiento(false);
        psicologiaClinica.setReflexivo(false);
        psicologiaClinica.setPasivo(false);
        psicologiaClinica.setApatico(false);
        psicologiaClinica.setDependiente(false);
        psicologiaClinica.setDominante(false);
        psicologiaClinica.setCauteloso(false);
        psicologiaClinica.setQuejoso(false);
        psicologiaClinica.setTemeroso(false);
        psicologiaClinica.setTeatral(false);
        psicologiaClinica.setRitualista(false);
        psicologiaClinica.setAislamiento(false);
        psicologiaClinica.setAtaquesDePanico(false);
        psicologiaClinica.setIncapacidadDeRealizacionDeActividadesProductivas(false);
        psicologiaClinica.setRiesgoPotencialOPotencialSuicida(false);
        psicologiaClinica.setInhibicion(false);
        psicologiaClinica.setApatia(false);
        psicologiaClinica.setHumorVariable(false);
        psicologiaClinica.setAltaSensibilidad(false);
        psicologiaClinica.setAgresividadAfectividad(false);
        psicologiaClinica.setSumision(false);
        psicologiaClinica.setRabietas(false);
        psicologiaClinica.setSolidaridad(false);
        psicologiaClinica.setGenerosidad(false);
        psicologiaClinica.setAfectuoso(false);
        psicologiaClinica.setAngustia(false);
        psicologiaClinica.setAnsiedadSituacional(false);
        psicologiaClinica.setTimidez(false);
        psicologiaClinica.setAnsiedadExpectante(false);
        psicologiaClinica.setDepresion(false);
        psicologiaClinica.setPerdidaRecienteDeInteres(false);
        psicologiaClinica.setDesesperacion(false);
        psicologiaClinica.setEuforia(false);
        psicologiaClinica.setIndiferencia(false);
        psicologiaClinica.setAplanamiento(false);
        psicologiaClinica.setAmbivalencia(false);
        psicologiaClinica.setIrritabilidadAfectividad(false);
        psicologiaClinica.setLabilidad(false);
        psicologiaClinica.setTenacidad(false);
        psicologiaClinica.setIncontinencia(false);
        psicologiaClinica.setSentimientosInadecuados(false);
        psicologiaClinica.setNeotimia(false);
        psicologiaClinica.setDisociacionIdeoAfectiva(false);
        psicologiaClinica.setAnhedonia(false);
        psicologiaClinica.setLucidez(false);
        psicologiaClinica.setObnubilacion(false);
        psicologiaClinica.setEstupor(false);
        psicologiaClinica.setComa(false);
        psicologiaClinica.setHipervigilancia(false);
        psicologiaClinica.setConfusion(false);
        psicologiaClinica.setEstadoCrepuscular(false);
        psicologiaClinica.setOnirismo(false);
        psicologiaClinica.setSonambulismoEstadoDeConciencia(false);
        psicologiaClinica.setHipercepcion(false);
        psicologiaClinica.setHipoprosexia(false);
        psicologiaClinica.setDisprosexia(false);
        psicologiaClinica.setDistraibilidad(false);
        psicologiaClinica.setSinAlteracion(false);
        psicologiaClinica.setHipercepcionSensopercepcion(false);
        psicologiaClinica.setIlusiones(false);
        psicologiaClinica.setSeudoalucionciones(false);
        psicologiaClinica.setAlusinosis(false);
        psicologiaClinica.setMacropsias(false);
        psicologiaClinica.setMicropsias(false);
        psicologiaClinica.setNoPresenta(false);
        psicologiaClinica.setAlucinaiones(false);
        psicologiaClinica.setHipermnecia(false);
        psicologiaClinica.setAmnesiaDeFijacion(false);
        psicologiaClinica.setAmnesiaDeEvocacion(false);
        psicologiaClinica.setMixta(false);
        psicologiaClinica.setLacunar(false);
        psicologiaClinica.setDismensia(false);
        psicologiaClinica.setParamnesias(false);
        psicologiaClinica.setSinAlteracionMemoria(false);
        psicologiaClinica.setEnlentecimiento(false);
        psicologiaClinica.setExcitacionPsicomotriz(false);
        psicologiaClinica.setCatatoniaConductaMotora(false);
        psicologiaClinica.setActitudesAnormales(false);
        psicologiaClinica.setAlteracionesDeLaMarcha(false);
        psicologiaClinica.setInquietudConductaMotora(false);
    }
}