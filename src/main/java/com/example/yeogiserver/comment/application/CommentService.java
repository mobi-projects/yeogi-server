package com.example.yeogiserver.comment.application;

import com.example.yeogiserver.member.domain.Member;

import com.example.yeogiserver.member.repository.DefaultMemberRepository;
import com.example.yeogiserver.comment.application.dto.CommentRequestDto;
import com.example.yeogiserver.comment.application.dto.LikeRequestDto;
import com.example.yeogiserver.comment.domain.Comment;
import com.example.yeogiserver.comment.domain.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;

    private final DefaultMemberRepository memberRepository;

    public List<Comment> getComments(Long postId) {
        return commentRepository.findByPostId(postId);
    }

    public void saveComment(CommentRequestDto commentRequestDto) {
        //TODO. Session and Request Validate

        Comment comment = commentRequestDto.toEntity();

        Member author = memberRepository.findByEmail(comment.getAuthor().getEmail()).orElseThrow(() -> new IllegalArgumentException("Member not found"));
        commentRepository.saveComment(comment);
    }
    public void updateComment(Long id, CommentRequestDto commentRequestDto) {
        //TODO. Session and Request Validate

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        comment.of(commentRequestDto.content());

        commentRepository.saveComment(comment);
    }
    public void deleteComment(Long id) {
        //TODO. Session and Request Validate
        commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        commentRepository.deleteByCommentId(id);
    }
    public void deletePostComment(Long postId) {
        //TODO. Session and Request Validate

        commentRepository.deleteByPostId(postId);
    }


    public void saveLike(Long commentId,LikeRequestDto likeRequestDto) {
        commentRepository.findById(commentId).orElseThrow(() -> new RuntimeException("Comment not found"));


        commentRepository.saveLike(likeRequestDto.toEntity());

    }

}