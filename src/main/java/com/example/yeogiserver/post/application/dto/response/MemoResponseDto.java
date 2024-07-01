package com.example.yeogiserver.post.application.dto.response;

import com.example.yeogiserver.post.domain.Memo;

public record MemoResponseDto(
        Long shortPostId,
        String content
) {
    public static MemoResponseDto of(Memo memo) {
        return new MemoResponseDto(
                memo.getId(),
                memo.getContent()
        );
    }
}
