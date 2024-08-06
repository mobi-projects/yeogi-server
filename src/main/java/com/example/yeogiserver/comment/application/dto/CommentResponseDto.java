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
                                 Long parentId,
                                 List<CommentResponseDto> children) {
    public static CommentResponseDto of(Comment comment, boolean hasLiked, List<CommentResponseDto> children) {
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
                comment.getParentId(),
                children
        );
    }
}