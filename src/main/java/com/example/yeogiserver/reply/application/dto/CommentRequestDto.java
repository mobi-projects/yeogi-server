package com.example.yeogiserver.reply.application.dto;

import com.example.yeogiserver.member.domain.Member;
import com.example.yeogiserver.reply.domain.Comment;

public record CommentRequestDto(Member author, String content, Long postId) {
    public Comment toEntity() {
        return Comment.of(author, content,postId);
    }
}
