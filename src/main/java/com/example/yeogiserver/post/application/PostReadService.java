package com.example.yeogiserver.post.application;

import com.example.yeogiserver.comment.application.CommentService;
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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostReadService {

    private final PostReadRepository postReadRepository;

    private final MemberQueryService memberQueryService;

    private final CommentService commentService;

    private Post getPost(Long id) {
        return postReadRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("post not found"));
    }

    public PostResponseDto getPostDetail(Long postId) {
        Post post = getPost(postId);
        Long likeCount = getLikeCount(postId);
        // commentCount
        List<LikedMembersInfo> likedMemberInfoList = getLikedMemberInfoList(postId);

        return PostResponseDto.ofPost(post, likeCount, likedMemberInfoList);
    }

    public List<LikedMembersInfo> getLikedMemberInfoList(Long postId){
        List<Long> likedMemberIds = postReadRepository.findLikedMemberByPostId(postId);
        List<Member> memberList = memberQueryService.findAllByIds(likedMemberIds);

        return memberList.stream().map(LikedMembersInfo::of).toList();
    }

    public Long getLikeCount(Long postId){
        return postReadRepository.getLikeCount(postId);
    }

    public List<PostListResponseDto> getPostList(PostSearchType postSearchType, String searchString, PostSortCondition postSortCondition, Theme theme){
        List<Post> postList = postReadRepository.findPostListBySearchTypeAndSortCondition(postSearchType, searchString, postSortCondition, theme);
        return postList.stream().map(
                each -> PostListResponseDto.of(each, getLikeCount(each.getId()), commentService.getCommentCount(each.getId()))
        ).toList();
    }
}
