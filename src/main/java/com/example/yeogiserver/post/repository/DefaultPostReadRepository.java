package com.example.yeogiserver.post.repository;

import com.example.yeogiserver.member.domain.Gender;
import com.example.yeogiserver.member.domain.Member;
import com.example.yeogiserver.post.domain.Post;
import com.example.yeogiserver.post.domain.PostLike;
import com.example.yeogiserver.post.domain.PostReadRepository;
import com.example.yeogiserver.post.domain.Theme;
import com.example.yeogiserver.post.presentation.search_condition.PostSearchType;
import com.example.yeogiserver.post.presentation.search_condition.PostSortCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class DefaultPostReadRepository implements PostReadRepository {

    private static final String DUMMY = "DUMMY";

    private static final Member DUMMY_MEMBER = Member.of(DUMMY, DUMMY, DUMMY, DUMMY, DUMMY, DUMMY, DUMMY, Gender.M);

    private static final Post DUMMY_POST = new Post(
            DUMMY,
            LocalDateTime.now().minusDays(1),
            LocalDateTime.now(),
            DUMMY,
            DUMMY,
            DUMMY_MEMBER,
            DUMMY,
            DUMMY
    );
    // TODO : 더미에 대한 처리를 완료한다

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
    public List<Post> findPostListBySearchTypeAndSortCondition(PostSearchType postSearchType, String searchString, PostSortCondition postSortCondition, Theme theme){
        return queryDslPostRepository.findPostListBySearchTypeAndSortCondition(postSearchType, searchString, postSortCondition, theme);
    }

    @Override
    public List<Post> findPopularPostListByTheme(List<Theme> themeList) {

        List<Post> resultList = new ArrayList<>();
        for (Theme theme : themeList) {
            List<Post> list = jpaPostRepository.findAllByPostThemeListThemeOrderByViewCountDescCreatedAtDesc(PageRequest.of(0, 10), theme).stream().toList();
            addPostToResultListIfNotExists(list, resultList, DUMMY_POST);
        }

        return resultList;
    }

    private void addPostToResultListIfNotExists(List<Post> list, List<Post> resultList, Post dummy) {
        if (list.isEmpty()){
            resultList.add(dummy); // 더미 포스트 추가
            return;
        }

        boolean found = false;
        for (Post each : list) {
            if (!resultList.contains(each)) {
                resultList.add(each);
                found = true;
                break;
            }
        }

        if (!found) {
            resultList.add(dummy); // 더미 포스트 추가
        }
    }
}
