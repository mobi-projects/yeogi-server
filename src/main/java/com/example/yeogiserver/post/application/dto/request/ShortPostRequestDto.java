package com.example.yeogiserver.post.application.dto.request;

import com.example.yeogiserver.post.domain.ShortPost;

public record ShortPostRequestDto(String content){

    public ShortPost toEntity(){
        return new ShortPost(content);
    }

}