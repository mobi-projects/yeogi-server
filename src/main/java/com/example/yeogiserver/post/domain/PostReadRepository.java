package com.example.yeogiserver.post.domain;

import com.example.yeogiserver.post.presentation.search_condition.PostSearchType;
import com.example.yeogiserver.post.presentation.search_condition.PostSortCondition;

import java.util.List;
import java.util.Optional;

public interface PostReadRepository {
    Optional<Post> findById(Long postId);

    List<Long> findLikedMemberByPostId(Long postId);

    Long getLikeCount(Long postId);

    List<Post> findPostListBySearchTypeAndSortCondition(PostSearchType postSearchType, String searchString, PostSortCondition postSortCondition);
}
