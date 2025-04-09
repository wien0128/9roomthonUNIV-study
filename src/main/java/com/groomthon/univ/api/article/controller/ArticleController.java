package com.groomthon.univ.api.article.controller;

import com.groomthon.univ.api.article.dto.ArticleRequestDTO;
import com.groomthon.univ.api.article.dto.ArticleResponseDTO;
import com.groomthon.univ.api.article.service.ArticleService;
import com.groomthon.univ.common.response.ApiResponse;
import com.groomthon.univ.common.response.SuccessStatus;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Tag(name = "Article", description = "스터디 게시글 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/article")
public class ArticleController {

    private final ArticleService articleService;

    @Operation(
            summary = "게시글 등록 API",
            description = "새로운 게시글을 등록합니다."
    )
    @PostMapping("/new")
    public ResponseEntity<ApiResponse<Void>> createArticle(
            @RequestBody @Valid ArticleRequestDTO articleRequestDTO
    ) {
        ArticleResponseDTO createdArticle = articleService.createArticle(articleRequestDTO);

        return ApiResponse.success_only(SuccessStatus.CREATE_ARTICLE_SUCCESS);
    }
}
