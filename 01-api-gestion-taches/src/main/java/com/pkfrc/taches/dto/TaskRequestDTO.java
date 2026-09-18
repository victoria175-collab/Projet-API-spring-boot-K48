package com.pkfrc.taches.dto;

import com.pkfrc.taches.entity.TaskStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequestDTO {

    @NotBlank(message = "Le titre est obligatoire")
    @Size(max = 150, message = "Le titre ne doit pas depasser 150 caracteres")
    private String title;

    @Size(max = 2000, message = "La description ne doit pas depasser 2000 caracteres")
    private String description;

    private TaskStatus status;
}
