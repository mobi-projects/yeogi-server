package com.example.yeogiserver.mypage.repository;

import com.example.yeogiserver.mypage.domain.Pin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaPinRepository extends JpaRepository<Pin,Long> {

    Boolean existsByPostIdAndMember_Email(Long postId, String email);
    List<Pin> findByMember_Email(String email);
}
