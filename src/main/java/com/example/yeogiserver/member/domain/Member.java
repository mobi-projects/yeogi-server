package com.example.yeogiserver.member.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private String nickname;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDate birthday;

    @Enumerated(EnumType.STRING)
    private Role role;

    public static Member of (String email , String password , String nickName , Gender gender , LocalDate birthday) {
        return Member.builder()
                .email(email)
                .password(password)
                .nickname(nickName)
                .birthday(birthday)
                .gender(gender)
                .role(Role.USER)
                .build();
    }

    public void setEncodePassword(String password) {
        this.password = password;
    }

}
