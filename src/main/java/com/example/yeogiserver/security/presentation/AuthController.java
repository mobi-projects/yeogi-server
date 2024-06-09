package com.example.yeogiserver.security.presentation;

import com.example.yeogiserver.security.application.AuthService;
import com.example.yeogiserver.security.config.JwtTokenProvider;
import com.example.yeogiserver.security.dto.Auth;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    @RequestMapping("logout")
    public ResponseEntity logout(HttpServletRequest request) {
        String refreshToken = jwtTokenProvider.resolveRefreshToken(request);
        String accessToken = jwtTokenProvider.resolveAccessToken(request);
        authService.logout(refreshToken , accessToken);

        return new ResponseEntity("로그아웃 되었습니다." , HttpStatus.OK);
    }

    @RequestMapping("reissue")
    public Auth.LoginResponse reissue(HttpServletRequest request) {
        String refreshToken = jwtTokenProvider.resolveRefreshToken(request);
        String accessToken = authService.reissue(refreshToken);

        Auth.LoginResponse response = new Auth.LoginResponse();
        response.setAccessToken("Bearer " + accessToken);

        return response;
    }
}
