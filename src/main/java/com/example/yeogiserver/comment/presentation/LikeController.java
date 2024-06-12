package com.example.yeogiserver.comment.presentation;

import com.example.yeogiserver.comment.application.LikeService;
import com.example.yeogiserver.security.domain.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/comment/like/{commentId}")
    public void addCommentLike(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long commentId) {
        System.out.printf("YEJUN4911"+userDetails.getId()+commentId);

        likeService.saveLike(userDetails.getId(), commentId);
    }
    @DeleteMapping("/comment/like/{commentId}")
    public void deleteCommentLike(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long commentId) {
        System.out.printf("YEJUN4911"+userDetails.getId()+commentId);
        likeService.deleteLike(userDetails.getId(), commentId);
    }
}
