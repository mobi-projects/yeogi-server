package com.example.yeogiserver.common.exception;

import lombok.Getter;

import java.io.Serializable;

@Getter
public class customException extends RuntimeException implements Serializable {

    public customException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    private final ErrorCode errorCode;
}
