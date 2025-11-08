package com.kesi.planit.article.application.handler;

import com.kesi.planit.article.domain.Article;
import com.kesi.planit.article.domain.ArticleCategory;

public interface ArticleCreationCategoryHandler {
    Article handle(Article article);
    ArticleCategory getCategory();
}
