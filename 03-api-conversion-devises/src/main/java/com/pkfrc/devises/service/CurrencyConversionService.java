package com.pkfrc.devises.service;

import com.pkfrc.devises.dto.ConversionRequestDTO;
import com.pkfrc.devises.dto.ConversionResponseDTO;

public interface CurrencyConversionService {
    ConversionResponseDTO convert(ConversionRequestDTO request);
}
