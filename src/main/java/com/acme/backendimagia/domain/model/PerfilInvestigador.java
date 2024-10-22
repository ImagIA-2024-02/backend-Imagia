package com.acme.backendimagia.domain.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "perfil_investigador")
public class PerfilInvestigador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "usuario_id", unique = true, nullable = false)
    private Usuario usuario;

    @Column(name = "institucion")
    private String institucion;

    @Column(name = "grado_academico")
    private String gradoAcademico;

    @Column(name = "area_investigacion")
    private String areaInvestigacion;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "intereses")
    private String intereses;



}
