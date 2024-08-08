package com.example.yeogiserver.security.presentation;

import com.example.yeogiserver.security.application.AuthService;
import com.example.yeogiserver.security.config.JwtTokenProvider;
import com.example.yeogiserver.security.domain.SignupResponseDto;
import com.example.yeogiserver.security.domain.Token;
import com.example.yeogiserver.security.dto.Auth;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;
    @GetMapping("callback")
    public String callback(String code) {
        return code;
    }

    @GetMapping("generateToken/{registrationId}")
    @Operation(description = "프론트에서 token값을 받아 JWT Token 생성")
    public SignupResponseDto generateToken(@PathVariable(name = "registrationId") String registrationId , @RequestParam(name = "token") String token) {
        return authService.generateToken(registrationId , token);
    }

    @GetMapping("generateCode/{registrationId}")
    @Operation(description = "프론트에서 인가 코드를 받아 토큰 생성 [테스트 용도]")
    public String generateCode(@PathVariable(name = "registrationId") String registrationId , @RequestParam(name = "code") String code , @RequestParam(name = "redirect_uri") String redirectUri , @RequestParam(name="state" , required = false) String state) {
        return authService.generateCode(registrationId , code , redirectUri , state);
    }

    @GetMapping("logout")
    @Operation(description = "로그아웃")
    public ResponseEntity logout(HttpServletRequest request , HttpServletResponse response) throws IOException {
        String refreshToken = jwtTokenProvider.resolveRefreshToken(request);
        String accessToken = jwtTokenProvider.resolveAccessToken(request);
        authService.logout(refreshToken , accessToken , response);

        return new ResponseEntity("로그아웃 되었습니다." , HttpStatus.OK);
    }

    @GetMapping("reissue")
    @Operation(description = "refresh_token 을 이용하여 JWT token 재발급")
    public Auth.LoginResponse reissue(HttpServletRequest request , HttpServletResponse response) throws IOException {
        String refreshToken = jwtTokenProvider.resolveRefreshToken(request);
        String accessToken = authService.reissue(refreshToken , response);

        Auth.LoginResponse loginResponse = new Auth.LoginResponse();
        loginResponse.setAccessToken(accessToken);

        return loginResponse;
    }
}
