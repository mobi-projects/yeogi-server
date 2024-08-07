package com.example.yeogiserver.mypage.application.dto;

public record PinRequestDto(Long postId) {
    public static PinRequestDto of(Long postId) {return new PinRequestDto(postId);}
}
