package com.example.yeogiserver.comment.application.dto;

public record CommentRequestDto(String content, Long postId) {
    public CommentRequestDto of(String content, Long postId) {return new CommentRequestDto(content,postId);}
}
