package com.example.yeogiserver.post.application.dto.response;

import com.example.yeogiserver.post.domain.Post;
import com.example.yeogiserver.post.domain.PostTheme;
import com.example.yeogiserver.post.domain.Theme;

import java.time.LocalDateTime;
import java.util.List;

public record PostListResponseDto(
        Long postId,
        String author,
        String title,
        Long commentCount,
        Long likeCount, // fetch join ?
        Long viewCount,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt,
        String continent,
        String region,
        String address,
        List<Theme> themeList
        // thumbnail
) {
    public static PostListResponseDto of(Post post, Long commentCount, Long likeCount) {
        return new PostListResponseDto(
                post.getId(),
                post.getAuthor().getNickname(),
                post.getTitle(),
                commentCount,
                likeCount,
                post.getViewCount(),
                post.getCreatedAt(),
                post.getModifiedAt(),
                post.getContinent(),
                post.getCountry(),
                post.getAddress(),
                post.getPostThemeList().stream().map(PostTheme::getTheme).toList()
        );
    }
}
