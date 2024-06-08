package com.example.yeogiserver.post.repository;

import com.example.yeogiserver.post.domain.ShortPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMemoRepository extends JpaRepository<ShortPost, Long> {
}
