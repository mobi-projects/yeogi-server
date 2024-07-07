package com.example.yeogiserver.post.repository;

import com.example.yeogiserver.global.fixture.MemberFixture;
import com.example.yeogiserver.global.fixture.PostFixture;
import com.example.yeogiserver.member.domain.Member;
import com.example.yeogiserver.member.repository.MemberJpaRepository;
import com.example.yeogiserver.post.domain.Post;
import com.example.yeogiserver.post.domain.PostTheme;
import com.example.yeogiserver.post.domain.Theme;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Transactional
class DefaultPostReadRepositoryTest {

    @Autowired
    private MemberJpaRepository memberJpaRepository;

    @Autowired
    private JpaPostRepository jpaPostRepository;

    private Member testMemberFixture;

    @Autowired
    private QueryDslPostRepository queryDslPostRepository;

    @BeforeEach
    void setUp() {
        Member testMemberFixture = MemberFixture.TEST_MEMBER_FIXTURE;
        memberJpaRepository.save(testMemberFixture);
    }

    @Test
    @DisplayName("뷰카운트가 높은 레코드가 항상 앞에 온다.")
    void higherViewCountRecordAlwaysComesFirst() {
        Post post = PostFixture.createDefaultFixtureOf(testMemberFixture);
        post.replaceThemeList(List.of(new PostTheme(Theme.ACTIVITY)));

        Post post2 = PostFixture.createDefaultFixtureOf(testMemberFixture);
        post2.replaceThemeList(List.of(new PostTheme(Theme.ACTIVITY)));

        jpaPostRepository.saveAll(List.of(post2, post));

        post.addViewCount();
        jpaPostRepository.flush(); // 변경사항을 반영한다.

        Optional<Post> expected = jpaPostRepository.findFirstByPostThemeListThemeOrderByViewCountDescCreatedAtDesc(Theme.ACTIVITY);

        SoftAssertions.assertSoftly(
                softly -> {
                    softly.assertThat(post.getViewCount()).isEqualTo(2);
                    softly.assertThat(expected.isPresent()).isTrue();
                    softly.assertThat(expected.get().getId()).isEqualTo(post.getId());
                }
        );
    }

    @Test
    @DisplayName("만약 viewCount 가 같은 포스트가 있다면, 생성 일자 기준으로 정렬된다.")
    void ifSameViewCountThenOrderByCreatedAtDesc() {
        Post post = PostFixture.createDefaultFixtureOf(testMemberFixture);
        post.replaceThemeList(List.of(new PostTheme(Theme.ACTIVITY)));

        Post post2 = PostFixture.createDefaultFixtureOf(testMemberFixture);
        post2.replaceThemeList(List.of(new PostTheme(Theme.ACTIVITY)));


        jpaPostRepository.save(post);
        jpaPostRepository.save(post2);

        Optional<Post> expected = jpaPostRepository.findFirstByPostThemeListThemeOrderByViewCountDescCreatedAtDesc(Theme.ACTIVITY);


        SoftAssertions.assertSoftly(
                softly -> {
                    softly.assertThat(expected.isPresent()).isTrue();
                    softly.assertThat(post2.getCreatedAt().isAfter(post.getCreatedAt())).isTrue();
                    softly.assertThat(expected.get().getId()).isEqualTo(post2.getId());
                }
        );
    }

    @Test
    @DisplayName("만약 다른 테마에서 이미 발견한 포스트라면, 응답 포스트 리스트에 추가되지 않는다.")
    void shouldNotContainsAlreadyFoundPost() {
        Post post = PostFixture.createDefaultFixtureOf(testMemberFixture);
        Post post2 = PostFixture.createDefaultFixtureOf(testMemberFixture);
        Post post3 = PostFixture.createDefaultFixtureOf(testMemberFixture);

        List<Theme> themeList = List.of(Theme.ACTIVITY, Theme.EATING, Theme.PACKAGE);
        List<PostTheme> postThemes = themeList.stream().map(PostTheme::new).toList();
        List<PostTheme> postThemes2 = themeList.stream().map(PostTheme::new).toList();
        List<PostTheme> postThemes3 = themeList.stream().map(PostTheme::new).toList();

        post.replaceThemeList(postThemes);
        post2.replaceThemeList(postThemes2);
        post3.replaceThemeList(postThemes3);

        jpaPostRepository.save(post);
        jpaPostRepository.save(post2);
        jpaPostRepository.save(post3);

        List<Post> resultSet = new ArrayList<>();
        Post dummy = new Post();

        for (Theme theme : themeList) {
            List<Post> list = jpaPostRepository.findAllByPostThemeListThemeOrderByViewCountDescCreatedAtDesc(PageRequest.of(0, 10), theme).stream().toList();
            if (list.isEmpty()){
                resultSet.add(dummy); // 더미 포스트 추가
                continue;
            }

            boolean found = false;
            for (Post each : list) {
                if (!resultSet.contains(each)) {
                    resultSet.add(each);
                    found = true;
                    break;
                }
            }

            if (!found) {
                resultSet.add(dummy); // 더미 포스트 추가
            }
        }

        SoftAssertions.assertSoftly(
                softly -> {
                    softly.assertThat(resultSet).hasSize(3);
                    softly.assertThat(resultSet).contains(post, post2, post3);
                }
        );

    }

    @Test
    @DisplayName("테마에 해당하는 포스트가 없다면 더미를 리턴한다.")
    void shouldReturnDummyIfPostNotExists() {
        Post post = PostFixture.createDefaultFixtureOf(testMemberFixture);
        Post post2 = PostFixture.createDefaultFixtureOf(testMemberFixture);

        List<Theme> themeList = List.of(Theme.ACTIVITY, Theme.EATING, Theme.PACKAGE);
        List<PostTheme> postThemes = themeList.stream().map(PostTheme::new).toList();
        List<PostTheme> postThemes2 = themeList.stream().map(PostTheme::new).toList();

        post.replaceThemeList(postThemes);
        PostTheme postTheme = postThemes2.getFirst();
        post2.replaceThemeList(List.of(postTheme));

        jpaPostRepository.save(post);
        jpaPostRepository.save(post2);

        List<Post> resultSet = new ArrayList<>();
        Post dummy = new Post();

        for (Theme theme : themeList) {
            List<Post> list = jpaPostRepository.findAllByPostThemeListThemeOrderByViewCountDescCreatedAtDesc(PageRequest.of(0, 10), theme).stream().toList();
            if (list.isEmpty()){
                resultSet.add(dummy); // 더미 포스트 추가
                continue;
            }

            boolean found = false;
            for (Post each : list) {
                if (!resultSet.contains(each)) {
                    resultSet.add(each);
                    found = true;
                    break;
                }
            }

            if (!found) {
                resultSet.add(dummy); // 더미 포스트 추가
            }
        }

        SoftAssertions.assertSoftly(
                softly -> {
                    softly.assertThat(resultSet).hasSize(3);
                    softly.assertThat(resultSet).contains(post, post2, dummy);
                }
        );
    }
}