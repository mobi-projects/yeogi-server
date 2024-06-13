package com.example.yeogiserver.member.dto;

import com.example.yeogiserver.member.domain.Gender;
import com.example.yeogiserver.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDto {
    private Long id;
    private String email;
    private String nickname;
    private String ageRange;
    private Gender gender;
    private String profile;
    private String motto;
    private String banner;


    public static MemberDto of(Member member) {
        return MemberDto.builder()
                .id(member.getId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .ageRange(member.getAgeRange())
                .gender(member.getGender())
                .profile(member.getProfile())
                .motto(member.getMotto())
                .banner(member.getBanner())
                .build();
    }
}
