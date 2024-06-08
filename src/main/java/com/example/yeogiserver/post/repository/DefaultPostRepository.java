package com.example.yeogiserver.post.repository;

import com.example.yeogiserver.post.domain.Post;
import com.example.yeogiserver.post.domain.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class DefaultPostRepository implements PostRepository {

    private final JpaPostRepository jpaPostRepository;

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
}
