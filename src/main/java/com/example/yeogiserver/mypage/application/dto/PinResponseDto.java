package com.example.yeogiserver.mypage.application.dto;

import com.example.yeogiserver.mypage.domain.Pin;

import java.util.List;

public record PinResponseDto(List<Pin> pin, int pinCount) {
    public static PinResponseDto of(List<Pin> pin) {
        return new PinResponseDto(pin, pin.size());
    }
}
