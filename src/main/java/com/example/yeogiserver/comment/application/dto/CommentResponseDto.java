package com.example.yeogiserver.comment.application.dto;

import com.example.yeogiserver.comment.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public record CommentResponseDto(Long id,
                                 String content,
                                 String nickname,
                                 Long postId,
                                 LocalDateTime createdAt,
                                 LocalDateTime modifiedAt,
                                 Long likeCount,
                                 List<ReplyResponseDto> child) {
    public static List<CommentResponseDto> toEntityList(List<Comment> comments) {
        return comments.stream()
                .filter(comment -> comment.getParent() == null)
                .map(comment -> new CommentResponseDto(
                        comment.getId(),
                        comment.getContent(),
                        comment.getMember().getNickname(),
                        comment.getPost().getId(),
                        comment.getCreatedAt(),
                        comment.getModifiedAt(),
                        (long) comment.getLikeList().size(),
                        ReplyResponseDto.of(comment.getChildren())))
                .collect(Collectors.toList());

    }
}

