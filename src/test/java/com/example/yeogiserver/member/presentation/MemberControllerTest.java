package com.example.yeogiserver.member.presentation;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.example.yeogiserver.common.application.RedisService;
import com.example.yeogiserver.common.config.SecurityConfig;
import com.example.yeogiserver.common.config.WithMockUser;
import com.example.yeogiserver.member.application.MemberQueryService;
import com.example.yeogiserver.member.application.MemberService;
import com.example.yeogiserver.member.domain.Gender;
import com.example.yeogiserver.member.domain.Member;
import com.example.yeogiserver.member.domain.Role;
import com.example.yeogiserver.member.dto.MemberDto;
import com.example.yeogiserver.member.dto.SignupMember;
import com.example.yeogiserver.member.repository.DefaultMemberRepository;
import com.example.yeogiserver.member.repository.MemberJpaRepository;
import com.example.yeogiserver.security.config.JwtTokenProvider;
import com.example.yeogiserver.security.domain.CustomUserDetails;
import com.example.yeogiserver.security.domain.Token;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Optional;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.headerWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import(SecurityConfig.class)
@SpringBootTest
public class MemberControllerTest {

    @Autowired
    private MockMvc mocMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberJpaRepository memberRepository;

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
    void deleteAll() {
        memberRepository.deleteAll();
    }

    public HttpHeaders getHeader() {
        CustomUserDetails customUserDetails = CustomUserDetails.of("mobi@gmail.com", Role.USER);
        Token token = jwtTokenProvider.generateToken(customUserDetails);
        String accessToken = token.getAccessToken();
        String refreshToken = token.getRefreshToken();
        redisService.setValue(customUserDetails.getEmail(), refreshToken , Duration.ofMillis(token.getAccessTokenExpiresIn()));

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization" , "Bearer " + accessToken);
        httpHeaders.set("Refresh" , refreshToken);
        return httpHeaders;
    }

    @DisplayName("회원 가입")
    @Test
    void signup() throws Exception {

        SignupMember.Request request = new SignupMember.Request("mobi@gmail.com", "mobi123", Gender.M, "mobi", "20-29" , "mobi.jpg");
        SignupMember.Response response = new SignupMember.Response("mobi@gmail.com", "mobi");

        mocMvc.perform(post("/member")
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
                                        fieldWithPath("ageRange").type(STRING).description("유저 연령대"),
                                        fieldWithPath("profile").type(STRING).description("유저 프로필")
                                ).responseFields(
                                        fieldWithPath("email").type(STRING).description("유저 이메일"),
                                        fieldWithPath("nickname").type(STRING).description("유저 닉네임")
                                ).build()
                        )
                ));
    }

    @DisplayName("유저 정보")
    @Test
    @WithMockUser
    void getMember() throws Exception{

        HttpHeaders header = getHeader();

        mocMvc.perform(get("/member")
                        .headers(header))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("유저 정보" ,
                        preprocessRequest(prettyPrint()) ,
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("USER API")
                                .summary("유저 정보")
                                .requestHeaders(
                                        headerWithName("Authorization").description("JWT 토큰"),
                                        headerWithName("Refresh").description("JWT Refresh 토큰")
                                )
                                .responseFields(
                                        fieldWithPath("id").type(NUMBER).description("유저 ID"),
                                        fieldWithPath("email").type(STRING).description("유저 이메일"),
                                        fieldWithPath("nickname").type(STRING).description("유저 닉네임"),
                                        fieldWithPath("gender").type(STRING).description("유저 성별"),
                                        fieldWithPath("ageRange").type(STRING).description("유저 연령대"),
                                        fieldWithPath("profile").type(STRING).description("유저 프로필")
                                ).build()
                        )
                        ));
    }

    @DisplayName("유저 정보 업데이트")
    @Test
    @WithMockUser
    void updateMember() throws Exception{

        MemberDto request = new MemberDto(1L, "mobi@gmail.com", "mobi", "20-29", Gender.M, "mobi.jpg");

        mocMvc.perform(put("/member")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("유저 정보 업데이트" ,
                        preprocessRequest(prettyPrint()) ,
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("USER API")
                                .summary("유저 정보 업데이트")
                                .requestFields(
                                        fieldWithPath("id").type(NUMBER).description("유저 ID"),
                                        fieldWithPath("email").type(STRING).description("유저 이메일"),
                                        fieldWithPath("nickname").type(STRING).description("유저 닉네임"),
                                        fieldWithPath("gender").type(STRING).description("유저 성별"),
                                        fieldWithPath("ageRange").type(STRING).description("유저 연령대"),
                                        fieldWithPath("profile").type(STRING).description("유저 프로필")
                                )
                                .responseFields(
                                        fieldWithPath("id").type(NUMBER).description("유저 ID"),
                                        fieldWithPath("email").type(STRING).description("유저 이메일"),
                                        fieldWithPath("nickname").type(STRING).description("유저 닉네임"),
                                        fieldWithPath("gender").type(STRING).description("유저 성별"),
                                        fieldWithPath("ageRange").type(STRING).description("유저 연령대"),
                                        fieldWithPath("profile").type(STRING).description("유저 프로필")
                                ).build()
                        )
                ));
    }

    @DisplayName("유저 탈퇴")
    @Test
    @WithMockUser
    void deleteMember() throws Exception{

        HttpHeaders header = getHeader();

        mocMvc.perform(delete("/member")
                        .headers(header))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("유저 탈퇴" ,
                        preprocessRequest(prettyPrint()) ,
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .tag("USER API")
                                .summary("유저 탈퇴")
                                .requestHeaders(
                                        headerWithName("Authorization").description("JWT 토큰"),
                                        headerWithName("Refresh").description("JWT Refresh 토큰")
                                )
                                .build()
                        )
                ));
    }


}
