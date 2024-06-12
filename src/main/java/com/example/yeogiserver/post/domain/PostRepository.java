package com.example.yeogiserver.post.domain;

import java.util.Optional;

public interface PostRepository {
    Post savePost(Post post);

    Optional<Post> findById(Long id);

    void deleteById(Long id);

    boolean isLikeExist(Long postId, Long memberId);

    Optional<PostLike> findPostLikeByPostIdAndMemberId(Long postId, Long memberId);

    void addViewCount(Long postId);
}
