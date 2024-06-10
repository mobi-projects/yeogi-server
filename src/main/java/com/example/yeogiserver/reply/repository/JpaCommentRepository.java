package com.example.yeogiserver.reply.repository;

import com.example.yeogiserver.reply.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface JpaCommentRepository extends JpaRepository<Comment, Long>{
    List<Comment> findByPostId(Long id);
    void deleteByPostId(Long id);
}
