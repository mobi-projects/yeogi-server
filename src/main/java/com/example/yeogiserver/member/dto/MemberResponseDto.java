package com.example.yeogiserver.member.dto;

import com.example.yeogiserver.member.domain.Gender;
import com.example.yeogiserver.member.domain.Member;

public record MemberResponseDto(Long memberId,
                                String email,
                                String nickname,
                                String ageRange,
                                String profile,
                                String motto,
                                String banner,
                                Gender gender
) {
    public static MemberResponseDto of(Member member) {
        return new MemberResponseDto(
                member.getId(),
                member.getEmail(),
                member.getNickname(),
                member.getAgeRange(),
                member.getProfile(),
                member.getMotto(),
                member.getBanner(),
                member.getGender()
        );
    }
}
