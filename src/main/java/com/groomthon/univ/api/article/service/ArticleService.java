package com.groomthon.univ.api.article.service;

import com.groomthon.univ.api.article.dto.ArticleRequestDTO;
import com.groomthon.univ.api.article.dto.ArticleResponseDTO;
import com.groomthon.univ.api.article.entity.Article;
import com.groomthon.univ.api.article.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleResponseDTO createArticle(ArticleRequestDTO articleRequest) {

        Article article = Article.builder()
                .title(articleRequest.getTitle())
                .content(articleRequest.getContent())
                .build();

        Article savedArticle = articleRepository.save(article);

        return ArticleResponseDTO.toDTO(savedArticle);
    }
}
