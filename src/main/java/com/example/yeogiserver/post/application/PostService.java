package com.example.yeogiserver.post.application;

import com.example.yeogiserver.member.application.MemberQueryService;
import com.example.yeogiserver.member.domain.Member;
import com.example.yeogiserver.post.application.dto.request.PostRequestDto;
import com.example.yeogiserver.post.application.dto.request.ShortPostRequestDto;
import com.example.yeogiserver.post.domain.Post;
import com.example.yeogiserver.post.domain.PostLike;
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

    private final MemberQueryService memberQueryService;

    private Post getPost(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Post not found"));
    }

    public void addViewCount(Long postId){
        postRepository.addViewCount(postId);
    }

    public Long createPost(String email, PostRequestDto postRequestDto) {
        Member author = memberQueryService.findMember(email);
        Post post = postRequestDto.toEntity(author);

        List<String> shortPosts = postRequestDto.shortPosts();

        if (!shortPosts.isEmpty()){
            List<ShortPost> shortPostList = shortPosts.stream().map(ShortPost::new).toList();
            shortPostList.forEach(post::addShortPost);
        }

        postRepository.savePost(post);

        return post.getId();
    }

    public void updatePost(Long id, PostRequestDto postRequestDto) {
        Post post = getPost(id);

        post.updateFields(postRequestDto.continent(), postRequestDto.tripStartDate(), postRequestDto.tripEndDate(), postRequestDto.title(), postRequestDto.title());
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

    public void likePost(Long postId, Long memberId){
        boolean likeExist = postRepository.isLikeExist(postId, memberId);
        if (likeExist){
            throw new IllegalArgumentException("Member already like this post");
        }

        Post post = getPost(postId);
        PostLike postLike = new PostLike(memberId);
        post.addPostLike(postLike);
    }

    public void dislikePost(Long memberId, Long postId){
        PostLike postLike = postRepository.findPostLikeByPostIdAndMemberId(postId, memberId).orElseThrow(
                () -> new IllegalArgumentException("Member has not like this post")
        );

        Post post = getPost(postId);
        post.removePostLike(postLike);
    }
}
