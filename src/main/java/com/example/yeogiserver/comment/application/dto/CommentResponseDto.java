package com.example.yeogiserver.comment.application.dto;

import com.example.yeogiserver.comment.domain.Comment;

import java.time.LocalDateTime;
import java.util.List;

public record CommentResponseDto(Long id,
                                 String content,
                                 String nickname,
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
                comment.getPost().getId(),
                comment.getCreatedAt(),
                comment.getModifiedAt(),
                (long) comment.getLikeList().size(),
                hasLiked,
                ReplyResponseDto.of(comment.getChildren()))
                ;
    }
}

