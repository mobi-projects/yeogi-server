package com.example.yeogiserver.post.application;

import com.example.yeogiserver.common.exception.CustomException;
import com.example.yeogiserver.common.exception.ErrorCode;
import com.example.yeogiserver.event.recommand.RecommandEvent;
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
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;

    private final MemberQueryService memberQueryService;

    private final ApplicationEventPublisher applicationEventPublisher;

    private Post getPost(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.POST_NOT_FOUND));
    }

    public void addViewCount(Long postId){
        Post post = getPost(postId);
        postRepository.addViewCount(post.getId());
    }

    public Long createPost(Long memberId, PostRequestDto postRequestDto) {
        Member author = memberQueryService.findById(memberId);
        Post post = postRequestDto.toEntity(author);

        List<MemoRequestDto> memoRequestDtoList = postRequestDto.memos();

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

    public void updatePost(Long id, PostUpdateRequest postUpdateRequest, Long memberId) {
        Post post = getPost(id);

        if (!Objects.equals(post.getAuthor().getId(), memberId)) {
            throw new CustomException(ErrorCode.NOT_MY_POST);
        }

        post.updateFields(postUpdateRequest.continent(), postUpdateRequest.country(), postUpdateRequest.tripStartDate(), postUpdateRequest.tripEndDate(), postUpdateRequest.title(), postUpdateRequest.content(), postUpdateRequest.address());
        updateMemoList(postUpdateRequest.memos(), post);
        updateProjectTheme(postUpdateRequest.themeList(), post);
    }

    private void updateMemoList(List<MemoUpdateRequestDto> memoUpdateRequestDtoList, Post post) {
        List<Long> idList = memoUpdateRequestDtoList.stream().map(MemoUpdateRequestDto::memoId).toList();
        List<Memo> memoList = post.getMemoList();

        // 딜리트
        memoList.stream().filter(each -> !idList.contains(each.getId())).forEach(post::removeMemo);

        // 업데이트
        memoUpdateRequestDtoList.stream().filter(each -> each.memoId() != null && each.memoId() != 0).forEach(
                each -> {
                    Optional<Memo> optionalMemo = memoList.stream().filter(memo -> memo.getId().equals(each.memoId())).findFirst();
                    optionalMemo.ifPresent(memo -> memo.update(each.content(), each.address()));
                }
        );

        // 생성
        memoUpdateRequestDtoList
                .stream()
                .filter(each -> each.memoId() == null || each.memoId() == 0)
                .map(each -> new Memo(each.content(), each.address()))
                .toList()
                .forEach(post::addMemo);
    }

    private void updateProjectTheme(List<Theme> themeList, Post post) {
        List<PostTheme> postThemeList = themeList.stream().map(PostTheme::new).toList();
        post.replaceThemeList(postThemeList);
    }

    public void delete(Long id , Long memberId) {
        Post post = getPost(id);

        if (!Objects.equals(post.getAuthor().getId(), memberId)) {
            throw new CustomException(ErrorCode.NOT_MY_POST);
        }

        postRepository.deleteById(id);
    }

    public void likePost(Long memberId, Long postId){
        boolean likeExist = postRepository.isLikeExist(postId, memberId);
        if (likeExist){
            throw new CustomException(ErrorCode.MEMBER_ALREADY_LIKE_POST);
        }

        Post post = getPost(postId);
        PostLike postLike = new PostLike(memberId);
        post.addPostLike(postLike);
        applicationEventPublisher.publishEvent(new RecommandEvent(this,post.getId(),memberId));
    }

    public void dislikePost(Long memberId, Long postId){
        PostLike postLike = postRepository.findPostLikeByPostIdAndMemberId(postId, memberId).orElseThrow(
                () -> new CustomException(ErrorCode.MEMBER_ALREADY_DISLIKE_POST)
        );

        Post post = getPost(postId);
        post.removePostLike(postLike);
    }
}
