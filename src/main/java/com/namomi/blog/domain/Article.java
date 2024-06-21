package com.namomi.blog.domain;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Class: BaseEntity Project: com.minizin.travel.blog.domain
 * <p>
 * Description: Article
 *
 * @author JANG CHIHUN
 * @date 6/9/24 14:15 Copyright (c) 2024 MiniJin
 * @see <a href="https://github.com/team-MiniJin/BE">GitHub Repository</a>
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tiile", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "author", nullable = false)
    private String author;

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updateAt;

    @Builder
    public Article(String author, String title, String content) {
        this.author = author;
        this.title = title;
        this.content = content;
    }
}
