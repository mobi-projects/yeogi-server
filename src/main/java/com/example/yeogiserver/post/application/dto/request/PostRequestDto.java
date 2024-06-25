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
        Theme theme,
        List<String> shortPosts,
        String address
) {
    public Post toEntity(Member member) {
        return new Post(continent, tripStartDate, tripEndDate, title, content, member, region, theme, address);
    }
}
