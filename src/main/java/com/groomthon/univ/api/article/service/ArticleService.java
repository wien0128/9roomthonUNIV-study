package com.groomthon.univ.api.article.service;

import com.groomthon.univ.api.article.dto.ArticleRequestDTO;
import com.groomthon.univ.api.article.entity.Article;
import com.groomthon.univ.api.article.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleResponseDto  createArticle(ArticleRequestDTO articleRequestDTO) {

        Article article = Article.builder()
                .title(articleRequestDTO.getTitle())
                .content(articleRequestDTO.getContent())
                .build();

        Article savedArticle = articleRepository.save(article);

        return ArticleREspon
    }
}
