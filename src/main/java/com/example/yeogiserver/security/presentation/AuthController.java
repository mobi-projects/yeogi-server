package com.example.yeogiserver.security.presentation;

import com.example.yeogiserver.security.application.AuthService;
import com.example.yeogiserver.security.config.JwtTokenProvider;
import com.example.yeogiserver.security.dto.Auth;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    @RequestMapping("logout")
    public ResponseEntity logout(HttpServletRequest request , HttpServletResponse response) throws IOException {
        String refreshToken = jwtTokenProvider.resolveRefreshToken(request);
        String accessToken = jwtTokenProvider.resolveAccessToken(request);
        authService.logout(refreshToken , accessToken , response);

        return new ResponseEntity("로그아웃 되었습니다." , HttpStatus.OK);
    }

    @RequestMapping("reissue")
    public Auth.LoginResponse reissue(HttpServletRequest request , HttpServletResponse response) throws IOException {
        String refreshToken = jwtTokenProvider.resolveRefreshToken(request);
        String accessToken = authService.reissue(refreshToken , response);

        Auth.LoginResponse loginResponse = new Auth.LoginResponse();
        loginResponse.setAccessToken(accessToken);

        return loginResponse;
    }
}
