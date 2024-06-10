package com.example.yeogiserver.post.repository;

import com.example.yeogiserver.post.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaPostRepository extends JpaRepository<Post, Long> {
}
