package com.groomthon.univ.api.article.service;

import com.groomthon.univ.api.article.dto.ArticleRequestDTO;
import com.groomthon.univ.api.article.dto.ArticleResponseDTO;
import com.groomthon.univ.api.article.entity.Article;
import com.groomthon.univ.api.article.repository.ArticleRepository;
import com.groomthon.univ.common.exception.NotFoundException;
import com.groomthon.univ.common.response.ErrorStatus;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    // 게시글 생성
    public ArticleResponseDTO createArticle(ArticleRequestDTO articleRequest) {

        Article article = Article.builder()
                .title(articleRequest.getTitle())
                .content(articleRequest.getContent())
                .build();

        Article savedArticle = articleRepository.save(article);

        return ArticleResponseDTO.toDTO(savedArticle);
    }

    // 전체 게시글 조회
    public List<ArticleResponseDTO> getTotalArticles() {

        return articleRepository.findAll().stream()
                .map(ArticleResponseDTO::toDTO)
                .toList();
    }

    // 게시글 단건 조회
    public ArticleResponseDTO getArticleById(Long id) {

        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorStatus.RESOURCE_NOT_FOUND.getMessage()));

        return ArticleResponseDTO.toDTO(article);
    }

    // 게시글 수정
    @Transactional
    public ArticleResponseDTO updateArticle(Long id, @Valid ArticleRequestDTO articleRequest) {

        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorStatus.RESOURCE_NOT_FOUND.getMessage()));

        article.updateArticle(articleRequest.getTitle(), articleRequest.getContent());

        return ArticleResponseDTO.toDTO(article);
    }

    // 게시글 단건 삭제
    public void deleteArticle(Long id) {

        if (!articleRepository.existsById(id)) {
            throw new NotFoundException(ErrorStatus.RESOURCE_NOT_FOUND.getMessage());
        }

        articleRepository.deleteById(id);
    }
}
