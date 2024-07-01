package com.example.yeogiserver.post.application.dto.response;

import com.example.yeogiserver.member.dto.LikedMembersInfo;
import com.example.yeogiserver.post.domain.Post;
import com.example.yeogiserver.post.domain.PostTheme;
import com.example.yeogiserver.post.domain.Theme;

import java.time.LocalDateTime;
import java.util.List;

public record PostResponseDto(
        Long postId,
        String author,
        String title,
        String content,
        List<MemoResponseDto> shortPostList,
        Long likeCount,
        List<LikedMembersInfo> likedMembersInfos,
        Long viewCount,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt,
        LocalDateTime tripStartDate,
        LocalDateTime tripEndDate,
        String continent,
        String region,
        String address,
        List<Theme> themeList
        ) {
    public static PostResponseDto ofPost(Post post, Long likeCount, List<LikedMembersInfo> likedMembersInfos) {
        return new PostResponseDto(
                post.getId(),
                post.getAuthor().getNickname(),
                post.getTitle(),
                post.getContent(),
                post.getMemoList().stream().map(MemoResponseDto::of).toList(),
                likeCount,
                likedMembersInfos,
                post.getViewCount(),
                post.getCreatedAt(),
                post.getModifiedAt(),
                post.getTripStarDate(),
                post.getTripEndDate(),
                post.getContinent(),
                post.getCountry(),
                post.getAddress(),
                post.getPostThemeList().stream().map(PostTheme::getTheme).toList()
        );
    }
}
