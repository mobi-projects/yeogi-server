package com.example.yeogiserver.post.domain;

import com.example.yeogiserver.member.domain.Member;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String region;

    private LocalDateTime tripStarDate;

    private LocalDateTime tripEndDate;

    @Column(nullable = false)
    private String title;

    @Lob
    @Column
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member author;

    // TODO : eager 해도 될수도
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ShortPost> shortPostList = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<PostLike> postLikeList = new ArrayList<>();

    public Post(String region, LocalDateTime tripStarDate, LocalDateTime tripEndDate, String title, String content, Member author) {
        this.region = region;
        this.tripStarDate = tripStarDate;
        this.tripEndDate = tripEndDate;
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void updateFields(String region, LocalDateTime tripStarDate, LocalDateTime tripEndDate, String title, String content) {
        this.region = region;
        this.tripStarDate = tripStarDate;
        this.tripEndDate = tripEndDate;
        this.title = title;
        this.content = content;
    }


    public void addShortPost(ShortPost shortPost) {
        shortPostList.add(shortPost);
        shortPost.assignPost(this);
    }

    public void removeShortPost(ShortPost shortPost) {
        shortPostList.remove(shortPost);
        shortPost.assignPost(null);
    }

    public void addPostLike(PostLike postLike) {
        postLikeList.add(postLike);
        postLike.assignPost(this);
    }

    public void removePostLike(PostLike postLike) {
        postLikeList.remove(postLike);
        postLike.assignPost(null);
    }
}
