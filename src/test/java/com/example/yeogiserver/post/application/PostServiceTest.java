package com.example.yeogiserver.post.application;

import com.example.yeogiserver.member.application.MemberQueryService;
import com.example.yeogiserver.member.domain.Member;
import com.example.yeogiserver.post.application.dto.request.MemoRequestDto;
import com.example.yeogiserver.post.application.dto.request.PostRequestDto;
import com.example.yeogiserver.post.domain.Post;
import com.example.yeogiserver.post.domain.Theme;
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
    MemberQueryService memberQueryService;


    @Test
    void name() {
        // given
        List<MemoRequestDto> memoStrings = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            MemoRequestDto memoRequestDto = new MemoRequestDto("test", "testAddress");
            memoStrings.add(memoRequestDto);
        }

        Member member = mock(Member.class);

        // when
        when(memberQueryService.findById(anyLong())).thenReturn(member);
        PostRequestDto postRequestDto = new PostRequestDto("test", "test", LocalDateTime.now(), LocalDateTime.now(),  "test", "test", List.of(Theme.ACTIVITY), memoStrings, "address");
        postService.createPost("test", postRequestDto);
        em.clear();

        // then
        Post post = postRepository.findAll().get(0);
        assertThat(post.getMemoList()).hasSameSizeAs(memoStrings);
    }
}
