package com.example.yeogiserver.comment.application;

import com.example.yeogiserver.comment.application.dto.CommentRequestDto;
import com.example.yeogiserver.comment.application.dto.CommentResponseDto;
import com.example.yeogiserver.comment.application.dto.CommentSaveResponse;
import com.example.yeogiserver.comment.domain.Comment;
import com.example.yeogiserver.comment.domain.CommentRepository;
import com.example.yeogiserver.event.recommand.RecommandEvent;
import com.example.yeogiserver.member.application.MemberQueryService;
import com.example.yeogiserver.member.domain.Member;
import com.example.yeogiserver.post.application.PostService;
import com.example.yeogiserver.post.domain.Post;
import com.example.yeogiserver.security.domain.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {
    private final CommentRepository commentRepository;
    private final CommentLikeService commentLikeService;
    private final PostService postService;
    private final MemberQueryService memberQueryService;
    private final ApplicationEventPublisher applicationEventPublisher;

    public List<CommentResponseDto> getComments(Long postId, Long memberId, Pageable pageable) {

        List<Comment> commnetEntityList = commentRepository.findByPostId(postId, pageable);

        return commnetEntityList
                .stream()
                .map(each -> getCommentResponseDto(memberId, each))
                .toList();
    }

    private CommentResponseDto getCommentResponseDto(Long memberId, Comment each) {
        boolean hasLiked = false;

        if (memberId != null){
            hasLiked = commentLikeService.hasLiked(memberId, each.getId());
        }

        return CommentResponseDto.of(each, hasLiked);
    }

    public CommentSaveResponse addComment(CommentRequestDto commentRequestDto, CustomUserDetails userDetails) {

        Member member = memberQueryService.findMember(userDetails.getEmail());
        Post post = postService.getPost(commentRequestDto.postId());
        applicationEventPublisher.publishEvent(new RecommandEvent(this,post.getId(),member.getId()));

        return CommentSaveResponse.of(commentRepository.saveComment(Comment.of(member,commentRequestDto.content(),post)));
    }
    public CommentSaveResponse addReply(CommentRequestDto commentRequestDto,CustomUserDetails userDetails, Long commentId) {
        Member member = memberQueryService.findMember(userDetails.getEmail());
        Post post = postService.getPost(commentRequestDto.postId());

        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new RuntimeException("Comment Not Found"));
        Comment child = Comment.of(member,commentRequestDto.content(),post);

        child.updateParent(comment.getId());
        commentRepository.saveComment(child);
        return CommentSaveResponse.of(child);
    }

    public CommentSaveResponse updateComment(Long id, CommentRequestDto commentRequestDto) {

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        comment.update(commentRequestDto.content());
        return CommentSaveResponse.of(comment);
    }

    public void deleteComment(Long id, Long memberId) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        if (!comment.getMember().getId().equals(memberId)){
            throw new IllegalArgumentException("Not my Comment");
        }

        commentRepository.delete(comment);
    }

    public Long getCommentCount(Long postId) {
        return commentRepository.countByPostId(postId);
    }
}
