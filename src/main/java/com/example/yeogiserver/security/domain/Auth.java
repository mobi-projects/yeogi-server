package com.example.yeogiserver.security.domain;

import com.example.yeogiserver.member.domain.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class Auth {

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Login{
        private String email;
        private String password;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class LoginResponse{
        private String email;
        private String nickname;
        private Role role;
    }
}
