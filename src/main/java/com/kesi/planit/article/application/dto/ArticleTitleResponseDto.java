package com.kesi.planit.article.application.dto;

import com.kesi.planit.article.domain.Article;
import com.kesi.planit.article.domain.ArticleCategory;
import com.kesi.planit.core.content.permission.ContentPermission;
import lombok.Builder;

@Builder
public class ArticleTitleResponseDto {
    public Long id;
    public String title;
    public String authorEmail;
    public ArticleCategory category;
    public ContentPermission updatePermission;
    public ContentPermission readPermission;
    public ContentPermission commentPermission;
    public String createdTime;
    public String updatedTime;

    public static ArticleTitleResponseDto from(Article article) {
        return ArticleTitleResponseDto.builder()
                .id(article.getId())
                .title(article.getArticleContent().title())
                .authorEmail(article.getUser().getEmail())
                .category(article.getCategory())
                .updatePermission(article.getUpdatePermission())
                .readPermission(article.getReadPermission())
                .commentPermission(article.getCommentPermission())
                .createdTime(article.getCreatedTime().toString())
                .updatedTime(article.getUpdatedTime().toString())
                .build();
    }
}
