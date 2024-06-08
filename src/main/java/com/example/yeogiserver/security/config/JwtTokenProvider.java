package com.example.yeogiserver.security.config;

import com.example.yeogiserver.security.domain.CustomUserDetails;
import com.example.yeogiserver.security.domain.Token;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
@Getter
public class JwtTokenProvider {

    public static final String BEARER_TYPE = "Bearer";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String REFRESH_HEADER = "Refresh";
    public static final String BEARER_PREFIX = "Bearer ";

    @Value("${jwt.secret-key}")
    private String secretKey;

    private long accessTokenExpirationMillis = 30 * 60 * 1000L;

    private long refreshTokenExpirationMillis = 7 * 24 * 60 * 60 * 1000L;

    private Key key;


    @PostConstruct
    public void init() {
        String encodedSecretKey = encodeBase64SecretKey(secretKey);
        this.key = getKeyFromBase64EncodedKey(encodedSecretKey);
    }

    public String encodeBase64SecretKey(String secretKey) {
        return Encoders.BASE64.encode(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    private Key getKeyFromBase64EncodedKey(String base64EncodedSecretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(base64EncodedSecretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public Token generateToken(CustomUserDetails customUserDetails) {
        Date accessTokenExpiresIn = new Date(new Date().getTime() + accessTokenExpirationMillis);
        Date refreshTokenExpiresIn = new Date(new Date().getTime() + refreshTokenExpirationMillis);
        Map<String , Object> claims = new HashMap<>();
        claims.put("role" , customUserDetails.getRole());

        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setSubject(customUserDetails.getEmail())
                .setExpiration(accessTokenExpiresIn)
                .setIssuedAt(Calendar.getInstance().getTime())
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        String refreshToken = Jwts.builder()
                .setSubject(customUserDetails.getEmail())
                .setIssuedAt(Calendar.getInstance().getTime())
                .setExpiration(refreshTokenExpiresIn)
                .signWith(key)
                .compact();

        return Token.builder()
                .grantType(BEARER_TYPE)
                .authorizationType(AUTHORIZATION_HEADER)
                .accessToken(accessToken)
                .accessTokenExpiresIn(accessTokenExpiresIn.getTime())
                .refreshToken(refreshToken)
                .build();
    }


    public void setAccessToken(HttpServletResponse response, String accessToken) {
        String headerValue = BEARER_PREFIX + accessToken;
        response.setHeader(AUTHORIZATION_HEADER , headerValue);
    }

    public void setRefreshToken(HttpServletResponse response, String refreshToken) {
        response.setHeader("Refresh" , refreshToken);
    }
}
