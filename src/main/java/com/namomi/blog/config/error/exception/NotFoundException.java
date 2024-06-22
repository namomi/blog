package com.namomi.blog.config.error.exception;

import com.namomi.blog.config.error.ErrorCode;

public class NotFoundException extends BusinessBaseException{
	public NotFoundException(ErrorCode errorCode) {
		super(errorCode.getMessage(), errorCode);
	}

	public NotFoundException() {
		super(ErrorCode.NOT_FOUND);
	}
}
