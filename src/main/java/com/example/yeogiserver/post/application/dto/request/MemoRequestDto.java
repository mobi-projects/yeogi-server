package com.example.yeogiserver.post.application.dto.request;

import com.example.yeogiserver.post.domain.Memo;

public record MemoRequestDto(String content, String address){

    public Memo toEntity(){
        return new Memo(content, address);
    }

}
