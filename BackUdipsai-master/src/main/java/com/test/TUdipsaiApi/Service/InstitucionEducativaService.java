package com.test.TUdipsaiApi.Service;

import com.test.TUdipsaiApi.Model.InstitucionEducativa;
import com.test.TUdipsaiApi.Repository.InstitucionEducativaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InstitucionEducativaService {

    private final InstitucionEducativaRepositorio institucionEducativaRepositorio;

    @Autowired
    public InstitucionEducativaService(InstitucionEducativaRepositorio institucionEducativaRepositorio) {
        this.institucionEducativaRepositorio = institucionEducativaRepositorio;
    }

    public List<InstitucionEducativa> listarInstitucionesActivas() {
        return institucionEducativaRepositorio.findByInstitucionEstado(1);
    }

    public Optional<InstitucionEducativa> obtenerInstitucionPorId(Integer id) {
        return institucionEducativaRepositorio.findById(id);
    }

    public InstitucionEducativa guardarInstitucion(InstitucionEducativa institucionEducativa) {
        return institucionEducativaRepositorio.save(institucionEducativa);
    }

    public void cambiarEstadoInstitucion(Integer id) {
        Optional<InstitucionEducativa> institucionOpt = institucionEducativaRepositorio.findById(id);
        if (institucionOpt.isPresent()) {
            InstitucionEducativa institucion = institucionOpt.get();
            institucion.setInstitucionEstado(0);
            institucionEducativaRepositorio.save(institucion);
        }
    }

    public InstitucionEducativa actualizarInstitucion(Integer id, InstitucionEducativa nuevaInstitucion) {
        Optional<InstitucionEducativa> institucionOpt = institucionEducativaRepositorio.findById(id);
        if (institucionOpt.isPresent()) {
            InstitucionEducativa institucionExistente = institucionOpt.get();
            institucionExistente.setNombreInstitucion(nuevaInstitucion.getNombreInstitucion());
            institucionExistente.setDireccion(nuevaInstitucion.getDireccion());
            institucionExistente.setTipoInstitucion(nuevaInstitucion.getTipoInstitucion());
            institucionExistente.setInstitucionEstado(nuevaInstitucion.getInstitucionEstado());
            return institucionEducativaRepositorio.save(institucionExistente);
        } else {
            throw new RuntimeException("Institución no encontrada");
        }
    }
}
