package com.example.yeogiserver.mypage.application.dto;

import com.example.yeogiserver.mypage.domain.Pin;
import com.example.yeogiserver.post.domain.Post;

import java.time.LocalDateTime;

public record PinResponseDto(String x, String y, Long pinId, String nickname, Long postId, String country,
                             LocalDateTime createdAt) {
    public static PinResponseDto of(Pin pin) {
        Post post = pin.getPost();
        return new PinResponseDto(
                pin.getX(),
                pin.getY(),
                pin.getId(),
                pin.getMember().getNickname(),
                post.getId(),
                post.getCountry(),
                post.getCreatedAt()

        );
    }
}
