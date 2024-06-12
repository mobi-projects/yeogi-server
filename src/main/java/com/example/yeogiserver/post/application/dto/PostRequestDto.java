package com.example.yeogiserver.post.application.dto;

import com.example.yeogiserver.member.domain.Member;
import com.example.yeogiserver.post.domain.Post;

import java.time.LocalDateTime;
import java.util.List;

public record PostRequestDto(
        String continent,
        String country,
        LocalDateTime tripStarDate,
        LocalDateTime tripEndDate,
        String title,
        String content,
        List<String> shortPosts
) {
    public Post toEntity(Member member) {
        return new Post(continent, tripStarDate, tripEndDate, title, content, member, country);
    }
}
