package com.example.yeogiserver.post.application.dto.request;

public record MemoUpdateRequestDto(
        Long memoId,
        String content,
        String address
) {
}
