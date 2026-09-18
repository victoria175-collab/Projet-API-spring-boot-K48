package com.pkfrc.blog.controller;

import com.pkfrc.blog.dto.*;
import com.pkfrc.blog.service.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
@Tag(name = "Blog", description = "Gestion des articles et des commentaires")
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping
    @Operation(summary = "Créer un article")
    public ResponseEntity<ArticleResponseDTO> create(@Valid @RequestBody ArticleRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(articleService.create(request));
    }

    @GetMapping
    @Operation(summary = "Lister tous les articles")
    public ResponseEntity<List<ArticleResponseDTO>> findAll() {
        return ResponseEntity.ok(articleService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un article spécifique (avec ses commentaires)")
    public ResponseEntity<ArticleResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(articleService.findById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un article")
    public ResponseEntity<ArticleResponseDTO> update(@PathVariable Long id,
                                                        @Valid @RequestBody ArticleRequestDTO request) {
        return ResponseEntity.ok(articleService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un article")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        articleService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/comments")
    @Operation(summary = "Ajouter un commentaire à un article")
    public ResponseEntity<CommentResponseDTO> addComment(@PathVariable Long id,
                                                            @Valid @RequestBody CommentRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(articleService.addComment(id, request));
    }
}
