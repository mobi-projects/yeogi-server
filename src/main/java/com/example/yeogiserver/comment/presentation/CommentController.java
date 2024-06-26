package com.example.yeogiserver.comment.presentation;


import com.example.yeogiserver.comment.application.CommentService;
import com.example.yeogiserver.comment.application.dto.CommentRequestDto;
import com.example.yeogiserver.comment.application.dto.CommentResponseDto;
import com.example.yeogiserver.comment.application.dto.CommentSaveResponse;
import com.example.yeogiserver.security.domain.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;


    @GetMapping("/comments/{postId}")
    public List<CommentResponseDto> getComments(Pageable pageable, @PathVariable Long postId) {
        return commentService.getComments(postId,pageable);
    }
    @PostMapping("/comment")
    public CommentSaveResponse addComment(@RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal CustomUserDetails userDetails) {
        return commentService.addComment(commentRequestDto,userDetails);
    }
    @PostMapping("/reply/{commentId}")
    public CommentSaveResponse addReply(@PathVariable Long commentId,@RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal CustomUserDetails userDetails) {
        return commentService.addReply(commentRequestDto,userDetails,commentId);
    }
    @PutMapping("/comment/{commentId}")
    public CommentSaveResponse updateComment(@PathVariable Long commentId,@RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal CustomUserDetails userDetails){
        return commentService.updateComment(commentId, commentRequestDto);
    }
    @DeleteMapping("/comment/{commentId}")
    public void deleteComment(@PathVariable Long commentId, @AuthenticationPrincipal CustomUserDetails userDetails){
        commentService.deleteComment(commentId);
    }


}
