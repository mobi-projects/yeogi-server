package com.example.yeogiserver.mypage.application.dto;

import com.example.yeogiserver.mypage.domain.Pin;

public record PinRequestDto(String x, String y,Long postId) {
    public static PinRequestDto of(String x, String y, String email, Long postId) {return new PinRequestDto(x,y,postId);}
}
