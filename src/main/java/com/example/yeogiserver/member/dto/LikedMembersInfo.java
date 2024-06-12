package com.example.yeogiserver.member.dto;

import com.example.yeogiserver.member.domain.Member;

public record LikedMembersInfo(
        Long userId,
        String nickname
) {
    public static LikedMembersInfo of(Member member) {
        return new LikedMembersInfo(
                member.getId(),
                member.getNickname()
        );
    }
}
