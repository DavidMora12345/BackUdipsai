package com.test.TUdipsaiApi.Service;

import com.test.TUdipsaiApi.Model.Especialistas;
import com.test.TUdipsaiApi.Model.Permisos;
import com.test.TUdipsaiApi.Repository.EspecialistasRepositorio;
import com.test.TUdipsaiApi.dto.EspecialidadDTO;
import com.test.TUdipsaiApi.dto.EspecialistasSinImagenDTO;
import com.test.TUdipsaiApi.dto.PermisosDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EspecialistasService {

    @Autowired
    private EspecialistasRepositorio especialistasRepositorio;

    public Especialistas saveOrUpdate(Especialistas especialista) {
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

    public Optional<Especialistas> updateEspecialista(String cedula, Especialistas updatedEspecialista) {
        Optional<Especialistas> existingEspecialista = especialistasRepositorio.findById(cedula);
        if (existingEspecialista.isPresent()) {
            Especialistas especialista = existingEspecialista.get();
            especialista.setPrimerNombre(updatedEspecialista.getPrimerNombre());
            especialista.setSegundoNombre(updatedEspecialista.getSegundoNombre());
            especialista.setPrimerApellido(updatedEspecialista.getPrimerApellido());
            especialista.setSegundoApellido(updatedEspecialista.getSegundoApellido());
            especialista.setEspecialidad(updatedEspecialista.getEspecialidad());
            especialista.setEsPasante(updatedEspecialista.getEsPasante());
            especialista.setEspecialistaAsignado(updatedEspecialista.getEspecialistaAsignado());
            especialista.setEspecialistaEstado(updatedEspecialista.getEspecialistaEstado());
            especialista.setContrasena(updatedEspecialista.getContrasena());
            especialista.setInicioPasantia(updatedEspecialista.getInicioPasantia());
            especialista.setFinPasantia(updatedEspecialista.getFinPasantia());
            especialista.setImagen(updatedEspecialista.getImagen());
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
        return dto;
    }
}
