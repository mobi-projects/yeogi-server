package com.example.yeogiserver.security.domain;

import com.example.yeogiserver.common.exception.CustomException;
import com.example.yeogiserver.common.exception.ErrorCode;
import com.example.yeogiserver.member.domain.Gender;
import com.example.yeogiserver.member.domain.Member;
import com.example.yeogiserver.member.domain.Role;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Builder
@Getter
public class OAuth2UserInfo {

    private String name;
    private String email;
    private Gender gender;
    private String ageRange;
    private String profile;

    public static OAuth2UserInfo of(String registrationId , Map<String, Object> attributes) {
         switch (registrationId) {
            case "google" :
                return ofGoogle(attributes);
             case "naver" :
                 return ofNaver(attributes);
             case "kakao" :
                 return ofKakao(attributes);
            default :
                throw new CustomException(ErrorCode.ILLEGAL_REGISTRATION_ID);
        }
    }

    private static OAuth2UserInfo ofGoogle(Map<String, Object> attributes) {
        return OAuth2UserInfo.builder()
                .name(String.valueOf(attributes.get("name")))
                .email(String.valueOf(attributes.get("email")))
                .profile(String.valueOf(attributes.get("picture")))
                .build();
    }

    private static OAuth2UserInfo ofNaver(Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        return OAuth2UserInfo.builder()
                .name(String.valueOf(response.get("nickname")))
                .email(String.valueOf(response.get("email")))
                .profile(String.valueOf(response.get("profile_image")))
                .gender(String.valueOf(response.get("gender")).equals("M") ? Gender.M : Gender.F)
                .ageRange(String.valueOf(response.get("age")))
                .build();
    }

    private static OAuth2UserInfo ofKakao(Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");

        return OAuth2UserInfo.builder()
                .name(String.valueOf(kakaoProfile.get("nickname")))
                .email(String.valueOf(kakaoAccount.get("email")))
                .profile(String.valueOf(kakaoProfile.get("profile_image_url")))
                .build();
    }

    public Member toEntity() {
        return new Member(null , email , null , name , ageRange , gender , Role.USER);
    }

}
