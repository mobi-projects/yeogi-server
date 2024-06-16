package com.example.yeogiserver.post.presentation;

import com.example.yeogiserver.post.application.PostReadService;
import com.example.yeogiserver.post.application.dto.response.PostListResponseDto;
import com.example.yeogiserver.post.application.dto.response.PostResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PostReadController {

    private final PostReadService postReadService;

    @GetMapping("/posts/{postId}")
    public PostResponseDto getPostById(@PathVariable Long postId) {
        return postReadService.getPostDetail(postId);
    }

    @GetMapping("/posts")
    public List<PostListResponseDto> getAllPosts(
            @RequestParam SearchType searchType,
            @RequestParam(required = false) String searchString,
            @RequestParam SortCondition sortCondition
    ) {
        return postReadService.getPostList(searchType, searchString, sortCondition);
    }
}
