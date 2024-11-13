package com.test.TUdipsaiApi.Service;

import com.test.TUdipsaiApi.Model.Especialistas;
import com.test.TUdipsaiApi.Model.Especialidad;
import com.test.TUdipsaiApi.Model.Permisos;
import com.test.TUdipsaiApi.Model.Sede;
import com.test.TUdipsaiApi.Repository.EspecialidadRepositorio;
import com.test.TUdipsaiApi.Repository.EspecialistasRepositorio;
import com.test.TUdipsaiApi.Repository.SedeRepositorio;
import com.test.TUdipsaiApi.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EspecialistasService {

    @Autowired
    private EspecialistasRepositorio especialistasRepositorio;

    @Autowired
    private SedeRepositorio sedeRepositorio;

    @Autowired
    private EspecialidadRepositorio especialidadRepositorio;

    @Autowired
    private ImagenServiceEspecialistas imagenService;

    private static final String ROOT_DIRECTORY = System.getProperty("user.home") + "/UdipsaiEspecialistasImagenes/";



    public EspecialistasDTO convertToDTO(Especialistas especialista) throws IOException {
        EspecialistasDTO dto = new EspecialistasDTO();
        dto.setCedula(especialista.getCedula());
        dto.setEspecialistaEstado(especialista.getEspecialistaEstado());
        dto.setPrimerNombre(especialista.getPrimerNombre());
        dto.setSegundoNombre(especialista.getSegundoNombre());
        dto.setPrimerApellido(especialista.getPrimerApellido());
        dto.setSegundoApellido(especialista.getSegundoApellido());
        dto.setContrasena(especialista.getContrasena()); // Incluimos la contraseña

        // Recuperar la imagen desde el archivo comprimido y convertirla a Base64
        String fileName = "imagen-" + especialista.getPrimerNombre().replace(" ", "_") + "-" + especialista.getCedula();
        String imagenBase64;

        try {
            imagenBase64 = imagenService.getDecompressedImage(ROOT_DIRECTORY + fileName + ".txt.gz");
        } catch (FileNotFoundException e) {
            imagenBase64 = null;
        }

        dto.setImagen(imagenBase64);  // Enviar la imagen como Base64 o null

        // Asignar especialidad si existe
        if (especialista.getEspecialidad() != null) {
            EspecialidadDTO especialidadDTO = new EspecialidadDTO();
            especialidadDTO.setId(especialista.getEspecialidad().getId());
            especialidadDTO.setArea(especialista.getEspecialidad().getArea());
            dto.setEspecialidad(especialidadDTO);
        }

        if (especialista.getSede() != null) {
            dto.setSede(especialista.getSede());
        }


        dto.setEsPasante(especialista.getEsPasante());
        dto.setEspecialistaAsignado(especialista.getEspecialistaAsignado());
        dto.setInicioPasantia(especialista.getInicioPasantia());
        dto.setFinPasantia(especialista.getFinPasantia());

        return dto;
    }


    public Optional<Especialistas> updateEspecialista(String cedula, EspecialistasIdDTO updatedEspecialistaDTO) throws IOException {
        Optional<Especialistas> existingEspecialista = especialistasRepositorio.findById(cedula);
        if (existingEspecialista.isPresent()) {
            Especialistas especialista = existingEspecialista.get();
            especialista.setPrimerNombre(updatedEspecialistaDTO.getPrimerNombre());
            especialista.setSegundoNombre(updatedEspecialistaDTO.getSegundoNombre());
            especialista.setPrimerApellido(updatedEspecialistaDTO.getPrimerApellido());
            especialista.setSegundoApellido(updatedEspecialistaDTO.getSegundoApellido());

            if (updatedEspecialistaDTO.getEspecialidadId() != null) {
                Optional<Especialidad> especialidad = especialidadRepositorio.findById(updatedEspecialistaDTO.getEspecialidadId());
                especialidad.ifPresent(especialista::setEspecialidad);
            }

            especialista.setEsPasante(updatedEspecialistaDTO.getEsPasante());
            especialista.setEspecialistaAsignado(updatedEspecialistaDTO.getEspecialistaAsignado());
            especialista.setEspecialistaEstado(updatedEspecialistaDTO.getEspecialistaEstado());
            especialista.setContrasena(updatedEspecialistaDTO.getContrasena());
            especialista.setInicioPasantia(updatedEspecialistaDTO.getInicioPasantia());
            especialista.setFinPasantia(updatedEspecialistaDTO.getFinPasantia());

            // Si hay una imagen en Base64, guardarla en el disco
            if (updatedEspecialistaDTO.getImagen() != null) {
                String fileName = "imagen-" + updatedEspecialistaDTO.getPrimerNombre().replace(" ", "_") + "-" + updatedEspecialistaDTO.getCedula();
                imagenService.saveCompressedImage(updatedEspecialistaDTO.getImagen(), fileName);  // Guardar la imagen comprimida
            }

            // Asignar la sede completa usando solo el ID de la sede
            if (updatedEspecialistaDTO.getSedeId() != null) {
                Optional<Sede> sede = sedeRepositorio.findById(updatedEspecialistaDTO.getSedeId());
                if (sede.isPresent()) {
                    especialista.setSede(sede.get());
                } else {
                    throw new RuntimeException("Sede no encontrada con ID: " + updatedEspecialistaDTO.getSedeId());
                }
            }

            especialistasRepositorio.save(especialista);
            return Optional.of(especialista);
        }
        return Optional.empty();
    }




    public List<Especialistas> findAll() {
        return especialistasRepositorio.findAll();
    }

    public List<Especialistas> findAllActive() {
        return especialistasRepositorio.findAllByEstado();
    }

    public Optional<Especialistas> findByCedula(String cedula) {
        return especialistasRepositorio.findById(cedula);
    }

    public boolean deleteByCedula(String cedula) {
        Optional<Especialistas> especialistaOptional = especialistasRepositorio.findById(cedula);
        if (especialistaOptional.isPresent()) {
            Especialistas especialista = especialistaOptional.get();
            especialista.setEspecialistaEstado(false);
            especialistasRepositorio.save(especialista);
            return true;
        }
        return false;
    }

    public Especialistas login(String cedula, String contrasena) {
        Especialistas especialista = especialistasRepositorio.findByCedula(cedula);
        if (especialista != null && Boolean.TRUE.equals(especialista.getEspecialistaEstado())) {
            if (especialista.getContrasena().equals(contrasena)) {
                return especialista; // Contraseña correcta y sensible a mayúsculas
            }
        }
        return null; // Credenciales incorrectas o especialista inactivo
    }


    public List<Especialistas> findAllActiveNonPasantes() {
        return especialistasRepositorio.findAllByEspecialistaEstadoTrueAndEsPasanteFalse();
    }

    public List<EspecialistasSinImagenDTO> findAllActiveSinImagen() {
        List<Especialistas> especialistas = findAllActive();
        return especialistas.stream()
                .map(this::convertToSinImagenDTO)
                .collect(Collectors.toList());
    }

    // Convertir Especialistas a DTO sin imagen
    public EspecialistasSinImagenDTO convertToSinImagenDTO(Especialistas especialista) {
        EspecialistasSinImagenDTO dto = new EspecialistasSinImagenDTO();
        dto.setCedula(especialista.getCedula());
        dto.setEspecialistaEstado(especialista.getEspecialistaEstado());
        dto.setPrimerNombre(especialista.getPrimerNombre());
        dto.setSegundoNombre(especialista.getSegundoNombre());
        dto.setPrimerApellido(especialista.getPrimerApellido());
        dto.setSegundoApellido(especialista.getSegundoApellido());

        if (especialista.getEspecialidad() != null) {
            EspecialidadDTO especialidadDTO = new EspecialidadDTO();
            especialidadDTO.setId(especialista.getEspecialidad().getId());
            especialidadDTO.setArea(especialista.getEspecialidad().getArea());
            dto.setEspecialidad(especialidadDTO);
        }

        dto.setEsPasante(especialista.getEsPasante());
        dto.setEspecialistaAsignado(especialista.getEspecialistaAsignado());
        dto.setInicioPasantia(especialista.getInicioPasantia());
        dto.setFinPasantia(especialista.getFinPasantia());

        if (especialista.getSede() != null) {
            dto.setSede(especialista.getSede());
        }


        return dto;
    }

    public PermisosDTO convertToPermisosDTO(Permisos permisos) {
        PermisosDTO dto = new PermisosDTO();
        dto.setId(permisos.getId());
        dto.setEspecialistas(permisos.getEspecialistas());
        dto.setInstitucionesEducativas(permisos.getInstitucionesEducativas());
        dto.setHistoriaClinica(permisos.getHistoriaClinica());
        dto.setFonoAudiologia(permisos.getFonoAudiologia());
        dto.setPsicologiaClinica(permisos.getPsicologiaClinica());
        dto.setPsicologiaEducativa(permisos.getPsicologiaEducativa());
        dto.setPacientes(permisos.getPacientes());
        dto.setSede(permisos.getSede());
        return dto;
    }

    public EspecialistasDTO createEspecialista(EspecialistasIdDTO especialistaDTO) throws IOException {
        // Crear un nuevo objeto Especialistas
        Especialistas especialista = new Especialistas();

        // Guardar los datos del especialista
        especialista.setCedula(especialistaDTO.getCedula());
        especialista.setEspecialistaEstado(especialistaDTO.getEspecialistaEstado());
        especialista.setPrimerNombre(especialistaDTO.getPrimerNombre());
        especialista.setSegundoNombre(especialistaDTO.getSegundoNombre());
        especialista.setPrimerApellido(especialistaDTO.getPrimerApellido());
        especialista.setSegundoApellido(especialistaDTO.getSegundoApellido());
        especialista.setEsPasante(especialistaDTO.getEsPasante());
        especialista.setEspecialistaAsignado(especialistaDTO.getEspecialistaAsignado());
        especialista.setContrasena(especialistaDTO.getContrasena());
        especialista.setInicioPasantia(especialistaDTO.getInicioPasantia());
        especialista.setFinPasantia(especialistaDTO.getFinPasantia());

        // Guardar la imagen en la carpeta (si existe en el DTO)
        if (especialistaDTO.getImagen() != null) {
            String fileName = "imagen-" + especialistaDTO.getPrimerNombre().replace(" ", "_") + "-" + especialistaDTO.getCedula();
            imagenService.saveCompressedImage(especialistaDTO.getImagen(), fileName);
        }

        // Asociar la especialidad si está presente
        if (especialistaDTO.getEspecialidadId() != null) {
            Optional<Especialidad> especialidad = especialidadRepositorio.findById(especialistaDTO.getEspecialidadId());
            especialidad.ifPresent(especialista::setEspecialidad);
        }

        // Asociar sede si está presente (buscando por ID)
        if (especialistaDTO.getSedeId() != null) {
            Optional<Sede> sede = sedeRepositorio.findById(especialistaDTO.getSedeId());
            if (sede.isPresent()) {
                especialista.setSede(sede.get());  // Asignar la sede completa
            } else {
                throw new RuntimeException("Sede no encontrada con ID: " + especialistaDTO.getSedeId());
            }
        } else {
            // Si no se proporciona un ID de sede, lanzamos una excepción clara
            throw new IllegalArgumentException("El ID de la sede es obligatorio y no puede ser nulo.");
        }

        // Guardar el especialista en la base de datos
        Especialistas savedEspecialista = especialistasRepositorio.save(especialista);

        // Convertir el especialista guardado a DTO y devolverlo
        return convertToDTO(savedEspecialista);
    }



    public List<EspecialistasSinImagenDTO> findAllPasantesSinImagen() {
        List<Especialistas> pasantes = especialistasRepositorio.findAllPasantes();
        return pasantes.stream()
                .map(this::convertToSinImagenDTO) // Convertir cada especialista a DTO sin imagen
                .collect(Collectors.toList());
    }


}
