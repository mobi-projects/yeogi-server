package com.example.yeogiserver.reply.application.dto;


import com.example.yeogiserver.member.domain.Member;
import com.example.yeogiserver.reply.domain.Comment;
import com.example.yeogiserver.reply.domain.Like;

public record LikeRequestDto(Member author, Comment comment) {

    public Like toEntity () {
        return Like.of(author,comment);
    }

}
