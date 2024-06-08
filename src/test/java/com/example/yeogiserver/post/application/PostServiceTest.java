package com.example.yeogiserver.post.application;

import com.example.yeogiserver.post.application.dto.PostRequestDto;
import com.example.yeogiserver.post.domain.Post;
import com.example.yeogiserver.post.repository.JpaPostRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class PostServiceTest {
    @Autowired
    JpaPostRepository postRepository;

    @Autowired
    PostService postService;

    @Autowired
    EntityManager em;

    @Test
    void name() {
        List<String> memoStrings = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            memoStrings.add("test");
        }

        postService.createPost(new PostRequestDto("test", "test", "test", "test", memoStrings));

        em.clear();

        Post post = postRepository.findAll().get(0);

        assertThat(post.getShortPostList()).hasSameSizeAs(memoStrings);
    }
}