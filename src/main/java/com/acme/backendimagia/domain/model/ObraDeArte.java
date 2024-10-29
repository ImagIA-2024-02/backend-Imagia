package com.acme.backendimagia.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "obras_de_arte")
public class ObraDeArte {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String autor;

    @Column(length = 100)
    private String nombre;

    @Column(length = 100)
    private String epoca;

    @Column
    private String descripcion;

    @Column(nullable = false)
    private String img;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

}
