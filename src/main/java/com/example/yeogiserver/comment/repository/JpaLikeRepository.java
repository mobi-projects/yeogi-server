package com.example.yeogiserver.comment.repository;

import com.example.yeogiserver.comment.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaLikeRepository extends JpaRepository<Like,Long> {
}
