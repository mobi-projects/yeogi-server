package com.example.yeogiserver.comment.application.dto;

import com.example.yeogiserver.comment.domain.Comment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record ReplyResponseDto(Long id, String content, String nickname, LocalDateTime createdAt, LocalDateTime modifiedAt) {
    public static List<ReplyResponseDto> of(List<Comment> comments) {
        return comments.stream()
                .map(comment -> new ReplyResponseDto(
                        comment.getId(),
                        comment.getContent(),
                        comment.getMember().getNickname(),
                        comment.getCreatedAt(),
                        comment.getModifiedAt()))
                .collect(Collectors.toList());
    }

}
