package com.acme.backendimagia.application.dto;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PerfilCiudadanoDTO {

    private Long id;

    @NotNull
    private Long usuarioId;

    private String tipoDeArte;

    private String descripcion;

    private String intereses;

    private String obrasFavoritas;
}
