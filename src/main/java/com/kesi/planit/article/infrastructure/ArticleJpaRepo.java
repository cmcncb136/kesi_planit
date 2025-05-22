package com.kesi.planit.article.infrastructure;

import com.kesi.planit.article.domain.ArticleCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ArticleJpaRepo extends JpaRepository<ArticleJpaEntity, Long> {
    Page<ArticleJpaEntity> findByCategory(ArticleCategory category, Pageable pageable);
}
