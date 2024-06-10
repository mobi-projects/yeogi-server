package com.example.yeogiserver.comment.domain;

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
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column
    private String content;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member author;

    //TODO. POST_ID
    private Long postId;
    public static Comment of (Member author, String content, Long postId) {
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
