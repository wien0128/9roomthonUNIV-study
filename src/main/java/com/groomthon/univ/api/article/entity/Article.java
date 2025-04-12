package com.groomthon.univ.api.article.entity;

import com.groomthon.univ.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder(toBuilder = true)
@Table(name = "articles")
public class Article extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Long id;

    @Column(name = "article_title", nullable = false, length = 100)
    @Size(max = 100, message = "제목은 최대 100자까지 입력 가능합니다.")
    private String title;

    @Column(name = "article_content", nullable = false, columnDefinition = "TEXT")
    @NotBlank(message = "내용은 비어 있을 수 없습니다.")
    private String content;

    public void updateArticle(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
