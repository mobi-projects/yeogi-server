package com.example.yeogiserver.comment.application.dto;

import com.example.yeogiserver.member.domain.Member;
import com.example.yeogiserver.comment.domain.Comment;
import com.example.yeogiserver.post.domain.Post;

public record CommentRequestDto(String email, String content, Long postId) {
    public CommentRequestDto of(String email, String content, Long postId) {return new CommentRequestDto(email,content,postId);}
}
