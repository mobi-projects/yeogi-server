package com.example.yeogiserver.security.application;

import com.example.yeogiserver.common.application.RedisService;
import com.example.yeogiserver.common.exception.CustomException;
import com.example.yeogiserver.common.exception.ErrorCode;
import com.example.yeogiserver.security.config.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisService redisService;

    public void logout(String refreshToken, String accessToken) {
        verifiedRefreshToken(refreshToken);
        Claims claims = jwtTokenProvider.parseClaims(refreshToken);
        String email = claims.getSubject();
        String redisRefreshToken = redisService.getValue(email);
        if(redisService.checkExistsValue(redisRefreshToken)) {
            redisService.deleteValues(email);

            long accessTokenExpirationMillis = jwtTokenProvider.getAccessTokenExpirationMillis();
            redisService.setValue(accessToken , "logout" , Duration.ofMillis(accessTokenExpirationMillis));
        }
    }

    private void verifiedRefreshToken(String refreshToken) {
        if(refreshToken == null) {
            throw new CustomException(ErrorCode.HEADER_REFRESH_TOKEN_NOT_EXISTS);
        }
    }
}
