package com.example.yeogiserver.post.presentation;

import com.example.yeogiserver.post.application.PostReadService;
import com.example.yeogiserver.post.application.dto.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostReadController {

    private final PostReadService postReadService;

    @GetMapping("/posts/{postId}")
    public PostResponseDto getPostById(@PathVariable Long postId) {
        return postReadService.getPostById(postId);
    }
}
