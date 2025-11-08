package com.kesi.planit.article.domain;

import com.kesi.planit.core.content.permission.ContentPermission;
import com.kesi.planit.core.content.permission.ContentPermissionChecker;
import com.kesi.planit.user.domain.User;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.access.AccessDeniedException;

import java.time.LocalDateTime;

@Getter
public class Article {
    private final Long id;
    private final User user;
    private ArticleContent articleContent;
    private ArticleCategory category;
    private ContentPermission commentPermission;
    private ContentPermission readPermission;
    private ContentPermission updatePermission;
    private LocalDateTime createdTime;
    private LocalDateTime updatedTime;

    @Builder
    public Article(Long id, User user, ArticleContent articleContent, ArticleCategory category, ContentPermission commentPermission, ContentPermission readPermission, ContentPermission updatePermission, LocalDateTime createdTime, LocalDateTime updatedTime) {
        this.id = id;
        this.user = user;
        this.articleContent = articleContent;
        this.category = category;
        this.commentPermission = commentPermission;
        this.readPermission = readPermission;
        this.updatePermission = updatePermission;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;

        if(readPermission == ContentPermission.OWNER_ONLY)
            throw new IllegalArgumentException("cannot create an article, article read permission is not OWNER_ONLY");

        if(updatePermission == ContentPermission.PUBLIC)
            throw new IllegalArgumentException("cannot create an article, article update permission is not PUBLIC");

        if(commentPermission == ContentPermission.OWNER_ONLY)
            throw new IllegalArgumentException("cannot create an article, article comment permission is not OWNER_ONLY");
    }

    public void updateContent(ArticleContent articleContent, User currentUser) {
        if(articleContent == null)
            throw new NullPointerException("articleContent is null");

        if(!ContentPermissionChecker.can(updatePermission, currentUser, user))
           throw new AccessDeniedException("updatePermission is not allowed");

        this.articleContent = articleContent;
    }
}
