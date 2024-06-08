package com.example.yeogiserver.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    //MEMBER
    MEMBER_NOT_FOUND("회원을 찾을 수 없습니다."),
    MEMBER_ROLE_DOES_NOT_EXISTS("회원 권한이 존재하지 않습니다."),
    MEMBER_ROLE_INVALID("회원 권한이 유효하지 않습니다."),
    MEMBER_IS_LOGOUT("로그아웃한 회원 입니다.");



    private String message;
}
