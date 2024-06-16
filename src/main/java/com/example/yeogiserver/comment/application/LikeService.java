package com.example.yeogiserver.comment.application;

import com.example.yeogiserver.comment.domain.Comment;
import com.example.yeogiserver.comment.domain.CommentRepository;
import com.example.yeogiserver.comment.domain.Like;
import com.example.yeogiserver.comment.domain.LikeRepository;
import com.example.yeogiserver.member.domain.Member;
import com.example.yeogiserver.member.repository.DefaultMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeService {

    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;
    private final DefaultMemberRepository memberRepository;

    public void saveLike(String email, Long commentId) {
        likeRepository.existsByMemberEmailAndCommentId(email,commentId)
                .ifPresent((value)-> new IllegalArgumentException("Already Like : " + value));

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(()-> new IllegalArgumentException("Could not found member id : " + email));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()-> new IllegalArgumentException("Could not found comment id : " + commentId));

        likeRepository.save(Like.of(member,comment));

    }
    public void deleteLike(String email, Long commentId) {
        Like like = likeRepository.existsByMemberEmailAndCommentId(email,commentId)
                .orElseThrow(()-> new IllegalArgumentException("Could not found member id : " + email));
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(()-> new IllegalArgumentException("Could not found member id : " + email));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()-> new IllegalArgumentException("Could not found comment id : " + commentId));
        likeRepository.delete(Like.of(member,comment));
    }
}
