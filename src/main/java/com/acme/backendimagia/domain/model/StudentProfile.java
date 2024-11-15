package com.acme.backendimagia.domain.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "perfil_estudiante")
public class StudentProfile {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @OneToOne
        @JoinColumn(name = "usuario_id", unique = true, nullable = false)
        private User user;

        @Column(name = "institucion")
        private String institution;

        @Column(name = "carrera")
        private String career;

        @Column(name = "semestre")
        private String semester;

        @Column(name = "descripcion")
        private String description;

        @Column(name = "intereses")
        private String interests;
}
