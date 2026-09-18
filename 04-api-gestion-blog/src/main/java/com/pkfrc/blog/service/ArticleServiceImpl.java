package com.pkfrc.blog.service;

import com.pkfrc.blog.dto.*;
import com.pkfrc.blog.entity.Article;
import com.pkfrc.blog.entity.Comment;
import com.pkfrc.blog.exception.ResourceNotFoundException;
import com.pkfrc.blog.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    @Override
    public ArticleResponseDTO create(ArticleRequestDTO request) {
        Article article = Article.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .build();
        return toResponse(articleRepository.save(article));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ArticleResponseDTO> findAll() {
        return articleRepository.findAll().stream().map(this::toResponse).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ArticleResponseDTO findById(Long id) {
        return toResponse(getOrThrow(id));
    }

    @Override
    public ArticleResponseDTO update(Long id, ArticleRequestDTO request) {
        Article article = getOrThrow(id);
        article.setTitle(request.getTitle());
        article.setContent(request.getContent());
        return toResponse(articleRepository.save(article));
    }

    @Override
    public void delete(Long id) {
        Article article = getOrThrow(id);
        articleRepository.delete(article);
    }

    @Override
    public CommentResponseDTO addComment(Long articleId, CommentRequestDTO request) {
        Article article = getOrThrow(articleId);
        Comment comment = Comment.builder()
                .author(request.getAuthor())
                .content(request.getContent())
                .article(article)
                .build();
        article.getComments().add(comment);
        articleRepository.save(article);
        return toCommentResponse(comment);
    }

    private Article getOrThrow(Long id) {
        return articleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Article introuvable avec l'id : " + id));
    }

    private ArticleResponseDTO toResponse(Article article) {
        List<CommentResponseDTO> comments = article.getComments().stream()
                .map(this::toCommentResponse)
                .toList();
        return ArticleResponseDTO.builder()
                .id(article.getId())
                .title(article.getTitle())
                .content(article.getContent())
                .publicationDate(article.getPublicationDate())
                .comments(comments)
                .build();
    }

    private CommentResponseDTO toCommentResponse(Comment comment) {
        return CommentResponseDTO.builder()
                .id(comment.getId())
                .author(comment.getAuthor())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
