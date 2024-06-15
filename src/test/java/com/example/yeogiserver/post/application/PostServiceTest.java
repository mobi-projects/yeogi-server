package com.example.yeogiserver.post.application;

import com.example.yeogiserver.member.domain.Member;
import com.example.yeogiserver.member.repository.MemberJpaRepository;
import com.example.yeogiserver.post.application.dto.PostRequestDto;
import com.example.yeogiserver.post.domain.Post;
import com.example.yeogiserver.post.repository.JpaPostRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired
    JpaPostRepository postRepository;

    @Autowired
    PostService postService;

    @Autowired
    EntityManager em;

    @MockBean
    MemberJpaRepository memberRepository;


    @Test
    void name() {
        // given
        List<String> memoStrings = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            memoStrings.add("test");
        }

        Member member = mock(Member.class);

        // when
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(member));
        PostRequestDto postRequestDto = new PostRequestDto("test", "test", LocalDateTime.now(), LocalDateTime.now(),  "test", "test", memoStrings);
        postService.createPost("test", postRequestDto);
        em.clear();

        // then
        Post post = postRepository.findAll().get(0);
        assertThat(post.getShortPostList()).hasSameSizeAs(memoStrings);
    }
}
