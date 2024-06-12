package com.example.yeogiserver.comment.repository;

import com.example.yeogiserver.comment.domain.Comment;
import com.example.yeogiserver.comment.domain.CommentRepository;
import com.example.yeogiserver.comment.domain.Like;
import lombok.RequiredArgsConstructor;
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
    public List<Comment> findByPostId(Long postId) {
        return jpaCommentRepository.findByPostId(postId);
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


}
