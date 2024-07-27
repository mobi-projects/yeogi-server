package com.example.yeogiserver.comment.application.dto;

import com.example.yeogiserver.comment.domain.Comment;

import java.time.LocalDateTime;

public record ReplyResponseDto(Long id, String content, String nickname,String profile, LocalDateTime createdAt, LocalDateTime modifiedAt,
                               Long likeCount) {

    public static ReplyResponseDto of(Comment comment) {
        return new ReplyResponseDto(
                comment.getId(),
                comment.getContent(),
                comment.getMember().getNickname(),
                comment.getMember().getProfile(),
                comment.getCreatedAt(),
                comment.getModifiedAt(),
                (long) comment.getCommentLikeList().size());
    }
}
