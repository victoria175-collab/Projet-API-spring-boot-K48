package com.pkfrc.devises.controller;

import com.pkfrc.devises.dto.ConversionRequestDTO;
import com.pkfrc.devises.dto.ConversionResponseDTO;
import com.pkfrc.devises.service.CurrencyConversionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/currency")
@RequiredArgsConstructor
@Validated
@Tag(name = "Conversion de devises", description = "Conversion de montants entre devises via taux de change en temps réel")
public class CurrencyConversionController {

    private final CurrencyConversionService conversionService;

    @PostMapping("/convert")
    @Operation(summary = "Convertir un montant (corps JSON)")
    public ResponseEntity<ConversionResponseDTO> convert(@Valid @RequestBody ConversionRequestDTO request) {
        return ResponseEntity.ok(conversionService.convert(request));
    }

    @GetMapping("/convert")
    @Operation(summary = "Convertir un montant (paramètres de requête)",
            description = "Exemple : /api/currency/convert?from=USD&to=EUR&amount=100")
    public ResponseEntity<ConversionResponseDTO> convertViaQuery(
            @RequestParam @NotBlank String from,
            @RequestParam @NotBlank String to,
            @RequestParam @Positive Double amount) {

        ConversionRequestDTO request = new ConversionRequestDTO();
        request.setFrom(from);
        request.setTo(to);
        request.setAmount(amount);

        return ResponseEntity.ok(conversionService.convert(request));
    }
}
