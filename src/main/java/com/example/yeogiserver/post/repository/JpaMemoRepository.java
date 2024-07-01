package com.example.yeogiserver.post.repository;

import com.example.yeogiserver.post.domain.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMemoRepository extends JpaRepository<Memo, Long> {
}
