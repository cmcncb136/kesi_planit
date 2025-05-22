package com.kesi.planit.article.application;

import com.kesi.planit.article.application.repository.ArticleRepo;
import com.kesi.planit.article.domain.Article;
import com.kesi.planit.article.domain.ArticleCategory;
import com.kesi.planit.article.infrastructure.ArticleJpaEntity;
import com.kesi.planit.core.content.permission.ContentPermission;
import com.kesi.planit.core.content.permission.ContentPermissionChecker;
import com.kesi.planit.user.application.UserService;
import com.kesi.planit.user.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ArticleService {
    private final ArticleRepo articleRepo;
    private final UserService userService;

    public Article createArticle(Article article) {
        return articleRepo.save(ArticleJpaEntity.from(article)).toDomain(article.getUser());
    }

    public Article getById(Long id, User user) {
        return getById(id, Optional.of(user));
    }

    public Article getById(Long id, Optional<User> currentUser) {
        ArticleJpaEntity entity = articleRepo.findById(id).orElseThrow(NullPointerException::new);
        User user = userService.getById(entity.getUid());
        Article article = entity.toDomain(user);

        if(!ContentPermissionChecker.can(article.getReadPermission(), currentUser, article.getUser()))
            throw new IllegalArgumentException("You do not have permission to delete this article");

        return entity.toDomain(user);
    }

    public Page<Article> findByCategory(ArticleCategory category, Pageable pageable) {
        return articleRepo.findByCategory(category, pageable).map(entity ->
            entity.toDomain(userService.getById(entity.getUid()))
        );
    }

    public void delete(Article article, User currentUser) {
        if(!ContentPermissionChecker.can(article.getUpdatePermission(), currentUser, article.getUser()))
            throw new IllegalArgumentException("You do not have permission to delete this article");

        articleRepo.deleteById(article.getId());
    }
}
