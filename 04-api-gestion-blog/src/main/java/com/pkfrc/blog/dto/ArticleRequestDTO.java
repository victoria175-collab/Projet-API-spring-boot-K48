package com.pkfrc.blog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ArticleRequestDTO {

    @NotBlank(message = "Le titre est obligatoire")
    @Size(max = 200, message = "Le titre ne doit pas depasser 200 caracteres")
    private String title;

    @NotBlank(message = "Le contenu est obligatoire")
    private String content;
}
