package com.example.yeogiserver.comment.presentation;


import com.example.yeogiserver.comment.application.CommentService;
import com.example.yeogiserver.comment.application.dto.CommentRequestDto;
import com.example.yeogiserver.comment.application.dto.CommentResponseDto;
import com.example.yeogiserver.comment.domain.Comment;
import com.example.yeogiserver.security.domain.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;


    @GetMapping("/comments/{postId}")
    public List<CommentResponseDto> getComments(@PathVariable Long postId) {
        return commentService.getComments(postId);
    }
    @PostMapping("/comment")
    public void saveComment(@RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal CustomUserDetails userDetails) {
        commentService.saveComment(commentRequestDto,userDetails);
    }
    @PutMapping("/comment/{commentId}")
    public void updateComment(@PathVariable Long commentId,@RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal CustomUserDetails userDetails){
        commentService.updateComment(commentId, commentRequestDto);
    }
    @DeleteMapping("/comment/{commentId}")
    public void deleteComment(@PathVariable Long commentId, @AuthenticationPrincipal CustomUserDetails userDetails){
        commentService.deleteComment(commentId);
    }


}
