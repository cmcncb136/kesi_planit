package com.kesi.planit.article.application.dto;

import com.kesi.planit.article.domain.Article;
import com.kesi.planit.article.domain.ArticleCategory;
import com.kesi.planit.article.domain.ArticleContent;
import com.kesi.planit.core.content.permission.ContentPermission;
import com.kesi.planit.user.domain.User;

public class ArticleCreateRequestDto {
    public String title;
    public String content;
    public ArticleCategory category;
    public ContentPermission updatePermission;
    public ContentPermission readPermission;
    public ContentPermission commentPermission;

    public Article toDomain(User user) {
        return Article.builder()
                .user(user)
                .articleContent(new ArticleContent(title, content))
                .category(category)
                .updatePermission(updatePermission)
                .readPermission(readPermission)
                .commentPermission(commentPermission)
                .build();
    }
}
