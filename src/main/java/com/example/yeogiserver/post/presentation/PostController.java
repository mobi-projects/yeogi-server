package com.example.yeogiserver.post.presentation;

import com.example.yeogiserver.member.domain.Member;
import com.example.yeogiserver.post.application.PostService;
import com.example.yeogiserver.post.application.dto.PostRequestDto;
import com.example.yeogiserver.post.application.dto.ShortPostRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public void createPost(@RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal Member member) {
        // TODO : 인증 구성 완료 시 Member -> UserDetails 로 변경할 것
        postService.createPost(member.getId(), postRequestDto);
    }

    @PutMapping("/posts/{postId}")
    public void updatePost(@PathVariable Long postId, @RequestBody PostRequestDto postRequestDto) {
        postService.updatePost(postId, postRequestDto);
    }

    @DeleteMapping("/posts/{postId}")
    public void deletePost(@PathVariable Long postId) {
        postService.delete(postId);
    }

    @PostMapping("/posts/{postId}/short-posts")
    public void addMemo(@PathVariable Long postId, @RequestBody ShortPostRequestDto shortPostRequestDto) {
        postService.addShortPost(postId, shortPostRequestDto);
    }

    @PutMapping("/posts/short-posts/{shortPostId}")
    public void updateMemo(@PathVariable Long shortPostId, @RequestBody ShortPostRequestDto shortPostRequestDto) {
        postService.updateShortPost(shortPostId, shortPostRequestDto);
    }

    @DeleteMapping("/posts/{postId}/short-posts/{shortPostId}")
    public void deleteMemo(@PathVariable Long postId, @PathVariable Long shortPostId) {
        postService.deleteShortPost(postId, shortPostId);
    }

    @PostMapping("/posts/{postId}/likes")
    public void likePost(@AuthenticationPrincipal Member member, @PathVariable Long postId) {
        postService.likePost(member.getId(), postId);
    }

    @DeleteMapping("/posts/{postId}/likes")
    public void unlikePost(@AuthenticationPrincipal Member member, @PathVariable Long postId) {
        postService.dislikePost(member.getId(), postId);
    }
}
