package com.acme.backendimagia.application.dto;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CitizenProfileDTO {

    private Long id;

    @NotNull
    private Long userId;

    private String artType;

    private String description;

    private String interests;

    private String savedArtworks;
}
