package com.namomi.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.namomi.blog.domain.Article;

/**
 * Class: BaseEntity Project: com.minizin.travel.blog.repository
 * <p>
 * Description: BlogRepository
 *
 * @author JANG CHIHUN
 * @date 6/9/24 14:15 Copyright (c) 2024 MiniJin
 * @see <a href="https://github.com/team-MiniJin/BE">GitHub Repository</a>
 */
public interface BlogRepository extends JpaRepository<Article, Long> {
}
