package com.example.yeogiserver.post.presentation;

import com.example.yeogiserver.post.application.PostReadService;
import com.example.yeogiserver.post.application.dto.response.PostListResponseDto;
import com.example.yeogiserver.post.application.dto.response.PostResponseDto;
import com.example.yeogiserver.post.domain.Theme;
import com.example.yeogiserver.post.presentation.search_condition.PostSearchType;
import com.example.yeogiserver.post.presentation.search_condition.PostSortCondition;
import com.example.yeogiserver.security.domain.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
@Tag(name = "게시글 조회 컨트롤러")
public class PostReadController {

    private final PostReadService postReadService;

    @GetMapping("/posts/{postId}")
    @Operation(description = "포스트 아이디를 통해 포스트의 상세정보를 조회한다. 토큰이 있을 시 좋아요 여부, 없을 시 false 를 반환한다.")
    public PostResponseDto getPostById(@PathVariable Long postId, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long memberId = null;
        if (!Objects.isNull(customUserDetails)){
            memberId = customUserDetails.getId();
        }
        return postReadService.getPostDetail(postId, memberId);
    }

    // TODO : 페이지네이션 추가

    @GetMapping("/posts")
    @Operation(description = "검색 조건(postSearchType), 정렬 조건(postSortCondition)에 따라 포스트 리스트를 리턴한다")
    public List<PostListResponseDto> getAllPosts( @AuthenticationPrincipal CustomUserDetails customUserDetails,
            @RequestParam PostSearchType postSearchType,
            @RequestParam(required = false) String searchString,
            @RequestParam(required = false) String continent,
            @RequestParam PostSortCondition postSortCondition,
            @RequestParam(required = false) List<Theme> themes) {

        Long memberId = null;
        if (!Objects.isNull(customUserDetails)){
            memberId = customUserDetails.getId();
        }

        return postReadService.getPostList(postSearchType, searchString, postSortCondition, continent, themes, memberId);
    }

    @GetMapping("/posts/popular") // popular 말고 더 좋은 것은 없나?
    @Operation(description = "QueryString 으로 들어오는 테마 별로, 조회수 높은 게시글을 리턴한다.")
    public List<PostListResponseDto> getPopularPostListByTheme(@RequestParam List<Theme> themeList){
        return postReadService.getPopularPostListByTheme(themeList);
    }

    @GetMapping("/posts/mine")
    @Operation(description = "사용자의 토큰 정보 바탕으로, 내가 쓴 게시글 목록을 리턴한다.")
    public List<PostListResponseDto> getMinePostListByTheme(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return postReadService.getMyPostList(customUserDetails.getId());
    }
}
