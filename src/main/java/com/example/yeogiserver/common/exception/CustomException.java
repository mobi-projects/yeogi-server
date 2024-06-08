package com.example.yeogiserver.common.exception;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class CustomException extends RuntimeException implements Serializable {

    public CustomException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    private final ErrorCode errorCode;
}
