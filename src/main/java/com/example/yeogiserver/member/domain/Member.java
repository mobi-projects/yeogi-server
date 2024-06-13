package com.example.yeogiserver.member.domain;

import com.example.yeogiserver.member.dto.MemberDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
@ToString
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    private String password;

    private String nickname;

    private String ageRange;

    private String profile;

    private String motto;

    private String banner;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private Role role;

    public static Member of (String email , String password , String nickName , String ageRange , String profile , String motto , String banner , Gender gender) {
        return Member.builder()
                .email(email)
                .password(password)
                .nickname(nickName)
                .ageRange(ageRange)
                .profile(profile)
                .motto(motto)
                .banner(banner)
                .gender(gender)
                .role(Role.USER)
                .build();
    }

    public void update(MemberDto updateMember) {
        this.nickname = updateMember.getNickname();
        this.ageRange = updateMember.getAgeRange();
        this.gender = updateMember.getGender();
        this.motto = updateMember.getMotto();
    }

    public void setEncodePassword(String password) {
        this.password = password;
    }

}
