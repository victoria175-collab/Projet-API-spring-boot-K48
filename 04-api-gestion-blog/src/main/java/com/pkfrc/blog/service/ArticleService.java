package com.pkfrc.blog.service;

import com.pkfrc.blog.dto.ArticleRequestDTO;
import com.pkfrc.blog.dto.ArticleResponseDTO;
import com.pkfrc.blog.dto.CommentRequestDTO;
import com.pkfrc.blog.dto.CommentResponseDTO;

import java.util.List;

public interface ArticleService {
    ArticleResponseDTO create(ArticleRequestDTO request);
    List<ArticleResponseDTO> findAll();
    ArticleResponseDTO findById(Long id);
    ArticleResponseDTO update(Long id, ArticleRequestDTO request);
    void delete(Long id);
    CommentResponseDTO addComment(Long articleId, CommentRequestDTO request);
}
