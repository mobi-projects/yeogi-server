package com.example.yeogiserver.security.domain;

public record SignupResponseDto(
        Long memberId,
        String email,
        boolean isFirst,
        Token token
) {
}
