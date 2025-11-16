package com.kesi.planit.article.application;

import com.kesi.planit.article.application.handler.ArticleCreationCategoryHandler;
import com.kesi.planit.article.application.repository.ArticleRepo;
import com.kesi.planit.article.domain.Article;
import com.kesi.planit.article.domain.ArticleCategory;
import com.kesi.planit.article.infrastructure.ArticleJpaEntity;
import com.kesi.planit.core.content.permission.ContentPermissionChecker;
import com.kesi.planit.user.application.UserService;
import com.kesi.planit.user.domain.User;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ArticleService {
    private final ArticleRepo articleRepo;
    private final UserService userService;
    private final Map<ArticleCategory, ArticleCreationCategoryHandler> articleCreationCategoryHandlerMap;

    public ArticleService(ArticleRepo articleRepo, UserService userService, List<ArticleCreationCategoryHandler> articleCreationCategoryHandlers) {
        this.articleRepo = articleRepo;
        this.userService = userService;
        this.articleCreationCategoryHandlerMap = articleCreationCategoryHandlers.stream().collect(Collectors.toMap(ArticleCreationCategoryHandler::getCategory, Function.identity()));
    }

    public Article save(Article article) {
        return articleRepo.save(ArticleJpaEntity.from(article)).toDomain(article.getUser());
    }

    public Article createArticle(Article article) {
        log.info("Creating new article category {}", article.getCategory());
        Article processedArticle = articleCreationCategoryHandlerMap.get(article.getCategory()).handle(article);

        return articleRepo.save(ArticleJpaEntity.from(processedArticle)).toDomain(processedArticle.getUser());
    }

    public Article getById(Long id, User user) {
        return getById(id, Optional.of(user));
    }

    public Article getById(Long id, Optional<User> currentUser) {
        ArticleJpaEntity entity = articleRepo.findById(id).orElseThrow(NullPointerException::new);
        User user = userService.getById(entity.getUid());
        Article article = entity.toDomain(user);

        if(!ContentPermissionChecker.can(article.getReadPermission(), currentUser, article.getUser()))
            throw new AccessDeniedException("You do not have permission to read this article");

        return entity.toDomain(user);
    }

    public Page<Article> findByCategory(ArticleCategory category, Pageable pageable) {
        return articleRepo.findByCategory(category, pageable).map(entity ->
            entity.toDomain(userService.getById(entity.getUid()))
        );
    }

    public void delete(Article article, User currentUser) {
        if(!ContentPermissionChecker.can(article.getUpdatePermission(), currentUser, article.getUser()))
            throw new AccessDeniedException("You do not have permission to delete this article");

        articleRepo.deleteById(article.getId());
    }

    @PostConstruct
    public void validateHandlerMapping() {
        Set<ArticleCategory> requiredCategories = EnumSet.allOf(ArticleCategory.class);
        Set<ArticleCategory> actualCategories = articleCreationCategoryHandlerMap.keySet();

        requiredCategories.removeAll(actualCategories);
        log.info("actualCategories: {}", actualCategories);

        if(!requiredCategories.isEmpty()) {
            throw new IllegalStateException("Missing required categories: " + requiredCategories);
        }
    }
}
