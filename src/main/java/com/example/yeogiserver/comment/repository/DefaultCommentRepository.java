package com.example.yeogiserver.comment.repository;

import com.example.yeogiserver.comment.domain.Comment;
import com.example.yeogiserver.comment.domain.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class DefaultCommentRepository implements CommentRepository {

    private final JpaCommentRepository jpaCommentRepository;
    @Override
    public Comment saveComment(Comment comment) {
        return jpaCommentRepository.save(comment);
    }

    @Override
    public List<Comment> findByPostId(Long postId, Pageable pageable) {
        return jpaCommentRepository.findByPostId(postId,pageable);
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return jpaCommentRepository.findById(id);
    }

    @Override
    public void deleteByPostId(Long postId) {
        jpaCommentRepository.deleteByPostId(postId);
    }

    @Override
    public void deleteByCommentId(Long id) {
        jpaCommentRepository.deleteById(id);
    }

    @Override
    public Long countByPostId(Long postId) {
        return jpaCommentRepository.countByPostId(postId);
    }
}
