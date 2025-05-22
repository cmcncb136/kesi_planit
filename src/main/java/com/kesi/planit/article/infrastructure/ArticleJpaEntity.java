package com.kesi.planit.article.infrastructure;

import com.kesi.planit.article.domain.Article;
import com.kesi.planit.article.domain.ArticleCategory;
import com.kesi.planit.article.domain.ArticleContent;
import com.kesi.planit.core.content.permission.ContentPermission;
import com.kesi.planit.user.domain.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class ArticleJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String uid;

    private String title;

    @Lob
    private String content;

    @Enumerated(EnumType.STRING)
    private ArticleCategory category;

    @Enumerated(EnumType.STRING)
    private ContentPermission commentPermission;
    @Enumerated(EnumType.STRING)
    private ContentPermission readPermission;
    @Enumerated(EnumType.STRING)
    private ContentPermission updatePermission;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime updateDate;

    @Builder
    public ArticleJpaEntity(Long id, String uid, String title, String content, ArticleCategory category, ContentPermission commentPermission, ContentPermission readPermission, ContentPermission updatePermission, LocalDateTime createdDate, LocalDateTime updateDate) {
        this.id = id;
        this.uid = uid;
        this.title = title;
        this.content = content;
        this.category = category;
        this.commentPermission = commentPermission;
        this.readPermission = readPermission;
        this.updatePermission = updatePermission;
        this.createdDate = createdDate;
        this.updateDate = updateDate;
    }

    public Article toDomain(User user) {
        return Article.builder()
                .id(id)
                .user(user)
                .articleContent(new ArticleContent(title, content))
                .category(category)
                .commentPermission(commentPermission)
                .readPermission(readPermission)
                .updatePermission(updatePermission)
                .createdTime(createdDate)
                .updatedTime(updateDate)
                .build();
    }

    public static ArticleJpaEntity from(Article article) {
        return ArticleJpaEntity.builder()
                .id(article.getId())
                .uid(article.getUser().getUid())
                .title(article.getArticleContent().title())
                .content(article.getArticleContent().content())
                .category(article.getCategory())
                .commentPermission(article.getCommentPermission())
                .readPermission(article.getReadPermission())
                .updatePermission(article.getUpdatePermission())
                .build();
    }
}
