package com.example.yeogiserver.post.application.dto.response;

import com.example.yeogiserver.post.domain.Memo;

public record MemoResponseDto(
        Long memoId,
        String content,
        String address
) {
    public static MemoResponseDto of(Memo memo) {
        return new MemoResponseDto(
                memo.getId(),
                memo.getContent(),
                memo.getAddress()
        );
    }
}
