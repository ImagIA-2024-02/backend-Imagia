package com.acme.backendimagia.application.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResearcherProfileDTO {

    private Long id;

    @NotNull
    private Long userId;

    private String institution;

    private String academicDegree;

    private String researchArea;

    private String description;

    private String interests;
}
