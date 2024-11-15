package com.test.TUdipsaiApi.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.test.TUdipsaiApi.Model.InstitucionEducativa;
import com.test.TUdipsaiApi.Model.Jornada;
import com.test.TUdipsaiApi.Model.Sede;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class PacienteDTO {
    private Integer id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date fechaApertura;

    private Integer pacienteEstado;
    private String nombresApellidos;
    private String ciudad;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date fechaNacimiento;

    private String edad;
    private String cedula;
    private String domicilio;

    // Cambiado de byte[] a String para manejar Base64
    private String imagen;

    private String telefono;
    private String celular;
    private InstitucionEducativa institucionEducativa;
    private String proyecto;
    private Jornada jornada;
    private String anioEducacion;
    private String paralelo;
    private String perteneceInclusion;
    private String tieneDiscapacidad;
    private boolean portadorCarnet;
    private String diagnostico;
    private String motivoConsulta;
    private String observaciones;
    private String tipoDiscapacidad;
    private String detalleDiscapacidad;
    private Integer porcentajeDiscapacidad;
    private Boolean perteneceAProyecto;
    private DocumentoIdDTO fichaDiagnostica;
    private Sede sede;
    private DocumentoIdDTO fichaCompromiso;

    public void setFichaDiagnosticaId(Long id) {
        this.fichaDiagnostica = new DocumentoIdDTO(id);
    }

    public void setFichaCompromisoId(Long id) {
        this.fichaCompromiso = new DocumentoIdDTO(id);
    }
}
