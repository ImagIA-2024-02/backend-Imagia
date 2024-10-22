package com.acme.backendimagia.infraestructure.rest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String email;
    private String password;
    private String nombre;
    private String apellido;
    private String telefono;
    private String tipoUsuario;


}
