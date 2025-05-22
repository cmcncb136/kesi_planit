package com.kesi.planit.article.presentation;

import com.kesi.planit.article.application.ArticleFacade;
import com.kesi.planit.article.application.dto.ArticleContentResponseDto;
import com.kesi.planit.article.application.dto.ArticleTitleResponseDto;
import com.kesi.planit.article.domain.ArticleCategory;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("article")
public class ArticleController {
    private final ArticleFacade articleFacade;

    @GetMapping("")
    public ArticleContentResponseDto getById(Long articleId, HttpServletRequest request) {
        return articleFacade.getArticle(articleId, (String)request.getAttribute("uid"));
    }

    @GetMapping("/list")
    public Page<ArticleTitleResponseDto> findByCategory(ArticleCategory category, Integer page, Integer size) {
        return articleFacade.getArticleByCategory(category, page, size);
    }
}
