package com.acme.backendimagia.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "obras_de_arte")
public class Artwork {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "autor", nullable = false, length = 100)
    private String author;

    @Column(name = "nombre", length = 100)
    private String name;

    @Column(name = "epoca", length = 100)
    private String epoch;

    @Column(name = "descripcion")
    private String description;

    @Column(name = "img", nullable = false)
    private String img;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private User user;

}
