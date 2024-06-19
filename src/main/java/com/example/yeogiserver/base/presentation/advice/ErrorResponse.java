package com.example.yeogiserver.base.presentation.advice;

import org.springframework.http.HttpStatus;

public record ErrorResponse(String message, HttpStatus status) {
}
