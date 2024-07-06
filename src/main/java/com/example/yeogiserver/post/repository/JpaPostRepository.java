package com.example.yeogiserver.post.repository;

import com.example.yeogiserver.post.domain.Post;
import com.example.yeogiserver.post.domain.Theme;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface JpaPostRepository extends JpaRepository<Post, Long> {
    @Query("UPDATE Post p SET p.viewCount = p.viewCount + 1 WHERE p.id = :postId")
    @Modifying
    void addViewCount(Long postId);
    Optional<Post> findFirstByPostThemeListThemeOrderByViewCountDescCreatedAtDesc(Theme theme);

    List<Post> findAllByPostThemeListThemeOrderByViewCountDescCreatedAtDesc(Pageable pageable, Theme theme);
}
