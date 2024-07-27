package com.example.yeogiserver.comment.application;

import com.example.yeogiserver.comment.domain.Comment;
import com.example.yeogiserver.comment.domain.CommentLike;
import com.example.yeogiserver.comment.domain.CommentRepository;
import com.example.yeogiserver.comment.domain.CommentLikeRepository;
import com.example.yeogiserver.member.domain.Member;
import com.example.yeogiserver.member.repository.DefaultMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeService {

    private final CommentLikeRepository commentLikeRepository;
    private final CommentRepository commentRepository;
    private final DefaultMemberRepository memberRepository;

    public void saveLike(String email, Long commentId) {

        if(commentLikeRepository.existsByMemberEmailAndCommentId(email,commentId)) throw new IllegalArgumentException("Already Like : " + email);

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(()-> new IllegalArgumentException("Could not found member id : " + email));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()-> new IllegalArgumentException("Could not found comment id : " + commentId));

        commentLikeRepository.save(CommentLike.of(member,comment));

    }
    public void deleteLike(String email, Long commentId) {
        if(!commentLikeRepository.existsByMemberEmailAndCommentId(email,commentId)) throw new IllegalArgumentException("Could not found like : " + email);

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(()-> new IllegalArgumentException("Could not found member id : " + email));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()-> new IllegalArgumentException("Could not found comment id : " + commentId));
        commentLikeRepository.delete(CommentLike.of(member,comment));
    }

    public boolean hasLiked(Long memberId, Long commentId) {
        return commentLikeRepository.existsByMemberIdAndCommentId(memberId, commentId);
    }
}
