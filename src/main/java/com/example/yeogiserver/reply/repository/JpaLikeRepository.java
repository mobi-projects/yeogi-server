package com.example.yeogiserver.reply.repository;

import com.example.yeogiserver.reply.domain.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaLikeRepository extends JpaRepository<Like,Long> {
}
