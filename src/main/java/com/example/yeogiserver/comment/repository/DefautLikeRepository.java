package com.example.yeogiserver.comment.repository;

import com.example.yeogiserver.comment.domain.Like;
import com.example.yeogiserver.comment.domain.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class DefautLikeRepository  implements LikeRepository {
    private final JpaLikeRepository jpaLikeRepository;
    @Override
    public void save(Like like) {
        jpaLikeRepository.save(like);
    }

    @Override
    public void delete(Like like) {
        jpaLikeRepository.delete(like);
    }

    @Override
    public Boolean existsByMemberEmailAndCommentId(String email, Long commentId) {
        return jpaLikeRepository.existsByMemberEmailAndCommentId(email,commentId);
    }

    @Override
    public Long countById(Long postId) {
        return jpaLikeRepository.countById(postId);
    }
}
