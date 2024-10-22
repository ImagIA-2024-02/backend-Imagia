package com.acme.backendimagia.application.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PerfilInvestigadorDTO {

    private Long id;

    @NotNull
    private Long usuarioId;

    private String institucion;

    private String gradoAcademico;

    private String areaInvestigacion;

    private String descripcion;

    private String intereses;
}
