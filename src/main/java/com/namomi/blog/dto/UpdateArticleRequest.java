package com.namomi.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Class: BaseEntity Project: com.minizin.travel.blog.dto
 * <p>
 * Description: UpdateArticleRequest
 *
 * @author JANG CHIHUN
 * @date 6/9/24 16:34 Copyright (c) 2024 MiniJin
 * @see <a href="https://github.com/team-MiniJin/BE">GitHub Repository</a>
 */

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateArticleRequest {
    private String title;
    private String content;
}
