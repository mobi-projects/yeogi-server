package com.example.yeogiserver.post.application.dto;

import com.example.yeogiserver.post.domain.Post;

import java.util.List;

public record PostRequestDto(
        String region,
        String tripPeriod,
        String title,
        String content,
        List<String> shortPosts
) {
    public Post toEntity(PostRequestDto postRequestDto) {
        return new Post(postRequestDto.region, postRequestDto.tripPeriod, postRequestDto.title(), postRequestDto.content());
    }
}
