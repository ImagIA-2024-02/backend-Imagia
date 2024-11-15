package com.acme.backendimagia.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    private Long id;

    @NotNull
    @Size(min = 3, max = 50)
    private String firstName;

    @NotNull
    @Size(min = 3, max = 50)
    private String lastName;

    @NotNull
    @Email
    private String email;

    @Size(min = 10, max = 20)
    private String phone;

    @NotNull
    @Size(min = 3, max = 50)
    private String password;

    @NotNull(message = "El tipo de usuario es obligatorio")
    private String userType;

}
