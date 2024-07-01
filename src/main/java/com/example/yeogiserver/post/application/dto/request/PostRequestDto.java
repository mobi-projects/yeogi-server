package com.example.yeogiserver.post.application.dto.request;

import com.example.yeogiserver.member.domain.Member;
import com.example.yeogiserver.post.domain.Post;
import com.example.yeogiserver.post.domain.Theme;

import java.time.LocalDateTime;
import java.util.List;

public record PostRequestDto(
        String continent,
        String region,
        LocalDateTime tripStartDate,
        LocalDateTime tripEndDate,
        String title,
        String content,
        List<Theme> themeList,
        List<MemoRequestDto> shortPosts,
        String address
) {
    public Post toEntity(Member member) {
        return new Post(continent, tripStartDate, tripEndDate, title, content, member, region, address);
    }
}

// 이하 겟

//        Long postId,
//        String author,
//        String title,
//        Long commentCount,
//        Long likeCount, // fetch join ?
//        Long viewCount,
//        LocalDateTime createdAt,
//        LocalDateTime modifiedAt,
//        String continent,
//        String region,
//        String address,
//        Theme theme