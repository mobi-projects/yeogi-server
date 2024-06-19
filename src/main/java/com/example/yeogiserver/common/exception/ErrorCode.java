package com.example.yeogiserver.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    //MEMBER
    MEMBER_NOT_FOUND(300 , "회원을 찾을 수 없습니다."),
    MEMBER_EMAIL_ALREADY_EXISTS(301 , "이미 가입한 이메일이 있습니다."),
    MEMBER_IS_LOGOUT(302 , "로그아웃한 회원 입니다."),
    MEMBER_ROLE_DOES_NOT_EXISTS(303 , "회원 권한이 존재하지 않습니다."),
    MEMBER_ROLE_INVALID(303 , "회원 권한이 유효하지 않습니다."),

    //TOKEN
    TOKEN_EXPIRED(400 , "토큰이 만료되었습니다."),
    NO_ACCESS_TOKEN(403 , "토큰에 권한 정보가 존재하지 않습니다."),
    TOKEN_UNSUPPORTED(403 , "지원하지 않는 형식의 토큰입니다."),
    TOKEN_ILLEGAL_ARGUMENT(403 , "올바르지 않은 토큰입니다."),
    HEADER_REFRESH_TOKEN_NOT_EXISTS(404 , "헤더에 Refresh 토큰이 존재하지 않습니다."),

    //OAuth
    ILLEGAL_REGISTRATION_ID(400 , "올바르지 않은 registrationId 입니다.");



    private int status;
    private String message;
}
