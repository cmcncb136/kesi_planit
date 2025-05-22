package com.kesi.planit.article.application;

import com.kesi.planit.article.application.dto.ArticleContentResponseDto;
import com.kesi.planit.article.application.dto.ArticleCreateRequestDto;
import com.kesi.planit.article.application.dto.ArticleTitleResponseDto;
import com.kesi.planit.article.domain.Article;
import com.kesi.planit.article.domain.ArticleCategory;
import com.kesi.planit.core.PageRequestFactory;
import com.kesi.planit.user.application.UserService;
import com.kesi.planit.user.domain.User;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class ArticleFacade {
    private ArticleService articleService;
    private UserService userService;

    public Long createArticle(ArticleCreateRequestDto dto, String uid) {
        User user = userService.getById(uid);
        Article article = articleService.createArticle(dto.toDomain(user));
        return article.getId();
    }

    public void deleteArticle(Long articleId, String uid) {
        User user = userService.getById(uid);
        Article article = articleService.getById(articleId, user);
        articleService.delete(article, user);
    }

    public ArticleContentResponseDto getArticle(Long articleId, String uid) {
        Optional<User> user = userService.findById(uid);
        Article article = articleService.getById(articleId, user);
        return ArticleContentResponseDto.from(article);
    }

    public Page<ArticleTitleResponseDto> getArticleByCategory(ArticleCategory category, Integer size, Integer page) {
        Pageable pageable = PageRequestFactory.of(page, size);
        Page<Article> articles = articleService.findByCategory(category, pageable);
        return articles.map(ArticleTitleResponseDto::from);
    }
}
