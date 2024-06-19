package com.example.yeogiserver.post.repository;

import com.example.yeogiserver.post.domain.Post;
import com.example.yeogiserver.post.domain.PostLike;
import com.example.yeogiserver.post.domain.QueryDslPostRepository;
import com.example.yeogiserver.post.domain.PostReadRepository;
import com.example.yeogiserver.post.presentation.search_condition.PostSearchType;
import com.example.yeogiserver.post.presentation.search_condition.PostSortCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DefaultPostReadRepository implements PostReadRepository {

    private final JpaPostRepository jpaPostRepository;

    private final JpaPostLikeRepository jpaPostLikeRepository;

    private final QueryDslPostRepository queryDslPostRepository;

    @Override
    public Optional<Post> findById(Long postId) {
        return jpaPostRepository.findById(postId);
    }

    @Override
    public List<Long> findLikedMemberByPostId(Long postId) {
        List<PostLike> postLikes = jpaPostLikeRepository.findAllByPostId(postId);
        return postLikes.stream().map(PostLike::getMemberId).toList();
    }

    @Override
    public Long getLikeCount(Long postId){
        return jpaPostLikeRepository.countByPostId(postId);
    }

    @Override
    public List<Post> findPostListBySearchTypeAndSortCondition(PostSearchType postSearchType, String searchString, PostSortCondition postSortCondition){
        return queryDslPostRepository.findPostListBySearchTypeAndSortCondition(postSearchType, searchString, postSortCondition);
    }
}
