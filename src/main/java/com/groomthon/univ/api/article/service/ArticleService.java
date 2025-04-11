package com.groomthon.univ.api.article.service;

import com.groomthon.univ.api.article.dto.ArticleRequestDTO;
import com.groomthon.univ.api.article.dto.ArticleResponseDTO;
import com.groomthon.univ.api.article.entity.Article;
import com.groomthon.univ.api.article.repository.ArticleRepository;
import com.groomthon.univ.common.exception.NotFoundException;
import com.groomthon.univ.common.response.ErrorStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <h3>게시글 서비스</h3>
 * <p>게시글 생성, 단건/전체 조회, 수정, 삭제 기능</p>
 */

@Service
@RequiredArgsConstructor
@Transactional
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
    @Transactional(readOnly = true)
    public List<ArticleResponseDTO> getTotalArticles() {

        return articleRepository.findAll().stream()
                .map(ArticleResponseDTO::toDTO)
                .toList();
    }

    // 게시글 단건 조회
    @Transactional(readOnly = true)
    public ArticleResponseDTO getArticleById(Long id) {

        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorStatus.RESOURCE_NOT_FOUND.getMessage()));

        return ArticleResponseDTO.toDTO(article);
    }

    // 게시글 수정
    public ArticleResponseDTO updateArticle(Long id, ArticleRequestDTO articleRequest) {

        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorStatus.RESOURCE_NOT_FOUND.getMessage()));

        article.updateArticle(articleRequest.getTitle(), articleRequest.getContent());

        return ArticleResponseDTO.toDTO(article);
    }

    // 게시글 단건 삭제
    public void deleteArticle(Long id) {

//        if (!articleRepository.existsById(id)) {
//            throw new NotFoundException(ErrorStatus.RESOURCE_NOT_FOUND.getMessage());
//        }

        articleRepository.deleteById(id);
    }
}
