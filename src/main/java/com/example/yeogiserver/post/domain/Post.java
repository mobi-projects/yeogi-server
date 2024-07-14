package com.example.yeogiserver.post.domain;

import com.example.yeogiserver.base.TimeStamp;
import com.example.yeogiserver.member.domain.Member;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@ToString
public class Post extends TimeStamp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String continent;

    private String country;

    private LocalDateTime tripStarDate;

    private LocalDateTime tripEndDate;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "text")
    private String content;

    private Long viewCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member author;

    private String address;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Memo> memoList = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<PostLike> postLikeList = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<PostTheme> postThemeList = new ArrayList<>();

    public Post(String continent, LocalDateTime tripStarDate, LocalDateTime tripEndDate, String title, String content, Member author, String country, String address) {
        this.continent = continent;
        this.tripStarDate = tripStarDate;
        this.tripEndDate = tripEndDate;
        this.title = title;
        this.content = content;
        this.author = author;
        this.country = country;
        this.viewCount = 1L;
        this.address = address;
    }

    public void updateFields(String continent, String country, LocalDateTime tripStarDate, LocalDateTime tripEndDate, String title, String content, String address) {
        this.continent = continent;
        this.country = country;
        this.tripStarDate = tripStarDate;
        this.tripEndDate = tripEndDate;
        this.title = title;
        this.content = content;
        this.address = address;
    }

    public void replaceThemeList(List<PostTheme> themeList) {
        this.postThemeList.clear();
        themeList.forEach(each -> each.assignPost(this));
        this.postThemeList.addAll(themeList);
    }

    public void addMemo(Memo memo) {
        memoList.add(memo);
        memo.assignPost(this);
    }

    public void removeMemo(Memo memo) {
        memoList.remove(memo);
        memo.assignPost(null);
    }

    public void addPostLike(PostLike postLike) {
        postLikeList.add(postLike);
        postLike.assignPost(this);
    }

    public void removePostLike(PostLike postLike) {
        postLikeList.remove(postLike);
        postLike.assignPost(null);
    }

    public void addViewCount(){
        this.viewCount++;
    }
}
