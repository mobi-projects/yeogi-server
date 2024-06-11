package com.example.yeogiserver.member.domain;

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

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private Role role;

    public static Member of (String email , String password , String nickName , String ageRange , String profile , Gender gender) {
        return Member.builder()
                .email(email)
                .password(password)
                .nickname(nickName)
                .ageRange(ageRange)
                .profile(profile)
                .gender(gender)
                .role(Role.USER)
                .build();
    }

    public void setEncodePassword(String password) {
        this.password = password;
    }

}
