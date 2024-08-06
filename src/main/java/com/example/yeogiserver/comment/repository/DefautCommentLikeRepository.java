package com.example.yeogiserver.comment.repository;

import com.example.yeogiserver.comment.domain.CommentLike;
import com.example.yeogiserver.comment.domain.CommentLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class DefautCommentLikeRepository implements CommentLikeRepository {

    private final JpaCommentLikeRepository jpaCommentLikeRepository;

    @Override
    public void save(CommentLike commentLike) {
        jpaCommentLikeRepository.save(commentLike);
    }

    @Override
    public void delete(CommentLike commentLike) {
        jpaCommentLikeRepository.delete(commentLike);
    }

    @Override
    public Boolean existsByMemberEmailAndCommentId(String email, Long commentId) {
        return jpaCommentLikeRepository.existsByMemberEmailAndCommentId(email,commentId);
    }

    @Override
    public boolean existsByMemberIdAndCommentId(Long memberId, Long commentId) {
        return jpaCommentLikeRepository.existsByMemberIdAndCommentId(memberId,commentId);
    }

    @Override
    public Long countById(Long postId) {
        return jpaCommentLikeRepository.countById(postId);
    }
}
