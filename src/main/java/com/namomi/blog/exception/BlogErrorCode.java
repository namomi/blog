package com.namomi.blog.exception;


public record BlogErrorCode(int status, String message) implements ErrorCode {
    public static final BlogErrorCode BLOG_NOT_FOUND = new BlogErrorCode(404, "User not found");
}
