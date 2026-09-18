package com.pkfrc.devises.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExchangeRateApiResponse {
    private String result;
    private String baseCode;
    private Map<String, Double> rates;
}
