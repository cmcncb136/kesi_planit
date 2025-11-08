package com.kesi.planit.article.application.handler;


import com.kesi.planit.article.domain.Article;
import com.kesi.planit.article.domain.ArticleCategory;
import com.kesi.planit.core.content.permission.ContentPermissionChecker;
import com.kesi.planit.core.role.Role;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public class NoticeArticleCreationCategoryHandler implements ArticleCreationCategoryHandler {
    @Override
    public Article handle(Article article) {
        if(!article.getUser().getRole().isAtLeast(Role.MANAGER))
            throw new AccessDeniedException("only manager can create notices");

        return article;
    }

    @Override
    public ArticleCategory getCategory() {
        return ArticleCategory.NOTICE;
    }
}
