package com.example.yeogiserver.post.application.dto.response;

import com.example.yeogiserver.post.domain.ShortPost;

public record ShortPostResponseDto(
        Long shortPostId,
        String content
) {
    public static ShortPostResponseDto of(ShortPost shortPost) {
        return new ShortPostResponseDto(
                shortPost.getId(),
                shortPost.getContent()
        );
    }
}
