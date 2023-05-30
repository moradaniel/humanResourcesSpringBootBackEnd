package org.humanResources.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

@Data
public class RoleDetailsResponseDTO {
    @NotNull
    private Long id;

    @NotBlank
    private String name;

}
