package com.example.yeogiserver.member.dto;

import com.example.yeogiserver.member.domain.Gender;
import com.example.yeogiserver.member.domain.Keyword;
import com.example.yeogiserver.member.domain.Member;

import java.util.ArrayList;
import java.util.List;

public record MemberResponseDto(Long id,
                                String email,
                                String nickname,
                                String ageRange,
                                String profile,
                                String motto,
                                String banner,
                                Gender gender,

                                List<KeywordDto> keywordList
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
                member.getGender(),
                member.getKeywordList().stream().map((keyword) -> new KeywordDto(keyword.getTitle())).toList()
        );
    }
}
