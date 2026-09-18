package com.pkfrc.inventaire.service;

import com.pkfrc.inventaire.dto.ProductRequestDTO;
import com.pkfrc.inventaire.dto.ProductResponseDTO;
import com.pkfrc.inventaire.entity.Product;
import com.pkfrc.inventaire.exception.ResourceNotFoundException;
import com.pkfrc.inventaire.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private static final int DEFAULT_LOW_STOCK_THRESHOLD = 5;

    private final ProductRepository productRepository;

    @Override
    public ProductResponseDTO create(ProductRequestDTO request) {
        Product product = Product.builder()
                .name(request.getName())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .build();
        return toResponse(productRepository.save(product));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> findAll() {
        return productRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponseDTO findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    @Override
    public ProductResponseDTO update(Long id, ProductRequestDTO request) {
        Product product = getOrThrow(id);
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
        return toResponse(productRepository.save(product));
    }

    @Override
    public void delete(Long id) {
        Product product = getOrThrow(id);
        productRepository.delete(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponseDTO> findLowStock(int threshold) {
        int effectiveThreshold = threshold > 0 ? threshold : DEFAULT_LOW_STOCK_THRESHOLD;
        return productRepository.findByQuantityLessThan(effectiveThreshold).stream()
                .map(this::toResponse)
                .toList();
    }

    private Product getOrThrow(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produit introuvable avec l'id : " + id));
    }

    private ProductResponseDTO toResponse(Product product) {
        return ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .lowStock(product.getQuantity() < DEFAULT_LOW_STOCK_THRESHOLD)
                .updatedAt(product.getUpdatedAt())
                .build();
    }
}
