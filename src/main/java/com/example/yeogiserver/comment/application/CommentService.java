package com.example.yeogiserver.comment.application;

import com.example.yeogiserver.comment.application.dto.CommentRequestDto;
import com.example.yeogiserver.comment.application.dto.CommentResponseDto;
import com.example.yeogiserver.comment.domain.Comment;
import com.example.yeogiserver.comment.domain.CommentRepository;
import com.example.yeogiserver.comment.domain.LikeRepository;
import com.example.yeogiserver.member.application.MemberQueryService;
import com.example.yeogiserver.member.domain.Member;
import com.example.yeogiserver.post.domain.Post;
import com.example.yeogiserver.post.domain.PostRepository;
import com.example.yeogiserver.security.domain.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final MemberQueryService memberRepository;

    public List<CommentResponseDto> getComments(Long postId) {

        return CommentResponseDto.toEntityList(commentRepository.findByPostId(postId),likeRepository.countById(postId));
    }

    public void saveComment(CommentRequestDto commentRequestDto, CustomUserDetails userDetails) {
        //TODO. Session and Request Validate


        Member member = memberRepository.findMember(userDetails.getEmail());
        Post post = postRepository.findById(commentRequestDto.postId()).orElseThrow(() -> new IllegalArgumentException("Post not found"));

        commentRepository.saveComment(Comment.of(member,commentRequestDto.content(),post));
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

    public Long getCommentCount(Long postId) {
        return commentRepository.countByPostId(postId);
    }
}
