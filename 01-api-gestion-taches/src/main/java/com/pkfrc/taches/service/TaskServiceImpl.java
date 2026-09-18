package com.pkfrc.taches.service;

import com.pkfrc.taches.dto.TaskRequestDTO;
import com.pkfrc.taches.dto.TaskResponseDTO;
import com.pkfrc.taches.entity.Task;
import com.pkfrc.taches.entity.TaskStatus;
import com.pkfrc.taches.exception.ResourceNotFoundException;
import com.pkfrc.taches.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    @Override
    public TaskResponseDTO create(TaskRequestDTO request) {
        Task task = Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .status(request.getStatus() != null ? request.getStatus() : TaskStatus.A_FAIRE)
                .build();
        return toResponse(taskRepository.save(task));
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskResponseDTO> findAll(TaskStatus status) {
        List<Task> tasks = (status != null)
                ? taskRepository.findByStatus(status)
                : taskRepository.findAll();
        return tasks.stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public TaskResponseDTO findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    @Override
    public TaskResponseDTO update(Long id, TaskRequestDTO request) {
        Task task = getOrThrow(id);
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        if (request.getStatus() != null) {
            task.setStatus(request.getStatus());
        }
        return toResponse(taskRepository.save(task));
    }

    @Override
    public void delete(Long id) {
        Task task = getOrThrow(id);
        taskRepository.delete(task);
    }

    private Task getOrThrow(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tache introuvable avec l'id : " + id));
    }

    private TaskResponseDTO toResponse(Task task) {
        return TaskResponseDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .build();
    }
}
