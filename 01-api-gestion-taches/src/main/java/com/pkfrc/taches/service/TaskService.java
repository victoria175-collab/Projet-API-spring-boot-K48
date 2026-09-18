package com.pkfrc.taches.service;

import com.pkfrc.taches.dto.TaskRequestDTO;
import com.pkfrc.taches.dto.TaskResponseDTO;
import com.pkfrc.taches.entity.TaskStatus;

import java.util.List;

public interface TaskService {
    TaskResponseDTO create(TaskRequestDTO request);
    List<TaskResponseDTO> findAll(TaskStatus status);
    TaskResponseDTO findById(Long id);
    TaskResponseDTO update(Long id, TaskRequestDTO request);
    void delete(Long id);
}
