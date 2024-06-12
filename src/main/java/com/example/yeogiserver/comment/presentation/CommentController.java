package com.example.yeogiserver.comment.presentation;


import com.example.yeogiserver.comment.application.CommentService;
import com.example.yeogiserver.comment.application.dto.CommentRequestDto;
import com.example.yeogiserver.comment.application.dto.CommentResponseDto;
import com.example.yeogiserver.comment.domain.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Comment saveComment(@RequestBody CommentRequestDto commentRequestDto) {
        return commentService.saveComment(commentRequestDto);
    }
    @PutMapping("/comment/{commentId}")
    public void updateComment(@PathVariable Long commentId,@RequestBody CommentRequestDto commentRequestDto){
        commentService.updateComment(commentId, commentRequestDto);
    }
    @DeleteMapping("/comment/{commentId}")
    public void deleteComment(@PathVariable Long commentId){
        commentService.deleteComment(commentId);
    }


}
