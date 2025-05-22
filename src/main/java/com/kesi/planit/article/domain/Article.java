package com.kesi.planit.article.domain;

import com.kesi.planit.core.content.permission.ContentPermission;
import com.kesi.planit.core.content.permission.ContentPermissionChecker;
import com.kesi.planit.user.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class Article {
    private Long id;
    private User user;
    private ArticleContent articleContent;
    private ArticleCategory category;
    private ContentPermission commentPermission;
    private ContentPermission readPermission;
    private ContentPermission updatePermission;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;

    public void updateContent(ArticleContent articleContent, User currentUser) {
        if(articleContent == null)
            throw new NullPointerException("articleContent is null");

        if(!ContentPermissionChecker.can(updatePermission, currentUser, user))
           throw new IllegalArgumentException("updatePermission is not allowed");

        this.articleContent = articleContent;
    }
}
