package com.test.TUdipsaiApi.Service;

import com.test.TUdipsaiApi.Model.FichaMedica;
import com.test.TUdipsaiApi.Model.Paciente;
import com.test.TUdipsaiApi.Repository.FichaMedicaRepository;
import com.test.TUdipsaiApi.Repository.PacienteRepositorio;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class FichaMedicaService {

    @Autowired
    private FichaMedicaRepository fichaMedicaRepository;

    @Autowired
    private PacienteRepositorio pacienteRepository;

    @Autowired
    private HistorialCambiosService historialCambiosService;

    // Ruta donde se guardarán los genogramas familiares
    private static final String ROOT_DIRECTORY = System.getProperty("user.home") + "/DocumentosUdipsai/GenogramasFamiliares/";

    public List<FichaMedica> obtenerFichasActivas() {
        return fichaMedicaRepository.findByEstado(1);
    }

    public Optional<FichaMedica> obtenerFichaPorId(int id) {
        return fichaMedicaRepository.findById(id);
    }

    public List<FichaMedica> obtenerFichasPorPaciente(int idPaciente) {
        return fichaMedicaRepository.findByPacienteId(idPaciente);
    }

    public FichaMedica crearFichaMedica(FichaMedica fichaMedica, MultipartFile genogramaFamiliar) throws IOException {
        fichaMedica.setEstado(1); // Estado activo por defecto

        // Si se proporciona un archivo de genograma familiar, se guarda en el disco
        if (genogramaFamiliar != null && !genogramaFamiliar.isEmpty()) {
            String genogramaPath = saveGenogramaToLocalDisk(fichaMedica.getPaciente().getNombresApellidos(), fichaMedica.getPaciente().getCedula(), genogramaFamiliar);
            fichaMedica.setGenogramaFamiliar(genogramaPath.getBytes());
        }

        FichaMedica nuevaFicha = fichaMedicaRepository.save(fichaMedica);

        // Registro de cambios
        historialCambiosService.registrarCambio(
                "FichaMedica",
                nuevaFicha.getId().longValue(),
                "CREATE",
                null, // No hay valor anterior en la creación
                nuevaFicha
        );

        return nuevaFicha;
    }

    public FichaMedica actualizarFichaMedica(FichaMedica fichaMedica) throws IOException {
        Optional<FichaMedica> fichaExistente = fichaMedicaRepository.findById(fichaMedica.getId());

        if (fichaExistente.isPresent()) {
            FichaMedica valorAnterior = new FichaMedica(fichaExistente.get());

            // Si se proporciona un nuevo genograma familiar en formato Base64, se decodifica
            if (fichaMedica.getGenogramaFamiliar() != null && fichaMedica.getGenogramaFamiliar().length > 0) {
                // Asumiendo que el genograma ya está en fichaMedica como byte[] (decodificado de Base64)
                fichaMedica.setGenogramaFamiliar(fichaMedica.getGenogramaFamiliar());
            }

            FichaMedica fichaActualizada = fichaMedicaRepository.save(fichaMedica);

            // Registro de cambios
            historialCambiosService.registrarCambio(
                    "FichaMedica",
                    fichaMedica.getId().longValue(),
                    "UPDATE",
                    valorAnterior,
                    fichaActualizada
            );

            return fichaActualizada;
        } else {
            throw new RuntimeException("Ficha médica no encontrada");
        }
    }

    public void eliminarFichaMedica(int id) {
        fichaMedicaRepository.findById(id).ifPresent(ficha -> {
            ficha.setEstado(0); // Cambiar estado a inactivo en lugar de eliminar
            fichaMedicaRepository.save(ficha);
        });
    }

    public FichaMedica obtenerFichaPorIdPaciente(int idPaciente) {
        List<FichaMedica> fichasMedicas = fichaMedicaRepository.findByPacienteId(idPaciente);

        if (fichasMedicas.isEmpty()) {
            Optional<Paciente> paciente = pacienteRepository.findById(idPaciente);
            if (paciente.isPresent()) {
                FichaMedica nuevaFicha = new FichaMedica();
                nuevaFicha.setPaciente(paciente.get());
                nuevaFicha.setEstado(1); // Estado activo

                // Inicializar otros campos si es necesario
                inicializarCamposFichaMedica(nuevaFicha);

                FichaMedica fichaGuardada = fichaMedicaRepository.save(nuevaFicha);

                // Registro de cambios en la creación de la ficha asociada al paciente
                historialCambiosService.registrarCambio(
                        "FichaMedica",
                        fichaGuardada.getId().longValue(),
                        "CREATE",
                        null,
                        fichaGuardada
                );

                return fichaGuardada;
            } else {
                // Manejar el caso donde el paciente no existe
                return null;
            }
        } else {
            return fichasMedicas.get(0); // Devolver la ficha existente
        }
    }

    // Método para inicializar campos de la ficha médica
    private void inicializarCamposFichaMedica(FichaMedica fichaMedica) {
        fichaMedica.setVivenJuntos(false);
        fichaMedica.setEmbarazoDeseado(false);
        fichaMedica.setControlEmbarazo(false);
        fichaMedica.setPresentoAmenazaAborto(false);
        fichaMedica.setLloroAlNacer(false);
        fichaMedica.setPresentoAnoxiaAlNacer(false);
        fichaMedica.setPresentoHipoxiaAlNacer(false);
        fichaMedica.setPresentoIctericiaAlNacer(false);
        fichaMedica.setPresentoCianosisAlNacer(false);
        fichaMedica.setComplicacionesEnElParto(false);
        fichaMedica.setEstuvoEnIncubadora(false);
        fichaMedica.setEsquemaVacunacionCompleto(false);
    }

    // Método para guardar el genograma familiar en el disco
    private String saveGenogramaToLocalDisk(String nombres, String cedula, MultipartFile genograma) throws IOException {
        // Crear el directorio si no existe
        File directory = new File(ROOT_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs(); // Crear la carpeta si no existe
        }

        // Generar un hash único basado en el contenido del genograma
        String hash = DigestUtils.sha256Hex(genograma.getBytes());

        // Obtener la extensión del archivo
        String extension = getExtension(genograma.getOriginalFilename());

        // Crear el nombre del archivo en el formato especificado
        String fileName = "genograma-" + nombres.replace(" ", "_") + "-" + cedula + "-" + hash + "." + extension;

        // Ruta completa donde se guardará el genograma
        String filePath = ROOT_DIRECTORY + fileName;

        // Guardar el archivo en el disco
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(genograma.getBytes());
        }

        return filePath;
    }

    // Método para extraer la extensión de un archivo
    private String getExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }
}
