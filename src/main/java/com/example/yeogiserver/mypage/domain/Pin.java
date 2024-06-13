package com.example.yeogiserver.mypage.domain;

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
public class Pin extends TimeStamp {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String x;
    @Column
    private String y;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "email")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    public static Pin of(String x, String y, Member member, Post post) {
        return Pin.builder()
                .x(x)
                .y(y)
                .member(member)
                .post(post)
                .build();

    }
}
