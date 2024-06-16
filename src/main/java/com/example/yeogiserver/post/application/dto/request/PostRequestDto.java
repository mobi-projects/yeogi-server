package com.example.yeogiserver.post.application.dto.request;

import com.example.yeogiserver.member.domain.Member;
import com.example.yeogiserver.post.domain.Post;

import java.time.LocalDateTime;
import java.util.List;

public record PostRequestDto(
        String continent,
        String country,
        LocalDateTime tripStartDate,
        LocalDateTime tripEndDate,
        String title,
        String content,
        List<String> shortPosts
) {
    public Post toEntity(Member member) {
        return new Post(continent, tripStartDate, tripEndDate, title, content, member, country);
    }
}