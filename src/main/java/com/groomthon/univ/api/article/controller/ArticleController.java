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

import java.util.List;

/**
 * <h3>게시글 컨트롤러</h3>
 * <p>게시글 등록, 단건/전체 조회, 수정, 삭제 컨트롤러(엔드포인트)</p>
 */

@Tag(name = "Article", description = "스터디 게시글 관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/articles")
public class ArticleController {

    private final ArticleService articleService;

    @Operation(
            summary = "게시글 등록 API",
            description = "새로운 게시글을 등록합니다."
    )
    @PostMapping
    public ResponseEntity<ApiResponse<ArticleResponseDTO>> createArticle(
            @RequestBody @Valid ArticleRequestDTO articleRequestDTO
    ) {
        ArticleResponseDTO createdArticle = articleService.createArticle(articleRequestDTO);

        return ApiResponse.success(SuccessStatus.CREATE_ARTICLE_SUCCESS, createdArticle);
    }

    @Operation(
            summary = "게시글 단건 조회 API",
            description = "단건 게시글을 조회합니다."
    )
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ArticleResponseDTO>> getArticleById(
            @PathVariable Long id
    ) {
        ArticleResponseDTO article = articleService.getArticleById(id);

        return ApiResponse.success(SuccessStatus.SEND_ARTICLE_SUCCESS, article);
    }

    @Operation(
            summary = "전체 게시글 조회 API",
            description = "현재 게시글 목록을 조회합니다."
    )
    @GetMapping
    public ResponseEntity<ApiResponse<List<ArticleResponseDTO>>> getTotalArticles() {

        List<ArticleResponseDTO> articles = articleService.getTotalArticles();

        return ApiResponse.success(SuccessStatus.SEND_ARTICLE_SUCCESS, articles);
    }

    @Operation(
            summary = "게시글 수정 API",
            description = "등록된 게시글을 수정합니다."
    )
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ArticleResponseDTO>> updateArticle(
            @PathVariable Long id,
            @RequestBody @Valid ArticleRequestDTO articleRequest
    ) {
        ArticleResponseDTO updateArticle = articleService.updateArticle(id, articleRequest);

        return ApiResponse.success(SuccessStatus.UPDATE_MEMO_SUCCESS, updateArticle);
    }

    @Operation(
            summary = "게시글 삭제 API",
            description = "등록된 게시글을 삭제합니다."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteArticleById(
            @PathVariable Long id
    ) {
        articleService.deleteArticle(id);

        return ApiResponse.success_only(SuccessStatus.DELETE_MEMO_SUCCESS);
    }
}
