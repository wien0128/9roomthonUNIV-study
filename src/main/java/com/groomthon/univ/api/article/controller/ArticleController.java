package com.groomthon.univ.api.article.controller;

import com.groomthon.univ.api.article.service.ArticleService;
import com.groomthon.univ.common.response.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Article", description = "게시글 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/article")
public class ArticleController {

    private final ArticleService articleService;

    public ResponseEntity<ApiResponse<Void>> createArticle() {

    }
}
