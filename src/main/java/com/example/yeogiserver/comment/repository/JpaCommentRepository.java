package com.example.yeogiserver.comment.repository;

import com.example.yeogiserver.comment.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaCommentRepository extends JpaRepository<Comment, Long>{
    List<Comment> findByPostId(Long id, Pageable page);
    void deleteByPostId(Long id);
    Long countByPostId(Long id);
}
