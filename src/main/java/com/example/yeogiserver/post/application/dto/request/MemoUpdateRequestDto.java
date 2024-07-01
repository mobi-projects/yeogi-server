package com.example.yeogiserver.post.application.dto.request;

public record MemoUpdateRequestDto(
        Long id,
        String memo,
        String address
) {
}
