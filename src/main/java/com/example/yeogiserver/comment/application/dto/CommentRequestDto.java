package com.example.yeogiserver.comment.application.dto;

import com.example.yeogiserver.member.domain.Member;
import com.example.yeogiserver.comment.domain.Comment;
import com.example.yeogiserver.post.domain.Post;

public record CommentRequestDto(String content, Long postId) {
    public CommentRequestDto of(String content, Long postId) {return new CommentRequestDto(content,postId);}
}
