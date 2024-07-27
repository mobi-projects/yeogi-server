package com.example.yeogiserver.comment.application.dto;

import com.example.yeogiserver.comment.domain.Comment;

import java.time.LocalDateTime;
import java.util.List;

public record CommentResponseDto(Long id,
                                 String content,
                                 String nickname,
                                 String profile,
                                 Long postId,
                                 LocalDateTime createdAt,
                                 LocalDateTime modifiedAt,
                                 Long likeCount,
                                 boolean hasLiked,
                                 List<ReplyResponseDto> child) {
    public static CommentResponseDto of(Comment comment, boolean hasLiked) {
        return new CommentResponseDto(
                comment.getId(),
                comment.getContent(),
                comment.getMember().getNickname(),
                comment.getMember().getProfile(),
                comment.getPost().getId(),
                comment.getCreatedAt(),
                comment.getModifiedAt(),
                (long) comment.getCommentLikeList().size(),
                hasLiked,
                comment.getChildren().stream().map(ReplyResponseDto::of).toList());
    }
}