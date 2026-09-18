package com.pkfrc.devises.service;

import com.pkfrc.devises.client.ExchangeRateApiResponse;
import com.pkfrc.devises.dto.ConversionRequestDTO;
import com.pkfrc.devises.dto.ConversionResponseDTO;
import com.pkfrc.devises.exception.ExternalApiException;
import com.pkfrc.devises.exception.InvalidCurrencyException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class CurrencyConversionServiceImpl implements CurrencyConversionService {

    private final WebClient exchangeRateWebClient;

    @Override
    public ConversionResponseDTO convert(ConversionRequestDTO request) {
        String from = normalize(request.getFrom());
        String to = normalize(request.getTo());

        validateCurrencyFormat(from);
        validateCurrencyFormat(to);

        ExchangeRateApiResponse response = fetchRates(from);

        Map<String, Double> rates = response.getRates();
        if (rates == null || !rates.containsKey(to)) {
            throw new InvalidCurrencyException("Devise cible inconnue ou non supportée : " + to);
        }

        double rate = rates.get(to);
        double convertedAmount = round(request.getAmount() * rate);

        return ConversionResponseDTO.builder()
                .from(from)
                .to(to)
                .amount(request.getAmount())
                .rate(rate)
                .convertedAmount(convertedAmount)
                .build();
    }

    private ExchangeRateApiResponse fetchRates(String baseCurrency) {
        try {
            ExchangeRateApiResponse response = exchangeRateWebClient.get()
                    .uri("/v6/latest/{base}", baseCurrency)
                    .retrieve()
                    .onStatus(status -> status.value() == 404,
                            clientResponse -> {
                                throw new InvalidCurrencyException("Devise source inconnue : " + baseCurrency);
                            })
                    .bodyToMono(ExchangeRateApiResponse.class)
                    .block();

            if (response == null || !"success".equalsIgnoreCase(response.getResult())) {
                throw new ExternalApiException("Réponse invalide du fournisseur de taux de change");
            }
            return response;
        } catch (InvalidCurrencyException | ExternalApiException ex) {
            throw ex;
        } catch (WebClientResponseException ex) {
            log.error("Erreur API externe de taux de change : {}", ex.getMessage());
            throw new ExternalApiException("Erreur lors de l'appel à l'API externe de taux de change");
        } catch (Exception ex) {
            log.error("Impossible de contacter l'API externe de taux de change", ex);
            throw new ExternalApiException("Impossible de contacter l'API externe de taux de change", ex);
        }
    }

    private void validateCurrencyFormat(String code) {
        if (code == null || code.length() != 3) {
            throw new InvalidCurrencyException("Code de devise invalide (format ISO 4217 attendu, ex: USD) : " + code);
        }
    }

    private String normalize(String code) {
        return code == null ? null : code.trim().toUpperCase();
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }
}
