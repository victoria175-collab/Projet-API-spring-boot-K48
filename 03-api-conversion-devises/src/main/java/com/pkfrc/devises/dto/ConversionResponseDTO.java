package com.pkfrc.devises.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConversionResponseDTO {
    private String from;
    private String to;
    private Double amount;
    private Double rate;
    private Double convertedAmount;
}
