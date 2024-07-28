package com.test.TUdipsaiApi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InstitucionEducativaDTO {
    private Integer id;
    private String nombreInstitucion;
    private String direccion;
    private String tipoInstitucion;
    private Integer institucionEstado;
}
