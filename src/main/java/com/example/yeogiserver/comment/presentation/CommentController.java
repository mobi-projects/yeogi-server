package com.example.yeogiserver.comment.presentation;


import com.example.yeogiserver.comment.application.CommentService;
import com.example.yeogiserver.comment.application.dto.CommentRequestDto;
import com.example.yeogiserver.comment.application.dto.LikeRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private CommentService commentService;

    @PostMapping("/comments")
    public void saveComment(@RequestBody CommentRequestDto commentRequestDto) {
        commentService.saveComment(commentRequestDto);
    }
    @PutMapping("/comments/{commentId}")
    public void updateComment(@PathVariable Long commentId,@RequestBody CommentRequestDto commentRequestDto){
        commentService.updateComment(commentId, commentRequestDto);
    }
    @DeleteMapping("/comments/{commentId}")
    public void deleteComment(@PathVariable Long commentId){
        commentService.deleteComment(commentId);
    }

    @PostMapping("/comments/{commentId}")
    public void addCommentLike(@PathVariable Long commentId,@RequestBody LikeRequestDto likeRequestDto) {
        commentService.saveLike(commentId,likeRequestDto);
    }
}
