package com.pkfrc.inventaire.controller;

import com.pkfrc.inventaire.dto.ProductRequestDTO;
import com.pkfrc.inventaire.dto.ProductResponseDTO;
import com.pkfrc.inventaire.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Inventaire", description = "Gestion d'un inventaire de produits avec suivi des stocks")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @Operation(summary = "Créer un produit")
    public ResponseEntity<ProductResponseDTO> create(@Valid @RequestBody ProductRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.create(request));
    }

    @GetMapping
    @Operation(summary = "Lister tous les produits")
    public ResponseEntity<List<ProductResponseDTO>> findAll() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un produit par id")
    public ResponseEntity<ProductResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un produit (prix, quantité, nom)")
    public ResponseEntity<ProductResponseDTO> update(@PathVariable Long id,
                                                        @Valid @RequestBody ProductRequestDTO request) {
        return ResponseEntity.ok(productService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un produit")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/low-stock")
    @Operation(summary = "Lister les produits en stock bas",
            description = "Seuil par défaut : 5 unités. Un seuil personnalisé peut être fourni.")
    public ResponseEntity<List<ProductResponseDTO>> findLowStock(
            @RequestParam(required = false, defaultValue = "5") int threshold) {
        return ResponseEntity.ok(productService.findLowStock(threshold));
    }
}
