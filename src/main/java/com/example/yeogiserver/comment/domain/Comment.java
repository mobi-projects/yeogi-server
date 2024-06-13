package com.example.yeogiserver.comment.domain;

import com.example.yeogiserver.base.TimeStamp;
import com.example.yeogiserver.member.domain.Member;
import com.example.yeogiserver.post.domain.Post;
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


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
    public static Comment of (Member member, String content, Post post) {
        return Comment.builder()
                .member(member)
                .content(content)
                .post(post)
                .build();
    }
    public static Comment of (String content) {
        return Comment.builder()
                .content(content)
                .build();
    }

}
