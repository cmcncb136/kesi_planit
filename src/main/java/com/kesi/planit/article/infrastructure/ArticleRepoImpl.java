package com.kesi.planit.article.infrastructure;

import com.kesi.planit.article.application.repository.ArticleRepo;
import com.kesi.planit.article.domain.ArticleCategory;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class ArticleRepoImpl implements ArticleRepo {
    private final ArticleJpaRepo articleJpaRepo;

    @Override
    public Page<ArticleJpaEntity> findByCategory(ArticleCategory category, Pageable pageable) {
        return articleJpaRepo.findByCategory(category, pageable);
    }

    @Override
    public ArticleJpaEntity save(ArticleJpaEntity article) {
        return articleJpaRepo.save(article);
    }

    @Override
    public void deleteById(Long id) {
        articleJpaRepo.deleteById(id);
    }

    @Override
    public Optional<ArticleJpaEntity> findById(Long id) {
        return articleJpaRepo.findById(id);
    }
}
