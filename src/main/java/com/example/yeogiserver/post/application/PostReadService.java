package com.example.yeogiserver.post.application;

import com.example.yeogiserver.post.application.dto.PostResponseDto;
import com.example.yeogiserver.post.domain.Post;
import com.example.yeogiserver.post.domain.PostReadRepository;
import com.example.yeogiserver.post.repository.JpaPostLikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostReadService {

    private final PostReadRepository postReadRepository;

    private final JpaPostLikeRepository jpaPostLikeRepository;

    private Post getPost(Long id) {
        return postReadRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("post not found"));
    }

    public PostResponseDto getPostById(Long postId) {
        Post post = getPost(postId);
        Long likeCount = getLikeCount(postId);

        return PostResponseDto.ofPost(post, likeCount);
    }

    public Long getLikeCount(Long postId){
        return jpaPostLikeRepository.countByPostId(postId);
    }
}
