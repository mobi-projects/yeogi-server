package com.example.yeogiserver.post.application.dto;

import com.example.yeogiserver.post.domain.Post;
import com.example.yeogiserver.post.domain.ShortPost;

import java.time.LocalDateTime;
import java.util.List;

public record PostResponseDto(
        String author,
        String title,
        String content,
        List<String> shortPostList,
        Long likeCount,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt,
        LocalDateTime tripStarDate,
        LocalDateTime tripEndDate,
        String region
        ) {
    public static PostResponseDto ofPost(Post post, Long likeCount) {
        return new PostResponseDto(
                post.getAuthor().getNickname(),
                post.getTitle(),
                post.getContent(),
                post.getShortPostList().stream().map(ShortPost::getContent).toList(),
                likeCount,
                post.getCreatedAt(),
                post.getModifiedAt(),
                post.getTripStarDate(),
                post.getTripEndDate(),
                post.getRegion()
        );
    }
}
