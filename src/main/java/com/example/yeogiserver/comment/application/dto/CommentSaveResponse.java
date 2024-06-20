package com.example.yeogiserver.comment.application.dto;

import com.example.yeogiserver.comment.domain.Comment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record CommentSaveResponse(Long id, String content, String nickname, LocalDateTime createdAt, LocalDateTime modifiedAt,Long postId) {
    public static CommentSaveResponse of(Comment comment) {
        return new CommentSaveResponse(
                        comment.getId(),
                        comment.getContent(),
                        comment.getMember().getNickname(),
                        comment.getCreatedAt(),
                        comment.getModifiedAt(),
                        comment.getPost().getId()
                );
    }

}
