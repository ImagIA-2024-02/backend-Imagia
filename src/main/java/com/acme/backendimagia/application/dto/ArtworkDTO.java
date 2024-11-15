package com.acme.backendimagia.application.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArtworkDTO {
    private Long id;

    @NotNull
    @Size(min=4, max = 100)
    private String author;

    @Size(min=4, max = 100)
    private String name;

    @Size(min=4, max = 100)
    private String epoch;

    @Size(min=4, max = 100)
    private String description;

    @Size(min=4, max = 100)
    private String img;

    @NotNull
    private Long userId;
}
