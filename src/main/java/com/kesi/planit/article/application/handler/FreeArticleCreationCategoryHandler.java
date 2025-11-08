package com.kesi.planit.article.application.handler;

import com.kesi.planit.article.domain.Article;
import com.kesi.planit.article.domain.ArticleCategory;
import org.springframework.stereotype.Service;

@Service
public class FreeArticleCreationCategoryHandler implements ArticleCreationCategoryHandler {
    @Override
    public Article handle(Article article) {
        return article;
    }

    @Override
    public ArticleCategory getCategory() {
        return ArticleCategory.FREE;
    }
}
