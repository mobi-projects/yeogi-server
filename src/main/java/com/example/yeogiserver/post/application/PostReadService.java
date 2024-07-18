package com.example.yeogiserver.post.application;

import com.example.yeogiserver.comment.application.CommentService;
import com.example.yeogiserver.event.recommand.domain.Recommand;
import com.example.yeogiserver.event.recommand.domain.RecommandRepository;
import com.example.yeogiserver.member.application.MemberQueryService;
import com.example.yeogiserver.member.domain.Member;
import com.example.yeogiserver.member.dto.LikedMembersInfo;
import com.example.yeogiserver.post.application.dto.response.PostListResponseDto;
import com.example.yeogiserver.post.application.dto.response.PostResponseDto;
import com.example.yeogiserver.post.domain.Post;
import com.example.yeogiserver.post.domain.PostReadRepository;
import com.example.yeogiserver.post.domain.Theme;
import com.example.yeogiserver.post.presentation.search_condition.PostSearchType;
import com.example.yeogiserver.post.presentation.search_condition.PostSortCondition;
import com.example.yeogiserver.post.repository.JpaPostLikeRepository;
import com.example.yeogiserver.event.recommand.RecommandEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostReadService {

    private final PostReadRepository postReadRepository;

    private final MemberQueryService memberQueryService;

    private final CommentService commentService;

    private final JpaPostLikeRepository jpaPostLikeRepository;

    private final RecommandRepository recommandRepository;

    private final ApplicationEventPublisher applicationEventPublisher;
    private Post getPost(Long id) {
        return postReadRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("post not found"));
    }

    public PostResponseDto getPostDetail(Long postId, Long memberId) {
        Post post = getPost(postId);
        Long likeCount = getLikeCount(postId);
        List<LikedMembersInfo> likedMemberInfoList = getLikedMemberInfoList(postId);

        boolean hasLiked = false;
        if (memberId != null){
            hasLiked = jpaPostLikeRepository.existsByPostIdAndMemberId(postId, memberId);
        }
        applicationEventPublisher.publishEvent(new RecommandEvent(this,postId,memberId));
        Long commentCount = commentService.getCommentCount(postId);
        return PostResponseDto.ofPost(post, likeCount, likedMemberInfoList, hasLiked, commentCount);
    }

    public List<LikedMembersInfo> getLikedMemberInfoList(Long postId){
        List<Long> likedMemberIds = postReadRepository.findLikedMemberByPostId(postId);
        List<Member> memberList = memberQueryService.findAllByIds(likedMemberIds);

        return memberList.stream().map(LikedMembersInfo::of).toList();
    }

    public Long getLikeCount(Long postId){
        return postReadRepository.getLikeCount(postId);
    }

    public List<PostListResponseDto> getPostList(PostSearchType postSearchType, String searchString, PostSortCondition postSortCondition, String continent, List<Theme> themes){
        List<Post> postList = postReadRepository.findPostListBySearchTypeAndSortCondition(postSearchType, searchString, postSortCondition, continent, themes);
        return postList.stream()
                .map(each -> PostListResponseDto.of(each, commentService.getCommentCount(each.getId()), getLikeCount(each.getId())))
                .toList();
    }

    public List<PostListResponseDto> getPopularPostListByTheme(List<Theme> themeList){
        List<Post> popularPostList = postReadRepository.findPopularPostListByTheme(themeList);
        return popularPostList.stream()
                .map(each -> PostListResponseDto.of(each, commentService.getCommentCount(each.getId()), getLikeCount(each.getId())))
                .toList();
    }



    public List<PostListResponseDto> getRecommandPost(Long memberId) {
        Recommand recommand = recommandRepository.findByMemberId(memberId).orElseThrow(
                () -> new IllegalArgumentException("Member has not recommend this post")
        );

        List<String> topThemes = recommand.getTheme().entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(2)
                .map(Map.Entry::getKey)
                .toList();

        List<String> topCountry = recommand.getCountry().entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(2)
                .map(Map.Entry::getKey)
                .toList();

        List<Theme> themeList = new ArrayList<>();

        topThemes.forEach(themeStr -> {
            Theme themeEnum = Theme.valueOf(themeStr.toUpperCase());
            themeList.add(themeEnum);
        });


        List<Post> popularPostList = postReadRepository.findByRecommandThemeOrCountry(themeList,topCountry);
        return popularPostList.stream()
                .map(each -> PostListResponseDto.of(each, commentService.getCommentCount(each.getId()), getLikeCount(each.getId())))
                .toList();

    }
    public List<PostListResponseDto> getMyPostList(Long memberId) {
        List<Post> myPostList = postReadRepository.getMyPostList(memberId);
        return myPostList.stream()
                .map(each ->  PostListResponseDto.of(each, commentService.getCommentCount(each.getId()), getLikeCount(each.getId())))
                .toList();

    }
}
