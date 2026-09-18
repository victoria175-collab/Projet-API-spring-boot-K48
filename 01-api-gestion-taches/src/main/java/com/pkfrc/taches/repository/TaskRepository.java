package com.pkfrc.taches.repository;

import com.pkfrc.taches.entity.Task;
import com.pkfrc.taches.entity.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByStatus(TaskStatus status);
}
