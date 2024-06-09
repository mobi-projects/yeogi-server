package com.example.yeogiserver.post.presentation;

import com.example.yeogiserver.post.application.PostReadService;
import com.example.yeogiserver.post.application.dto.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostReadController {

    private final PostReadService postReadService;

    public PostResponseDto getPostById(Long postId) {
        return postReadService.getPostById(postId);
    }
}
