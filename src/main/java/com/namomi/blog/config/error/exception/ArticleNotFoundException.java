package com.namomi.blog.config.error.exception;

import com.namomi.blog.config.error.ErrorCode;

public class ArticleNotFoundException extends NotFoundException {
	public ArticleNotFoundException() {
		super(ErrorCode.ARTICLE_NOT_FOUND);
	}
}
