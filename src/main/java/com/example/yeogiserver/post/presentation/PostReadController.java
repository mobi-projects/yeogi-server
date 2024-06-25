package com.example.yeogiserver.post.presentation;

import com.example.yeogiserver.post.application.PostReadService;
import com.example.yeogiserver.post.application.dto.response.PostListResponseDto;
import com.example.yeogiserver.post.application.dto.response.PostResponseDto;
import com.example.yeogiserver.post.domain.Theme;
import com.example.yeogiserver.post.presentation.search_condition.PostSearchType;
import com.example.yeogiserver.post.presentation.search_condition.PostSortCondition;
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

    // TODO : 페이지네이션 추가

    @GetMapping("/posts")
    public List<PostListResponseDto> getAllPosts(
            @RequestParam PostSearchType postSearchType,
            @RequestParam(required = false) String searchString,
            @RequestParam PostSortCondition postSortCondition,
            @RequestParam Theme theme) {
        return postReadService.getPostList(postSearchType, searchString, postSortCondition, theme);
    }
}
