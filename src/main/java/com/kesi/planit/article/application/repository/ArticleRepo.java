package com.kesi.planit.article.application.repository;


import com.kesi.planit.article.domain.ArticleCategory;
import com.kesi.planit.article.infrastructure.ArticleJpaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface ArticleRepo {
    Page<ArticleJpaEntity> findByCategory(ArticleCategory category, Pageable pageable);
    ArticleJpaEntity save(ArticleJpaEntity article);
    void deleteById(Long id);
    Optional<ArticleJpaEntity> findById(Long id);
}
