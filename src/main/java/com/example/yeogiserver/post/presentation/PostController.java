package com.example.yeogiserver.post.presentation;

import com.example.yeogiserver.common.application.CommonService;
import com.example.yeogiserver.post.application.PostService;
import com.example.yeogiserver.post.application.dto.request.PostRequestDto;
import com.example.yeogiserver.post.application.dto.request.PostUpdateRequest;
import com.example.yeogiserver.security.domain.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "게시글 쓰기(C,U,D) 컨트롤러")
@Slf4j
public class PostController {

    private final PostService postService;
    private final CommonService commonService;

    @PostMapping("/posts")
    @Operation(description = "게시글을 생성한다.")
    public ResponseEntity<Void> createPost(@RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long postId = postService.createPost(userDetails.getId(), postRequestDto);

        // 방금 생성된 리소스의 URL 설정
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(postId)
                .toUri();

        log.info("[CREATE POST] TIME = {} , ID = {} , USER = {}" , commonService.getTime() , postId , commonService.getName(userDetails));

        // 201 Created 응답과 Location 헤더를 반환
        return ResponseEntity.created(location).build();
    }

    @PostMapping("/posts/{postId}/views")
    @Operation(description = "postId 에 해당하는 게시글의 조회수를 추가 한다.")
    public void addViewCount(@PathVariable Long postId , @AuthenticationPrincipal CustomUserDetails userDetails){
        postService.addViewCount(postId);
        log.info("[ADD VIEW COUNT] TIME = {} , ID = {} , USER = {} " , commonService.getTime() , postId , commonService.getName(userDetails));
    }

    @PutMapping("/posts/{postId}")
    @Operation(description = "postId 에 해당하는 게시글을 수정한다.")
    public void updatePost(@PathVariable Long postId, @RequestBody PostUpdateRequest postRequestDto, @AuthenticationPrincipal CustomUserDetails userDetails) {
        postService.updatePost(postId, postRequestDto, userDetails.getId());
        log.info("[UPDATE POST] TIME = {} , ID = {} , USER = {} " , commonService.getTime() , postId , commonService.getName(userDetails));
    }

    @DeleteMapping("/posts/{postId}")
    @Operation(description = "postId 에 해당하는 게시글을 삭제한다.")
    public void deletePost(@PathVariable Long postId , @AuthenticationPrincipal CustomUserDetails userDetails) {
        postService.delete(postId);
        log.info("[DELETE POST] TIME = {} , ID = {} , USER = {} " , commonService.getTime() , postId , commonService.getName(userDetails));
    }

    @PostMapping("/posts/{postId}/likes")
    @Operation(description = "postId 에 해당하는 게시글의 좋아요를 생성한다.")
    public void likePost(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long postId) {
        postService.likePost(userDetails.getId(), postId);
        log.info("[LIKE POST] TIME = {} , ID = {} , USER = {} " , commonService.getTime() , postId , commonService.getName(userDetails));
    }

    @DeleteMapping("/posts/{postId}/likes")
    @Operation(description = "postId 에 해당하는 게시글의 좋아요를 삭제한다.")
    public void unlikePost(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long postId) {
        postService.dislikePost(userDetails.getId(), postId);
        log.info("[DISLIKE POST] TIME = {} , ID = {} , USER = {} " , commonService.getTime() , postId , commonService.getName(userDetails));
    }
}
