package com.example.yeogiserver.global.fixture;

import com.example.yeogiserver.member.domain.Member;
import com.example.yeogiserver.post.domain.Post;

import java.time.LocalDateTime;

public class PostFixture {

    private static final String TEST_STRING = "TEST";

    public static Post createDefaultFixtureOf(Member member){
        return new Post(
                TEST_STRING, LocalDateTime.now(), LocalDateTime.now(), TEST_STRING, TEST_STRING, member, TEST_STRING, TEST_STRING
        );
    }

    public static Post createFixtureWithTime(Member member){
        return new Post(
                TEST_STRING, LocalDateTime.now(), LocalDateTime.now(), TEST_STRING, TEST_STRING, member, TEST_STRING, TEST_STRING
        );
    }
}
