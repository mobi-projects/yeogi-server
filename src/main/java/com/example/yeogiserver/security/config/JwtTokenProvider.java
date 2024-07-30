package com.example.yeogiserver.security.config;

import com.example.yeogiserver.common.exception.CustomException;
import com.example.yeogiserver.common.exception.ErrorCode;
import com.example.yeogiserver.member.domain.Role;
import com.example.yeogiserver.security.application.CustomUserDetailService;
import com.example.yeogiserver.security.domain.CustomUserDetails;
import com.example.yeogiserver.security.domain.Token;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;

@Component
@Slf4j
@Getter
@RequiredArgsConstructor
public class JwtTokenProvider {

    public static final String BEARER_TYPE = "Bearer";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String REFRESH_HEADER = "Refresh";
    public static final String BEARER_PREFIX = "Bearer ";

    private final CustomUserDetailService customUserDetailService;

    @Value("${jwt.secret-key}")
    private String secretKey;

    private long accessTokenExpirationMillis = 60 * 60 * 1000L;

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

    public Token generateToken(String email, Role role) {
        log.info("Generate token for email: {}", email);
        log.info("Role: {}", role);

        Date accessTokenExpiresIn = new Date(new Date().getTime() + accessTokenExpirationMillis);
        Date refreshTokenExpiresIn = new Date(new Date().getTime() + refreshTokenExpirationMillis);
        Map<String , Object> claims = new HashMap<>();
        claims.put("role" , role);

        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setExpiration(accessTokenExpiresIn)
                .setIssuedAt(Calendar.getInstance().getTime())
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        String refreshToken = Jwts.builder()
                .setSubject(email)
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

    public String resolveAccessToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public String resolveRefreshToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(REFRESH_HEADER);
        if(StringUtils.hasText(bearerToken)){
            return bearerToken;
        }
        return null;
    }

    public Authentication getAuthentication(String token) {
        Claims claims = parseClaims(token);

        if(claims.get("role") == null) {
            throw new CustomException(ErrorCode.NO_ACCESS_TOKEN);
        }
        String email = claims.getSubject();
        if (Objects.isNull(email) || email.isEmpty()){
            throw new IllegalArgumentException("invalid claims");
        }

        String authority = claims.get("role").toString();

        CustomUserDetails customUserDetails = customUserDetailService.loadUserByUsername(email);

        log.info("# AuthMember.getRoles 권한 체크 = {}", customUserDetails.getAuthorities().toString());

        return new UsernamePasswordAuthenticationToken(customUserDetails , null , customUserDetails.getAuthorities());
    }



    public void setAccessToken(HttpServletResponse response, String accessToken) {
        String headerValue = BEARER_PREFIX + accessToken;
        response.setHeader(AUTHORIZATION_HEADER , headerValue);
    }

    public void setRefreshToken(HttpServletResponse response, String refreshToken) {
        response.setHeader("Refresh" , refreshToken);
    }

    public boolean validateToken(String token){
        try {
            parseClaims(token);
        } catch (io.jsonwebtoken.security.SecurityException e) {
            log.warn("잘못된 JWT 서명입니다.");
            throw new CustomException(ErrorCode.TOKEN_ILLEGAL_ARGUMENT);
        } catch (ExpiredJwtException e) {
            log.warn("만료된 JWT 토큰입니다.");
            throw new CustomException(ErrorCode.TOKEN_EXPIRED);
        } catch (UnsupportedJwtException e) {
            log.warn("지원되지 않는 JWT 토큰입니다.");
            throw new CustomException(ErrorCode.TOKEN_UNSUPPORTED);
        } catch (IllegalArgumentException e) {
            log.warn("JWT 토큰이 잘못되었습니다.");
            throw new CustomException(ErrorCode.TOKEN_ILLEGAL_ARGUMENT);
        }
        return true;
    }

    public Claims parseClaims(String token) {
        try {
            Claims body = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return body;
        } catch (Exception e){
            log.error("invalid token : {}", token);
            throw new IllegalArgumentException("invalid token : " + token);
        }
    }
}
