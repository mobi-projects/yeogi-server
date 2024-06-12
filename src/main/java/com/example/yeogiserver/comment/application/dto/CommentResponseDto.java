package com.example.yeogiserver.comment.application.dto;

import com.example.yeogiserver.comment.domain.Comment;

import java.util.List;
import java.util.stream.Collectors;

public record CommentResponseDto(Comment comment, Long likeCount) {
    public static List<CommentResponseDto> toEntityList(List<Comment> comments, Long likeCount) {
        return comments.stream()
                .map(comment -> new CommentResponseDto(comment, likeCount))
                .collect(Collectors.toList());
    }

}

