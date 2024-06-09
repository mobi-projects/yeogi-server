package com.example.yeogiserver.member.repository;


import com.example.yeogiserver.security.domain.Token;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

//@SpringBootTest(classes = TestRedisConfig.class)
//class DefaultMemberRepositoryTest {
//
//    @Autowired
//    DefaultMemberRepository defaultMemberRepository;
//
//    @Test
//    void saveToRedis() {
//        Token token = new Token("test");
//
//        Token savedToken = defaultMemberRepository.saveToken(token);
//
//        assertThat(token).isEqualTo(savedToken);
//    }
//}