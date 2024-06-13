package com.example.yeogiserver.security.presentation;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.example.yeogiserver.common.application.RedisService;
import com.example.yeogiserver.common.config.SecurityConfig;
import com.example.yeogiserver.member.application.MemberService;
import com.example.yeogiserver.member.domain.Gender;
import com.example.yeogiserver.member.domain.Role;
import com.example.yeogiserver.member.dto.SignupMember;
import com.example.yeogiserver.member.repository.MemberJpaRepository;
import com.example.yeogiserver.security.config.JwtTokenProvider;
import com.example.yeogiserver.security.domain.CustomUserDetails;
import com.example.yeogiserver.security.domain.Token;
import com.example.yeogiserver.security.dto.Auth;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Duration;
import java.time.LocalDate;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.headerWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import(SecurityConfig.class)
@SpringBootTest
public class AuthControllerTest {

    @Autowired
    private MockMvc mocMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberJpaRepository memberRepository;

    @Autowired
    private MemberService memberService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private RedisService redisService;

    @BeforeEach
    void init() {
        SignupMember.Request request = new SignupMember.Request("mobi@gmail.com", "mobi123", Gender.M, "mobi", "20-29" , "mobi.jpg");
        memberService.signup(request);
    }

    @AfterEach
    void destroy() {
       memberRepository.deleteAll();
    }


    @DisplayName("로그인")
    @Test
    void loginSuccess() throws Exception {

        Auth.Login login = new Auth.Login("mobi@gmail.com", "mobi123");

        mocMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(login)))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("로그인" ,
                        preprocessRequest(prettyPrint()) ,
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("인증 API")
                                .summary("로그인")
                                .requestFields(
                                        fieldWithPath("email").type(STRING).description("유저 이메일"),
                                        fieldWithPath("password").type(STRING).description("유저 비밀번호")
                                ).responseHeaders(
                                        headerWithName("Authorization").description("JWT 토큰 ( Bearer )"),
                                        headerWithName("Refresh").description("JWT Refresh 토큰")
                                )
                                .build()
                        )
                        ));
    }

    @DisplayName("로그아웃")
    @Test
    void logout() throws Exception{
        CustomUserDetails customUserDetails = CustomUserDetails.of("mobi@gmail.com", Role.USER);
        Token token = jwtTokenProvider.generateToken(customUserDetails);
        String accessToken = token.getAccessToken();
        String refreshToken = token.getRefreshToken();
        redisService.setValue(customUserDetails.getEmail(), refreshToken , Duration.ofMillis(token.getAccessTokenExpiresIn()));

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization" , "Bearer " + accessToken);
        httpHeaders.set("Refresh" , refreshToken);

        mocMvc.perform(post("/auth/logout")
                        .headers(httpHeaders))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("로그아웃",
                        preprocessRequest(prettyPrint()) ,
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("인증 API")
                                .summary("로그아웃")
                                .requestHeaders(
                                        headerWithName("Authorization").description("JWT 토큰"),
                                        headerWithName("Refresh").description("JWT Refresh 토큰")
                                ).build()
                        )
                ));
    }

    @DisplayName("JWT 토큰 재발급")
    @Test
    void reissue() throws Exception{
        CustomUserDetails customUserDetails = CustomUserDetails.of("mobi@gmail.com", Role.USER);
        Token token = jwtTokenProvider.generateToken(customUserDetails);
        String accessToken = token.getAccessToken();
        String refreshToken = token.getRefreshToken();
        redisService.setValue(customUserDetails.getEmail(), refreshToken , Duration.ofMillis(token.getAccessTokenExpiresIn()));

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization" , "Bearer " + accessToken);
        httpHeaders.set("Refresh" , refreshToken);

        mocMvc.perform(post("/auth/reissue")
                        .headers(httpHeaders))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("JWT 토큰 재발급",
                        preprocessRequest(prettyPrint()) ,
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("인증 API")
                                .summary("JWT 토큰 재발급")
                                .requestHeaders(
                                        headerWithName("Authorization").description("JWT 토큰"),
                                        headerWithName("Refresh").description("JWT Refresh 토큰")
                                ).responseFields(
                                        fieldWithPath("accessToken").description("JWT 토큰"),
                                        fieldWithPath("refreshToken").description("JWT Refresh 토큰")
                                )
                                .build()
                        )
                ));
    }
}
