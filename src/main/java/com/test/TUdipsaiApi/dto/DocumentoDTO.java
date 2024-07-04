package com.test.TUdipsaiApi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentoDTO {
    private Long id;
    private byte[] contenido;


    public DocumentoDTO(Long id, byte[] contenido) {
        this.id = id;
        this.contenido = contenido;
    }

    public DocumentoDTO() {
    }
}
