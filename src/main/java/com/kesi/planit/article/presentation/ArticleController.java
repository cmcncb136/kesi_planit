package com.kesi.planit.article.presentation;

import com.kesi.planit.article.application.ArticleFacade;
import com.kesi.planit.article.application.dto.ArticleContentResponseDto;
import com.kesi.planit.article.application.dto.ArticleCreateRequestDto;
import com.kesi.planit.article.application.dto.ArticleTitleResponseDto;
import com.kesi.planit.article.domain.ArticleCategory;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("article")
@Slf4j
public class ArticleController {
    private final ArticleFacade articleFacade;

    @GetMapping("")
    public ArticleContentResponseDto getById(Long articleId, HttpServletRequest request) {
        return articleFacade.getArticle(articleId, (String)request.getAttribute("uid"));
    }

    @GetMapping("/list")
    public Page<ArticleTitleResponseDto> findByCategory(ArticleCategory category, Integer page, Integer size) {
        return articleFacade.getArticleByCategory(category, size, page);
    }

    @PostMapping("")
    public Long createArticle(@RequestBody ArticleCreateRequestDto articleCreateRequestDto, HttpServletRequest request) {
        log.info("Creating request article: {}", articleCreateRequestDto);
        return articleFacade.createArticle(articleCreateRequestDto, (String)request.getAttribute("uid"));
    }
}
