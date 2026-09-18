package com.pkfrc.devises.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ConversionRequestDTO {

    @NotBlank(message = "La devise source est obligatoire")
    private String from;

    @NotBlank(message = "La devise cible est obligatoire")
    private String to;

    @NotNull(message = "Le montant est obligatoire")
    @DecimalMin(value = "0.0", inclusive = false, message = "Le montant doit être supérieur à 0")
    private Double amount;
}
