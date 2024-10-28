package com.acme.backendimagia.application.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ObraDeArteDTO {
    private Long id;

    @NotNull
    @Size(min=4, max = 100)
    private String autor;

    @Size(min=4, max = 100)
    private String nombre;

    @Size(min=4, max = 100)
    private String epoca;

    @Size(min=4, max = 100)
    private String descripcion;

    @Size(min=4, max = 100)
    private String img;

    @NotNull
    private Long usuarioId;
}
