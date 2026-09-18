package com.pkfrc.taches.controller;

import com.pkfrc.taches.dto.TaskRequestDTO;
import com.pkfrc.taches.dto.TaskResponseDTO;
import com.pkfrc.taches.entity.TaskStatus;
import com.pkfrc.taches.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
@Tag(name = "Taches", description = "Gestion des taches (To-Do List)")
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    @Operation(summary = "Creer une nouvelle tache")
    public ResponseEntity<TaskResponseDTO> create(@Valid @RequestBody TaskRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.create(request));
    }

    @GetMapping
    @Operation(summary = "Lister les taches, avec filtre optionnel par statut")
    public ResponseEntity<List<TaskResponseDTO>> findAll(
            @RequestParam(required = false) TaskStatus status) {
        return ResponseEntity.ok(taskService.findAll(status));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Recuperer une tache par id")
    public ResponseEntity<TaskResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.findById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre a jour une tache")
    public ResponseEntity<TaskResponseDTO> update(@PathVariable Long id,
         @Valid @RequestBody TaskRequestDTO request) {
        return ResponseEntity.ok(taskService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une tache")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
