package com.example.yeogiserver.base.presentation.advice;

import com.example.yeogiserver.common.exception.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerAdvice {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleException(RuntimeException e) {

        if(e instanceof CustomException) {
            return ResponseEntity
                    .status(((CustomException) e).getErrorCode().getStatus())
                    .body(new ErrorResponse(((CustomException) e).getErrorCode().getMessage() , HttpStatus.valueOf(((CustomException) e).getErrorCode().getStatus())));
        }

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
