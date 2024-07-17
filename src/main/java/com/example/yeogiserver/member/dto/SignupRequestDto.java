package com.example.yeogiserver.member.dto;

import com.example.yeogiserver.member.domain.Gender;

public record SignupRequestDto(
        Long memberId,
        String nickname,
        Gender gender,
        String ageRange // why string?
) {
}
