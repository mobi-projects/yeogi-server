package com.example.yeogiserver.security.application;

import com.example.yeogiserver.common.application.RedisService;
import com.example.yeogiserver.common.exception.CustomException;
import com.example.yeogiserver.common.exception.ErrorCode;
import com.example.yeogiserver.member.domain.Member;
import com.example.yeogiserver.member.repository.DefaultMemberRepository;
import com.example.yeogiserver.security.config.JwtTokenProvider;
import com.example.yeogiserver.security.domain.CustomUserDetails;
import com.example.yeogiserver.security.domain.OAuth2UserInfo;
import com.example.yeogiserver.security.domain.Token;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisService redisService;
    private final DefaultMemberRepository memberRepository;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String kakaoClientId;
    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    private String kakaoClientSecret;
    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String googleClientId;
    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String googleClientSecret;
    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
    private String naverClientId;
    @Value("${spring.security.oauth2.client.registration.naver.client-secret}")
    private String naverClientSecret;
    private Map<String , String> tokenMap = Map.of(
            "kakao" , "https://kauth.kakao.com/oauth/token",
            "google","https://oauth2.googleapis.com/token",
            "naver" ,"https://nid.naver.com/oauth2.0/token"
    );

    private Map<String , String> propertyMap = Map.of(
            "kakao" , "https://kapi.kakao.com/v2/user/me",
            "google" , "https://www.googleapis.com/oauth2/v3/userinfo",
            "naver","https://openapi.naver.com/v1/nid/me"
    );

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

    public Token generateToken(String registrationId, String code , String redirectUri , String state) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        MultiValueMap<String, String> params = null;

        switch (registrationId) {
            case "kakao" :
                params = accessTokenKaKao(code , redirectUri);
                break;
            case "google" :
                params = accessTokenGoogle(code , redirectUri);
                break;
            case "naver" :
                params = accessTokenNaver(code , redirectUri , state);
        }

        HttpEntity<MultiValueMap<String, String>> requestBody = new HttpEntity<>(params, headers);

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Object> response = restTemplate.exchange(
                tokenMap.get(registrationId),
                HttpMethod.POST,
                requestBody,
                Object.class
        );

        LinkedHashMap<String , String> map = (LinkedHashMap<String, String>) response.getBody();

        LinkedHashMap<String, Object> property = generateProperty(registrationId , map.get("access_token"));

        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfo.of(registrationId, property);

        Member member = saveOrUpdate(oAuth2UserInfo);

        CustomUserDetails customUserDetails = CustomUserDetails.of(member);

        Token token = jwtTokenProvider.generateToken(customUserDetails);

        long refreshTokenExpirationMillis = jwtTokenProvider.getRefreshTokenExpirationMillis();
        redisService.setValue(member.getEmail() , token.getRefreshToken() , Duration.ofMillis(refreshTokenExpirationMillis));

        return token;
    }

    private LinkedHashMap<String, Object> generateProperty(String registrationId , String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer "+ accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);

        RestTemplate rt = new RestTemplate();

        ResponseEntity<Object> response = rt.exchange(
                propertyMap.get(registrationId),
                HttpMethod.POST,
                request,
                Object.class
        );

        return (LinkedHashMap<String, Object>) response.getBody();
    }

    private MultiValueMap<String, String> accessTokenKaKao(String code , String redirectUri) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id" , kakaoClientId);
        params.add("redirect_uri", redirectUri);
        params.add("code", code);
        params.add("client_secret" , kakaoClientSecret);
        return params;
    }

    private MultiValueMap<String, String> accessTokenGoogle(String code , String redirectUri) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id" , googleClientId);
        params.add("redirect_uri", redirectUri);
        params.add("code", code);
        params.add("client_secret" , googleClientSecret);
        return params;
    }

    private MultiValueMap<String, String> accessTokenNaver(String code , String redirectUri ,  String state) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id" , naverClientId);
        params.add("redirect_uri", redirectUri);
        params.add("code", code);
        params.add("client_secret" , naverClientSecret);
        params.add("state" , state);
        return params;
    }

    private Member findMember(String email) {
        return memberRepository.findByEmail(email).orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOT_FOUND));
    }

    private Member saveOrUpdate(OAuth2UserInfo oAuth2UserInfo) {
        Member member = memberRepository.findByEmail(oAuth2UserInfo.getEmail())
                .orElse(oAuth2UserInfo.toEntity());
        return memberRepository.save(member);
    }
}
