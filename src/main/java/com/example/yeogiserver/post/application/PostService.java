package com.example.yeogiserver.post.application;

import com.example.yeogiserver.post.application.dto.PostRequestDto;
import com.example.yeogiserver.post.application.dto.ShortPostRequestDto;
import com.example.yeogiserver.post.domain.Post;
import com.example.yeogiserver.post.domain.PostRepository;
import com.example.yeogiserver.post.domain.ShortPost;
import com.example.yeogiserver.post.domain.ShortPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;

    private final ShortPostRepository shortPostRepository;

    private Post getPost(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Post not found"));
    }

    public void createPost(PostRequestDto postRequestDto) {
        Post post = postRequestDto.toEntity(postRequestDto);

        List<String> shortPosts = postRequestDto.shortPosts();

        if (!shortPosts.isEmpty()){
            List<ShortPost> shortPostList = shortPosts.stream().map(ShortPost::new).toList();
            shortPostList.forEach(post::addShortPost);
        }

        postRepository.savePost(post);
    }

    public void updatePost(Long id, PostRequestDto postRequestDto) {
        Post post = getPost(id);

        post.updateFields(postRequestDto.region(), postRequestDto.tripPeriod(), postRequestDto.title(), postRequestDto.title());
    }

    public void delete(Long id) {
        postRepository.deleteById(id);
    }

    public void addShortPost(Long postId, ShortPostRequestDto shortPostRequestDto) {
        Post post = getPost(postId);
        ShortPost newShortPost = shortPostRequestDto.toEntity();

        post.addShortPost(newShortPost);
    }

    public void updateShortPost(Long shortPostId, ShortPostRequestDto shortPostRequestDto){
        ShortPost shortPost = shortPostRepository.findById(shortPostId).orElseThrow(() -> new IllegalArgumentException("Memo not found"));

        shortPost.update(shortPostRequestDto.content());
    }

    public void deleteShortPost(Long postId, Long shortPostId){
        Post post = getPost(postId);
        ShortPost shortPost = shortPostRepository.findById(shortPostId).orElseThrow(() -> new IllegalArgumentException("Memo not found"));

        post.removeShortPost(shortPost);
    }
}
