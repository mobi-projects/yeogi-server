package com.example.yeogiserver.post.application.dto.response;

import com.example.yeogiserver.member.dto.LikedMembersInfo;
import com.example.yeogiserver.post.domain.Post;

import java.time.LocalDateTime;
import java.util.List;

public record PostResponseDto(
        Long postId,
        String author,
        String title,
        String content,
        List<ShortPostResponseDto> shortPostList,
        Long likeCount,
        List<LikedMembersInfo> likedMembersInfos,
        Long viewCount,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt,
        LocalDateTime tripStarDate,
        LocalDateTime tripEndDate,
        String continent,
        String region
        ) {
    public static PostResponseDto ofPost(Post post, Long likeCount, List<LikedMembersInfo> likedMembersInfos) {
        return new PostResponseDto(
                post.getId(),
                post.getAuthor().getNickname(),
                post.getTitle(),
                post.getContent(),
                post.getShortPostList().stream().map(ShortPostResponseDto::of).toList(),
                likeCount,
                likedMembersInfos,
                post.getViewCount(),
                post.getCreatedAt(),
                post.getModifiedAt(),
                post.getTripStarDate(),
                post.getTripEndDate(),
                post.getContinent(),
                post.getCountry()
        );
    }
}
