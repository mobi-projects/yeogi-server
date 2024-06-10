package com.example.yeogiserver.comment.application.dto;


import com.example.yeogiserver.member.domain.Member;
import com.example.yeogiserver.comment.domain.Like;

public record LikeRequestDto(Member author) {

    public Like toEntity () {
        return Like.of(author);
    }

}
