package com.example.yeogiserver.member.domain;

import com.example.yeogiserver.base.TimeStamp;
import com.example.yeogiserver.member.dto.MemberDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
@ToString
@Table(indexes = {
        @Index(name = "member_email_index", columnList = "email"),
        @Index(name = "nickname_index", columnList = "nickname")
})
public class Member extends TimeStamp {

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

    private boolean isFirst;

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
                .isFirst(true)
                .build();
    }

    public void update(MemberDto updateMember) {
        this.nickname = updateMember.getNickname();
        this.ageRange = updateMember.getAgeRange();
        this.gender = updateMember.getGender();
        this.motto = updateMember.getMotto();
        this.isFirst = false;
    }

    public void setEncodePassword(String password) {
        this.password = password;
    }

    public void setProfile(String profile){
        this.profile = profile;
    }

    public void setBanner(String banner){
        this.banner = banner;
    }

    public void completeSignup(String nickname, Gender gender, String ageRange) {
        this.nickname = nickname;
        this.gender = gender;
        this.ageRange = ageRange;
        this.isFirst = false;
    }
}
