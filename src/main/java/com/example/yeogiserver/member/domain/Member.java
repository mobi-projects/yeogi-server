package com.example.yeogiserver.member.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

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

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private Role role;

    public static Member of (String email , String password , String nickName , String ageRange , Gender gender) {
        return Member.builder()
                .email(email)
                .password(password)
                .nickname(nickName)
                .ageRange(ageRange)
                .gender(gender)
                .role(Role.USER)
                .build();
    }

    public void setEncodePassword(String password) {
        this.password = password;
    }

}
