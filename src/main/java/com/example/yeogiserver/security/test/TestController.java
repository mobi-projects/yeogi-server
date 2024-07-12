package com.example.yeogiserver.security.test;

import com.example.yeogiserver.member.domain.Role;
import com.example.yeogiserver.security.config.JwtTokenProvider;
import com.example.yeogiserver.security.domain.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {
    private final JwtTokenProvider jwtTokenProvider;
    @GetMapping("/tokens")
    public Token foo(@RequestParam String email){
        return jwtTokenProvider.generateToken(email, Role.USER);
    }
}
