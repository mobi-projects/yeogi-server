package com.example.yeogiserver.member.dto;

import com.example.yeogiserver.member.domain.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class SignupMember {

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {
        private String email;
        private String password;
        private Gender gender;
        private String nickname;
        private LocalDate birthday;
    }


    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response{
        private String email;
        private String nickname;
    }
}
