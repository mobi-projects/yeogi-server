package com.example.yeogiserver.global;

import org.springframework.http.HttpStatus;

public record ErrorResponse(String message, HttpStatus status) {
}
