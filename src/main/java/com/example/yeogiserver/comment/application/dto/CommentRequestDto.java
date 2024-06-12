package com.example.yeogiserver.comment.application.dto;

import com.example.yeogiserver.member.domain.Member;
import com.example.yeogiserver.comment.domain.Comment;

public record CommentRequestDto(String author, String content, Long postId) {
    public Comment toEntity() {
        return Comment.of(author, content,postId);
    }
}
