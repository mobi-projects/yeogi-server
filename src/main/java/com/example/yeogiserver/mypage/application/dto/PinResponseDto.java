package com.example.yeogiserver.mypage.application.dto;

import com.example.yeogiserver.mypage.domain.Pin;

import java.util.List;
import java.util.stream.Collectors;

public record PinResponseDto(String x, String y, Long pinId, String nickname, Long postId) {
    public static List<PinResponseDto> of(List<Pin> pins) {
        return pins.stream()
                .map(pin -> new PinResponseDto(
                        pin.getX(),
                        pin.getY(),
                        pin.getId(),
                        pin.getMember().getNickname(),
                        pin.getPost().getId()
                )).collect(Collectors.toList());
    }
    public static PinResponseDto of(Pin pin) {
        return new PinResponseDto(
                        pin.getX(),
                        pin.getY(),
                        pin.getId(),
                        pin.getMember().getNickname(),
                        pin.getPost().getId()
                );
    }
}
