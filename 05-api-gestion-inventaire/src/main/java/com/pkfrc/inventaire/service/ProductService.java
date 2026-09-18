package com.pkfrc.inventaire.service;

import com.pkfrc.inventaire.dto.ProductRequestDTO;
import com.pkfrc.inventaire.dto.ProductResponseDTO;

import java.util.List;

public interface ProductService {
    ProductResponseDTO create(ProductRequestDTO request);
    List<ProductResponseDTO> findAll();
    ProductResponseDTO findById(Long id);
    ProductResponseDTO update(Long id, ProductRequestDTO request);
    void delete(Long id);
    List<ProductResponseDTO> findLowStock(int threshold);
}
