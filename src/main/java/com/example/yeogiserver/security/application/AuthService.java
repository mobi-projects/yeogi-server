package com.example.yeogiserver.security.application;

import com.example.yeogiserver.common.application.RedisService;
import com.example.yeogiserver.common.exception.CustomException;
import com.example.yeogiserver.common.exception.ErrorCode;
import com.example.yeogiserver.member.domain.Member;
import com.example.yeogiserver.member.repository.DefaultMemberRepository;
import com.example.yeogiserver.security.config.JwtTokenProvider;
import com.example.yeogiserver.security.domain.CustomUserDetails;
import com.example.yeogiserver.security.domain.Token;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisService redisService;
    private final DefaultMemberRepository memberRepository;

    public void logout(String refreshToken, String accessToken , HttpServletResponse response) throws IOException {
        verifiedRefreshToken(refreshToken , response);
        Claims claims = jwtTokenProvider.parseClaims(refreshToken);
        String email = claims.getSubject();
        String redisRefreshToken = redisService.getValue(email);
        if(redisService.checkExistsValue(redisRefreshToken)) {
            redisService.deleteValues(email);

            long accessTokenExpirationMillis = jwtTokenProvider.getAccessTokenExpirationMillis();
            redisService.setValue(accessToken , "logout" , Duration.ofMillis(accessTokenExpirationMillis));
        }
    }

    public String reissue(String refreshToken , HttpServletResponse response) throws IOException {
        verifiedRefreshToken(refreshToken , response);
        Claims claims = jwtTokenProvider.parseClaims(refreshToken);
        String email = claims.getSubject();
        String redisRefreshToken = redisService.getValue(email);

        if(redisService.checkExistsValue(redisRefreshToken) && refreshToken.equals(redisRefreshToken)) {
            Member member = findMember(email);
            CustomUserDetails customUserDetails = CustomUserDetails.of(member);
            Token token = jwtTokenProvider.generateToken(customUserDetails);
            String accessToken = token.getAccessToken();
            long refreshTokenExpirationMillis = jwtTokenProvider.getRefreshTokenExpirationMillis();

            redisService.setValue(refreshToken , accessToken , Duration.ofMillis(refreshTokenExpirationMillis));
            return accessToken;
        }
        throw new CustomException(ErrorCode.TOKEN_ILLEGAL_ARGUMENT);
    }

    private void verifiedRefreshToken(String refreshToken , HttpServletResponse response) throws IOException {
        if(refreshToken == null) {
            log.warn(ErrorCode.HEADER_REFRESH_TOKEN_NOT_EXISTS.getMessage());
            response.sendError(ErrorCode.HEADER_REFRESH_TOKEN_NOT_EXISTS.getStatus() , ErrorCode.HEADER_REFRESH_TOKEN_NOT_EXISTS.getMessage());
        }
    }

    private Member findMember(String email) {
        return memberRepository.findByEmail(email).orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
    }

}
