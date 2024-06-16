package com.example.yeogiserver.comment.application.dto;

import com.example.yeogiserver.comment.domain.Comment;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record CommentResponseDto(Long id, String content, String nickname, Long postId, LocalDateTime createdAt, LocalDateTime modifiedAt, Long likeCount) {
    public static List<CommentResponseDto> toEntityList(List<Comment> comments, Long likeCount) {
        return comments.stream()
                .map(comment -> new CommentResponseDto(comment.getId(),comment.getContent()
                        ,comment.getMember().getNickname(),comment.getPost().getId(),comment.getCreatedAt(),comment.getModifiedAt(), likeCount))
                .collect(Collectors.toList());
    }

}

