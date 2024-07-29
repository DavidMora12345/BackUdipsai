package com.test.TUdipsaiApi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PermisosDTO {
    private Long id;
    private Boolean especialistas;
    private Boolean institucionesEducativas;
    private Boolean historiaClinica;
    private Boolean fonoAudiologia;
    private Boolean psicologiaClinica;
    private Boolean psicologiaEducativa;
    private Boolean pacientes;
    private Boolean sede;
}
