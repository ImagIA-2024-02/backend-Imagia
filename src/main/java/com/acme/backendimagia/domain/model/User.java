package com.acme.backendimagia.domain.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "usuarios")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String firstName;
    @Column(name = "apellido", nullable = false, length = 100)
    private String lastName;
    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "telefono", length = 20)
    private String phone;

    @Column(name = "password", nullable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_usuario", nullable = false)
    private UserType userType;

    @Column(name = "creado_en", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "actualizado_en", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "activo", nullable = false)
    private Boolean active;


    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.active = true;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
