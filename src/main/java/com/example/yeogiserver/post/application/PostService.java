package com.example.yeogiserver.post.application;

import com.example.yeogiserver.member.application.MemberQueryService;
import com.example.yeogiserver.member.domain.Member;
import com.example.yeogiserver.post.application.dto.request.MemoRequestDto;
import com.example.yeogiserver.post.application.dto.request.MemoUpdateRequestDto;
import com.example.yeogiserver.post.application.dto.request.PostRequestDto;
import com.example.yeogiserver.post.application.dto.request.PostUpdateRequest;
import com.example.yeogiserver.post.domain.Memo;
import com.example.yeogiserver.post.domain.Post;
import com.example.yeogiserver.post.domain.PostLike;
import com.example.yeogiserver.post.domain.PostRepository;
import com.example.yeogiserver.post.domain.PostTheme;
import com.example.yeogiserver.post.domain.Theme;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;

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

        List<MemoRequestDto> memoRequestDtoList = postRequestDto.shortPosts();

        if (!memoRequestDtoList.isEmpty()){
            List<Memo> memoList = memoRequestDtoList.stream().map(MemoRequestDto::toEntity).toList();
            memoList.forEach(post::addMemo);
        }

        List<Theme> themeList = postRequestDto.themeList();
        if (!themeList.isEmpty()){
            List<PostTheme> postThemeList = themeList.stream().map(PostTheme::new).toList();
            post.replaceThemeList(postThemeList);
        }

        postRepository.savePost(post);

        return post.getId();
    }

    public void updatePost(Long id, PostUpdateRequest postUpdateRequest) {
        Post post = getPost(id);
        post.updateFields(postUpdateRequest.continent(), postUpdateRequest.tripStartDate(), postUpdateRequest.tripEndDate(), postUpdateRequest.title(), postUpdateRequest.title(), postUpdateRequest.address());
        updateMemoList(postUpdateRequest.memos(), post);
        updateProjectTheme(postUpdateRequest.themeList(), post);
    }

    private void updateMemoList(List<MemoUpdateRequestDto> memoUpdateRequestDtoList, Post post) {
        List<Long> idList = memoUpdateRequestDtoList.stream().map(MemoUpdateRequestDto::id).toList();
        List<Memo> memoList = post.getMemoList();

        // 딜리트
        memoList.stream().filter(each -> !idList.contains(each.getId())).forEach(post::removeMemo);

        // 업데이트
        memoUpdateRequestDtoList.stream().filter(each -> each.id() != null && each.id() != 0).forEach(
                each -> {
                    Optional<Memo> optionalMemo = memoList.stream().filter(memo -> memo.getId().equals(each.id())).findFirst();
                    optionalMemo.ifPresent(memo -> memo.update(each.memo(), each.address()));
                }
        );

        // 생성
        memoUpdateRequestDtoList
                .stream()
                .filter(each -> each.id() == null || each.id() == 0)
                .map(each -> new Memo(each.memo(), each.address()))
                .toList()
                .forEach(post::addMemo);
    }

    private void updateProjectTheme(List<Theme> themeList, Post post) {
        List<PostTheme> postThemeList = themeList.stream().map(PostTheme::new).toList();
        post.replaceThemeList(postThemeList);
    }

    public void delete(Long id) {
        postRepository.deleteById(id);
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
