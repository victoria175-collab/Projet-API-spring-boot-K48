package com.pkfrc.blog.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentRequestDTO {

    @NotBlank(message = "L'auteur est obligatoire")
    @Size(max = 100)
    private String author;

    @NotBlank(message = "Le contenu du commentaire est obligatoire")
    @Size(max = 1000)
    private String content;
}
