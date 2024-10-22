package com.acme.backendimagia.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UsuarioDTO {

    private Long id;

    @NotNull
    @Size(min = 3, max = 50)
    private String nombre;

    @NotNull
    @Size(min = 3, max = 50)
    private String apellido;

    @NotNull
    @Email
    private String email;

    @Size(min = 10, max = 20)
    private String telefono;

    @NotNull
    @Size(min = 3, max = 50)
    private String password;

    @NotNull(message = "El tipo de usuario es obligatorio")
    private String tipoUsuario;

}
