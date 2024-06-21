package com.namomi.blog.service;



import static com.namomi.blog.exception.BlogErrorCode.*;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.namomi.blog.domain.Article;
import com.namomi.blog.dto.AddArticleRequest;
import com.namomi.blog.dto.UpdateArticleRequest;
import com.namomi.blog.exception.CustomException;
import com.namomi.blog.repository.BlogRepository;

import lombok.RequiredArgsConstructor;

/**
 * Class: BaseEntity Project: com.minizin.travel.blog.service
 * <p>
 * Description: BlogService
 *
 * @author JANG CHIHUN
 * @date 6/9/24 14:20 Copyright (c) 2024 MiniJin
 * @see <a href="https://github.com/team-MiniJin/BE">GitHub Repository</a>
 */
@RequiredArgsConstructor
@Service
public class BlogService {

    private final BlogRepository blogRepository;

    @Transactional
    public Article save(AddArticleRequest request, String username) {
        return blogRepository.save(request.toEntity(username));
    }

    @Transactional(readOnly = true)
    public List<Article> findAll() {
        return blogRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Article findById(Long id) {
        return getArticle(id);
    }

    @Transactional
    public Article update(Long id, UpdateArticleRequest request) {
        Article article = getArticle(id);

        authorizeArticleAuthor(article);
        article.update(request.getTitle(), request.getContent());

        return article;
    }

    @Transactional
    public void delete(Long id) {
        Article article = blogRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("not found : " + id));

        authorizeArticleAuthor(article);
        blogRepository.delete(article);
    }

    private static void authorizeArticleAuthor(Article article) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!article.getAuthor().equals(userName)) {
            throw new IllegalArgumentException("not authorized");
        }
    }

    private Article getArticle(Long id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new CustomException(BLOG_NOT_FOUND));
    }
}
