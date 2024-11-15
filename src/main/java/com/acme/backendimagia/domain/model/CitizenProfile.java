package com.acme.backendimagia.domain.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "perfil_ciudadano")
public class CitizenProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "usuario_id", unique = true, nullable = false)
    private User user;


    @Column(name = "tipo_de_arte")
    private String artType;

    @Column(name = "descripcion")
    private String description;

    @Column(name = "intereses")
    private String interests;

    @Column(name = "obras_favoritas")
    private String favoriteArtworks;
}
