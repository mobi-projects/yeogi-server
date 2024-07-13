package com.example.yeogiserver.post.presentation;

import com.example.yeogiserver.post.application.PostReadService;
import com.example.yeogiserver.post.application.dto.response.PostListResponseDto;
import com.example.yeogiserver.post.application.dto.response.PostResponseDto;
import com.example.yeogiserver.post.domain.Theme;
import com.example.yeogiserver.post.presentation.search_condition.PostSearchType;
import com.example.yeogiserver.post.presentation.search_condition.PostSortCondition;
import com.example.yeogiserver.security.domain.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    public PostResponseDto getPostById(@PathVariable Long postId, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return postReadService.getPostDetail(postId, customUserDetails.getId());
    }

    // TODO : 페이지네이션 추가

    @GetMapping("/posts")
    public List<PostListResponseDto> getAllPosts(
            @RequestParam PostSearchType postSearchType,
            @RequestParam(required = false) String searchString,
            @RequestParam(required = false) String continent,
            @RequestParam PostSortCondition postSortCondition,
            @RequestParam(required = false) List<Theme> themes) {
        return postReadService.getPostList(postSearchType, searchString, postSortCondition, continent, themes);
    }

    @GetMapping("/posts/popular") // popular 말고 더 좋은 것은 없나?
    public List<PostListResponseDto> getPopularPostListByTheme(@RequestParam List<Theme> themeList){
        return postReadService.getPopularPostListByTheme(themeList);
    }
}
