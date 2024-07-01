package com.example.yeogiserver.post.presentation;

import com.example.yeogiserver.post.application.PostService;
import com.example.yeogiserver.post.application.dto.request.PostRequestDto;
import com.example.yeogiserver.post.application.dto.request.PostUpdateRequest;
import com.example.yeogiserver.security.domain.CustomUserDetails;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
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
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public ResponseEntity<Void> createPost(@RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long postId = postService.createPost(userDetails.getEmail(), postRequestDto);

        // 방금 생성된 리소스의 URL 설정
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(postId)
                .toUri();

        // 201 Created 응답과 Location 헤더를 반환
        return ResponseEntity.created(location).build();
    }

    @PostMapping("/posts/{postId}/views")
    public void addViewCount(@PathVariable Long postId){
        postService.addViewCount(postId);
    }

    @PutMapping("/posts/{postId}")
    public void updatePost(@PathVariable Long postId, @RequestBody PostUpdateRequest postRequestDto) {
        postService.updatePost(postId, postRequestDto);
    }

    @DeleteMapping("/posts/{postId}")
    public void deletePost(@PathVariable Long postId) {
        postService.delete(postId);
    }

    @PostMapping("/posts/{postId}/likes")
    public void likePost(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long postId) {
        postService.likePost(userDetails.getId(), postId);
    }

    @DeleteMapping("/posts/{postId}/likes")
    public void unlikePost(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long postId) {
        postService.dislikePost(userDetails.getId(), postId);
    }
}
