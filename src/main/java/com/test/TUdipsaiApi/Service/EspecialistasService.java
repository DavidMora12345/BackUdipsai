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

    public Especialistas saveOrUpdate(EspecialistasIdDTO especialistaDTO) {
        Especialistas especialista = new Especialistas();
        especialista.setCedula(especialistaDTO.getCedula());
        especialista.setEspecialistaEstado(especialistaDTO.getEspecialistaEstado());
        especialista.setPrimerNombre(especialistaDTO.getPrimerNombre());
        especialista.setSegundoNombre(especialistaDTO.getSegundoNombre());
        especialista.setPrimerApellido(especialistaDTO.getPrimerApellido());
        especialista.setSegundoApellido(especialistaDTO.getSegundoApellido());

        if (especialistaDTO.getEspecialidadId() != null) {
            Optional<Especialidad> especialidad = especialidadRepositorio.findById(especialistaDTO.getEspecialidadId());
            if (especialidad.isPresent()) {
                especialista.setEspecialidad(especialidad.get());
            } else {
                throw new RuntimeException("Especialidad no encontrada con ID: " + especialistaDTO.getEspecialidadId());
            }
        }

        especialista.setEsPasante(especialistaDTO.getEsPasante());
        especialista.setEspecialistaAsignado(especialistaDTO.getEspecialistaAsignado());
        especialista.setContrasena(especialistaDTO.getContrasena());
        especialista.setInicioPasantia(especialistaDTO.getInicioPasantia());
        especialista.setFinPasantia(especialistaDTO.getFinPasantia());
        especialista.setImagen(especialistaDTO.getImagen());

        if (especialistaDTO.getSede() != null) {
            Optional<Sede> sede = sedeRepositorio.findById(especialistaDTO.getSede());
            sede.ifPresent(especialista::setSede);
        }

        return especialistasRepositorio.save(especialista);
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
        return especialistasRepositorio.findByCedulaAndContrasena(cedula, contrasena);
    }

    public List<Especialistas> findAllActiveNonPasantes() {
        return especialistasRepositorio.findAllByEspecialistaEstadoTrueAndEsPasanteFalse();
    }

    public Optional<Especialistas> updateEspecialista(String cedula, EspecialistasIdDTO updatedEspecialistaDTO) {
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
            especialista.setImagen(updatedEspecialistaDTO.getImagen());

            if (updatedEspecialistaDTO.getSede() != null) {
                Optional<Sede> sede = sedeRepositorio.findById(updatedEspecialistaDTO.getSede());
                sede.ifPresent(especialista::setSede);
            } else {
                especialista.setSede(null);
            }

            especialistasRepositorio.save(especialista);
            return Optional.of(especialista);
        }
        return Optional.empty();
    }



    public List<EspecialistasSinImagenDTO> findAllActiveSinImagen() {
        List<Especialistas> especialistas = findAllActive();
        return especialistas.stream()
                .map(this::convertToSinImagenDTO)
                .collect(Collectors.toList());
    }

    public EspecialistasSinImagenDTO convertToSinImagenDTO(Especialistas especialista) {
        EspecialistasSinImagenDTO dto = new EspecialistasSinImagenDTO();
        dto.setCedula(especialista.getCedula());
        dto.setEspecialistaEstado(especialista.getEspecialistaEstado());
        dto.setPrimerNombre(especialista.getPrimerNombre());
        dto.setSegundoNombre(especialista.getSegundoNombre());
        dto.setPrimerApellido(especialista.getPrimerApellido());
        dto.setSegundoApellido(especialista.getSegundoApellido());

        EspecialidadDTO especialidadDTO = new EspecialidadDTO();
        especialidadDTO.setId(especialista.getEspecialidad().getId());
        especialidadDTO.setArea(especialista.getEspecialidad().getArea());

        Permisos permisos = especialista.getEspecialidad().getPermisos();
        if (permisos != null) {
            PermisosDTO permisosDTO = convertToPermisosDTO(permisos);
            especialidadDTO.setPermisos(permisosDTO);
        } else {
            especialidadDTO.setPermisos(null);
        }

        dto.setEspecialidad(especialidadDTO);
        dto.setEsPasante(especialista.getEsPasante());
        dto.setEspecialistaAsignado(especialista.getEspecialistaAsignado());
        dto.setInicioPasantia(especialista.getInicioPasantia());
        dto.setFinPasantia(especialista.getFinPasantia());

        if (especialista.getSede() != null) {
            dto.setSede(especialista.getSede().getId());
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

    public List<Especialistas> findAllPasantes() {
        return especialistasRepositorio.findAllPasantes();
    }

    public List<EspecialistasSinImagenDTO> findAllPasantesSinImagen() {
        List<Especialistas> pasantes = findAllPasantes();
        return pasantes.stream()
                .map(this::convertToSinImagenDTO)
                .collect(Collectors.toList());
    }
}
