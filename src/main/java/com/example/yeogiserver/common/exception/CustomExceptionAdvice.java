package com.example.yeogiserver.common.exception;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@RestControllerAdvice
@Slf4j
public class CustomExceptionAdvice {

    @ExceptionHandler(CustomException.class)
    public void errorHandler(HttpServletResponse response , CustomException e) throws IOException {
        log.warn("Exception = {} " , e.getErrorCode().getMessage());
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(e.getErrorCode().getStatus());
        response.getWriter().print(e.getErrorCode().getMessage());
    }
}
