package com.namomi.blog.dto;



import com.namomi.blog.domain.Article;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Class: BaseEntity Project: com.minizin.travel.blog.dto
 * <p>
 * Description: AddArticleRequest
 *
 * @author JANG CHIHUN
 * @date 6/9/24 14:20 Copyright (c) 2024 MiniJin
 * @see <a href="https://github.com/team-MiniJin/BE">GitHub Repository</a>
 */

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AddArticleRequest {

    @NotNull
    @Size(min = 1, max = 10)
    private String title;

    @NotEmpty
    private String content;

    public Article toEntity(String author) {
        return Article.builder()
            .title(title)
            .content(content)
            .author(author)
            .build();
    }
}
