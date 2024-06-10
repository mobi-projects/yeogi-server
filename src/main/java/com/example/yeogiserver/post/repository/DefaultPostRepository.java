package com.example.yeogiserver.post.repository;

import com.example.yeogiserver.post.domain.Post;
import com.example.yeogiserver.post.domain.PostLike;
import com.example.yeogiserver.post.domain.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class DefaultPostRepository implements PostRepository {

    private final JpaPostRepository jpaPostRepository;

    private final JpaPostLikeRepository jpaPostLikeRepository;

    @Override
    public Post savePost(Post post){
        return jpaPostRepository.save(post);
    }

    @Override
    public Optional<Post> findById(Long id){
        return jpaPostRepository.findById(id);
    }

    @Override
    public void deleteById(Long id){
        jpaPostRepository.deleteById(id);
    }

    @Override
    public boolean isLikeExist(Long postId, Long memberId) {
        return jpaPostLikeRepository.existsByPostIdAndMemberId(postId, memberId);
    }

    @Override
    public Optional<PostLike> findPostLikeByPostIdAndMemberId(Long postId, Long memberId){
        return jpaPostLikeRepository.findByPostIdAndMemberId(postId, memberId);
    }
}
