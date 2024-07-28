package com.test.TUdipsaiApi.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "documento")
@Getter
@Setter
public class Documento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "contenido", columnDefinition = "BLOB")
    private byte[] contenido;
}
