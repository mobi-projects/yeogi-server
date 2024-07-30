package com.example.yeogiserver.comment.presentation;

import com.example.yeogiserver.comment.application.CommentLikeService;
import com.example.yeogiserver.security.domain.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    @PostMapping("/comment/like/{commentId}")
    public void addCommentLike(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long commentId) {
        commentLikeService.saveLike(userDetails.getEmail(), commentId);
    }
    @DeleteMapping("/comment/like/{commentId}")
    public void deleteCommentLike(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long commentId) {
        commentLikeService.deleteLike(userDetails.getEmail(), commentId);
    }
}
