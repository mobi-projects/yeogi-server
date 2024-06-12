package com.example.yeogiserver.comment.domain;

import com.example.yeogiserver.base.TimeStamp;
import com.example.yeogiserver.member.domain.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
@Table(name = "comments")
public class Comment extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column
    private String content;

    @Column
    private String author;

    //TODO. POST_ID
    @Column
    private Long postId;
    public static Comment of (String author, String content, Long postId) {
        return Comment.builder()
                .author(author)
                .content(content)
                .postId(postId)
                .build();
    }
    public static Comment of (String content) {
        return Comment.builder()
                .content(content)
                .build();
    }

}
