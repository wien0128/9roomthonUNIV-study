package com.groomthon.univ.api.article.dto;

import com.groomthon.univ.api.article.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.format.DateTimeFormatter;

@Getter
@AllArgsConstructor
@Builder
public class ArticleResponseDTO {

    private Long id;
    private String title;
    private String content;
    private String createdAt;
    private String updatedAt;

    public static ArticleResponseDTO toDTO(Article article) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return ArticleResponseDTO.builder()
                .id(article.getId())
                .title(article.getTitle())
                .content(article.getContent())
                .createdAt(article.getCreatedAt().format(formatter))
                .updatedAt(article.getUpdatedAt().format(formatter))
                .build();
    }
}
