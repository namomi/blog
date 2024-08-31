package com.namomi.blog.dto;

import com.namomi.blog.domain.Article;
import com.namomi.blog.domain.Comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddCommentRequest {
	private Long articleId;
	private String content;

	public Comment toEntity(String author, Article article) {
		return Comment.builder()
			.article(article)
			.content(content)
			.author(author)
			.build();
	}
}
