package com.example.yeogiserver.security.filter;

import com.example.yeogiserver.common.application.RedisService;
import com.example.yeogiserver.member.application.MemberQueryService;
import com.example.yeogiserver.member.domain.Member;
import com.example.yeogiserver.security.config.JwtTokenProvider;
import com.example.yeogiserver.security.dto.Auth;
import com.example.yeogiserver.security.domain.CustomUserDetails;
import com.example.yeogiserver.security.domain.Token;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.time.Duration;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberQueryService memberQueryService;
    private final RedisService redisService;


    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        ObjectMapper objectMapper = new ObjectMapper();
        Auth.Login login = objectMapper.readValue(request.getInputStream(), Auth.Login.class);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(login.getEmail(), login.getPassword());

        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        CustomUserDetails customUserDetails = (CustomUserDetails) authResult.getPrincipal();
        Token token = jwtTokenProvider.generateToken(customUserDetails);
        String accessToken = token.getAccessToken();
        String refreshToken = token.getRefreshToken();

        Member member = memberQueryService.findMember(customUserDetails.getEmail());
        loginSuccessResponse(response , accessToken , refreshToken);

        long refreshTokenExpirationMillis = jwtTokenProvider.getRefreshTokenExpirationMillis();
        redisService.setValue(member.getEmail() , refreshToken , Duration.ofMillis(refreshTokenExpirationMillis));

        getSuccessHandler().onAuthenticationSuccess(request , response , authResult);
    }

    public void loginSuccessResponse(HttpServletResponse response , String accessToken , String refreshToken){
        response.setHeader("Authorization" , "Bearer " + accessToken);
        response.setHeader("Refresh" , refreshToken);
    }
}
