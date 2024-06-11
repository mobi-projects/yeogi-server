package com.example.yeogiserver.security.handler;

import com.example.yeogiserver.common.application.RedisService;
import com.example.yeogiserver.member.domain.Member;
import com.example.yeogiserver.security.config.JwtTokenProvider;
import com.example.yeogiserver.security.domain.CustomOAuth2User;
import com.example.yeogiserver.security.domain.CustomUserDetails;
import com.example.yeogiserver.security.domain.Token;
import com.example.yeogiserver.security.dto.Auth;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.Duration;

@Component
@Slf4j
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisService redisService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {

        System.out.println("onAuthenticationSuccess");

        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

        Member member = oAuth2User.getMember();

        CustomUserDetails customUserDetails = CustomUserDetails.of(member);

        Token token = jwtTokenProvider.generateToken(customUserDetails);

        long refreshTokenExpirationMillis = jwtTokenProvider.getRefreshTokenExpirationMillis();
        redisService.setValue(member.getEmail() , token.getRefreshToken() , Duration.ofMillis(refreshTokenExpirationMillis));

        loginSuccessResponse(token , response);
    }

    private void loginSuccessResponse(Token token , HttpServletResponse response) throws IOException {

        Gson gson = new Gson();

        Auth.LoginResponse loginResponse = Auth.LoginResponse.builder()
                        .accessToken("Bearer " + token.getAccessToken())
                                .refreshToken(token.getRefreshToken())
                                        .build();

        response.getWriter().write(gson.toJson(loginResponse));
    }
}
