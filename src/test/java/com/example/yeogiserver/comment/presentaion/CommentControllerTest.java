package com.example.yeogiserver.comment.presentaion;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.example.yeogiserver.comment.application.CommentService;
import com.example.yeogiserver.common.config.SecurityConfig;
import com.example.yeogiserver.member.application.MemberService;
import com.example.yeogiserver.member.domain.Gender;
import com.example.yeogiserver.member.domain.Member;
import com.example.yeogiserver.member.domain.Role;
import com.example.yeogiserver.member.dto.SignupMember;
import com.example.yeogiserver.security.domain.CustomUserDetails;
import com.example.yeogiserver.security.dto.Auth;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import(SecurityConfig.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CommentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CommentService commentService;

    @Autowired
    private MemberService memberService;

    @Order(1)
    @DisplayName("댓글 생성")
    @Test
    void createComment() throws Exception {
        String requestJson = "{\"author\":\"test\", \"content\": \"contentTest\", \"postId\":\"1\"}";
        String responseJson ="댓글 작성이 완료 되었습니다.";
        mockMvc.perform(RestDocumentationRequestBuilders.post("/comment")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("댓글" ,
                        preprocessRequest(prettyPrint()) ,
                        preprocessResponse(prettyPrint()) ,
                        resource(ResourceSnippetParameters.builder()
                                .tag("댓글 API")
                                .summary("댓글 생성")
                                .requestFields(
                                        fieldWithPath("author").type(STRING).description("유저 이메일"),
                                        fieldWithPath("content").type(STRING).description("내용"),
                                        fieldWithPath("postId").type(STRING).description("글 번호")
                                ).responseFields(
                                        fieldWithPath("id").type(NUMBER).description("댓글 번호"),
                                        fieldWithPath("createdAt").type(STRING).description("댓글 번호"),
                                        fieldWithPath("modifiedAt").type(STRING).description("댓글 번호"),
                                        fieldWithPath("postId").type(NUMBER).description("댓글 번호"),
                                        fieldWithPath("content").type(STRING).description("댓글 번호"),
                                        fieldWithPath("author").type(STRING).description("댓글 번호")
                                )

                                .build()
                        )
                ));

    }
    @Order(2)
    @DisplayName("댓글 조회")
    @Test
    void getComments() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders.get("/comments/{postId}",1))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("댓글" ,
                        preprocessRequest(prettyPrint()) ,
                        preprocessResponse(prettyPrint()) ,
                        resource(ResourceSnippetParameters.builder()
                                .tag("댓글 API")
                                .summary("댓글 조회")
                                .pathParameters(
                                        parameterWithName("postId").description("댓글 번호")
                                ).responseFields(
                                        fieldWithPath("[].comment.id").type(NUMBER).description("댓글 번호"),
                                        fieldWithPath("[].comment.createdAt").type(STRING).description("댓글 번호"),
                                        fieldWithPath("[].comment.modifiedAt").type(STRING).description("댓글 번호"),
                                        fieldWithPath("[].comment.postId").type(NUMBER).description("댓글 번호"),
                                        fieldWithPath("[].comment.content").type(STRING).description("댓글 번호"),
                                        fieldWithPath("[].comment.author").type(STRING).description("댓글 번호"),
                                        fieldWithPath("[].likeCount").type(NUMBER).description("댓글 좋아요 수")
                                )
                                .build()
                        )
                ));

    }
    @Order(4)
    @DisplayName("댓글 삭제")
    @Test
    void deleteComment() throws Exception {
        mockMvc.perform(RestDocumentationRequestBuilders.delete("/comment/{commentId}",1))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("댓글" ,
                        preprocessRequest(prettyPrint()) ,
                        resource(ResourceSnippetParameters.builder()
                                .tag("댓글 API")
                                .summary("댓글 삭제")
                                .pathParameters(
                                        parameterWithName("commentId").description("댓글 번호")
                                )
                                .build()
                        )
                ));

    }


//    @Order(2)
//    @DisplayName("댓글 좋아요")
//    @Test
//    void saveLikeComment() throws Exception {
//        CustomUserDetails customUserDetails = CustomUserDetails.of("mobi@gmail.com", Role.USER);
//        mockMvc.perform(RestDocumentationRequestBuilders.post("/comment/like/{commentId}",1)
//                        .with(SecurityMockMvcRequestPostProcessors.user(customUserDetails)))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andDo(document("댓글" ,
//                        preprocessRequest(prettyPrint()) ,
//                        resource(ResourceSnippetParameters.builder()
//                                .tag("댓글 API")
//                                .summary("댓글 좋아요")
//                                .pathParameters(
//                                        parameterWithName("commentId").description("댓글 번호")
//                                )
//                                .build()
//                        )
//                ));
//
//    }
//    @Order(3)
//    @DisplayName("댓글 좋아요 해제")
//    @Test
//    void deleteLikeComment() throws Exception {
//        CustomUserDetails customUserDetails = CustomUserDetails.of("mobi@gmail.com", Role.USER);
//        mockMvc.perform(RestDocumentationRequestBuilders.delete("/comment/like/{commentId}",1)
//                        .with(SecurityMockMvcRequestPostProcessors.user(customUserDetails)))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andDo(document("댓글" ,
//                        preprocessRequest(prettyPrint()) ,
//                        resource(ResourceSnippetParameters.builder()
//                                .tag("댓글 API")
//                                .summary("댓글 좋아요 해제")
//                                .pathParameters(
//                                        parameterWithName("author").description("댓글 번호")
//                                )
//                                .build()
//                        )
//                ));
//
//    }
}
