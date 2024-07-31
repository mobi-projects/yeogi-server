package com.example.yeogiserver.base.presentation.advice;

import com.example.yeogiserver.common.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@RestControllerAdvice
@Slf4j
public class GlobalControllerAdvice {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleException(RuntimeException e) {

        if(e instanceof CustomException) {

            ZonedDateTime dateTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));

            log.warn("[ERROR] TIME = {} , MESSAGE = {}" , dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) , ((CustomException) e).getErrorCode().getMessage());

            return ResponseEntity
                    .status(((CustomException) e).getErrorCode().getStatus())
                    .body(new ErrorResponse(((CustomException) e).getErrorCode().getMessage() , HttpStatus.valueOf(((CustomException) e).getErrorCode().getStatus())));
        }

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
