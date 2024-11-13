package com.test.TUdipsaiApi.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DocumentoDTO {
    private Long id;
    private String url;

    // Constructor que acepta un URL
    public DocumentoDTO(Long id, String url) {
        this.id = id;
        this.url = url;
    }

    // Constructor vacío
    public DocumentoDTO() {
    }
}
