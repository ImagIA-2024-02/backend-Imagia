package com.acme.backendimagia.domain.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "perfil_ciudadano")
public class PerfilCiudadano {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "usuario_id", unique = true, nullable = false)
    private Usuario usuario;


    @Column(name = "tipo_de_arte")
    private String tipoDeArte;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "intereses")
    private String intereses;

    @Column(name = "obras_favoritas")
    private String obrasFavoritas;
}
