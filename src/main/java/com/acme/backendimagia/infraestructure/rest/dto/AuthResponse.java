package com.acme.backendimagia.infraestructure.rest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {

        private String token;
        private String tipoUsuario;
        private Long usuarioId;
        private String nombre;
}
