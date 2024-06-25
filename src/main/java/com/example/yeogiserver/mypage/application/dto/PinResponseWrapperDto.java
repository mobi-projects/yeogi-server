package com.example.yeogiserver.mypage.application.dto;

import com.example.yeogiserver.mypage.domain.Pin;

import java.util.List;

public record PinResponseWrapperDto(int count, List<PinResponseDto> pins) {
    public static PinResponseWrapperDto of(List<Pin> pins) {
        List<PinResponseDto> pinResponseDtos = PinResponseDto.of(pins);
        return new PinResponseWrapperDto(pinResponseDtos.size(), pinResponseDtos);
    }
}
