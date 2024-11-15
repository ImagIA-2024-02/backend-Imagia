package com.acme.backendimagia.application.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentProfileDTO {

    private Long id;

    @NotNull
    private Long userId;

    private String institution;

    private String career;

    private String semester;

    private String description;

    private String interests;
}