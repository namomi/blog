package com.namomi.blog.dto;


import com.namomi.blog.domain.Article;

import lombok.Data;

@Data
public class ArticleResponse {

    private String title;
    private String content;

    public ArticleResponse(Article article) {
        this.title = article.getTitle();
        this.content = article.getContent();
    }
}
