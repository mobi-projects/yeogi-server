package com.example.yeogiserver.post.domain;

import com.example.yeogiserver.post.presentation.SearchType;
import com.example.yeogiserver.post.presentation.SortCondition;

import java.util.List;
import java.util.Optional;

public interface PostReadRepository {
    Optional<Post> findById(Long postId);

    List<Long> findLikedMemberByPostId(Long postId);

    Long getLikeCount(Long postId);

    List<Post> findPostListBySearchTypeAndSortCondition(SearchType searchType, String searchString, SortCondition sortCondition);
}
