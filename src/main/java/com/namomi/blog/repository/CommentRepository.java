package com.namomi.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.namomi.blog.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
