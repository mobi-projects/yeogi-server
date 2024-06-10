package com.example.yeogiserver.member.presentation;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.example.yeogiserver.common.application.RedisService;
import com.example.yeogiserver.common.config.SecurityConfig;
import com.example.yeogiserver.member.application.MemberQueryService;
import com.example.yeogiserver.member.application.MemberService;
import com.example.yeogiserver.member.domain.Gender;
import com.example.yeogiserver.member.dto.SignupMember;
import com.example.yeogiserver.security.config.JwtTokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@WebMvcTest(MemberController.class)
@Import(SecurityConfig.class)
public class MemberControllerTest {

    @Autowired
    private MockMvc mocMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MemberService memberService;

    @MockBean
    private MemberQueryService memberQueryService;

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private RedisService redisService;

    @DisplayName("회원 가입")
    @Test
    void signup() throws Exception {

        SignupMember.Request request = new SignupMember.Request("mobi@gmail.com", "mobi123", Gender.M, "mobi", "20-29");
        SignupMember.Response response = new SignupMember.Response("mobi@gmail.com", "mobi");

        given(memberService.signup(any(SignupMember.Request.class))).willReturn(response);

        mocMvc.perform(post("/member/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("유저 회원 가입" ,
                        preprocessRequest(prettyPrint()) ,
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("USER API")
                                .summary("유저 회원 가입")
                                .requestFields(
                                        fieldWithPath("email").type(STRING).description("유저 이메일"),
                                        fieldWithPath("password").type(STRING).description("유저 비밀번호"),
                                        fieldWithPath("nickname").type(STRING).description("유저 닉네임"),
                                        fieldWithPath("gender").type(STRING).description("유저 성별"),
                                        fieldWithPath("birthday").type(STRING).description("유저 생년월일")
                                ).responseFields(
                                        fieldWithPath("email").type(STRING).description("유저 이메일"),
                                        fieldWithPath("nickname").type(STRING).description("유저 닉네임")
                                ).build()
                        )
                ));
    }
}
