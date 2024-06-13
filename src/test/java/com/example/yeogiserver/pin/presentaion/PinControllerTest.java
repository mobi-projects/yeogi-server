//package com.example.yeogiserver.pin.presentaion;
//
//import com.epages.restdocs.apispec.ResourceSnippetParameters;
//import com.example.yeogiserver.common.config.SecurityConfig;
//import com.example.yeogiserver.member.application.MemberService;
//import com.example.yeogiserver.member.domain.Gender;
//import com.example.yeogiserver.member.dto.SignupMember;
//import com.example.yeogiserver.mypage.application.PinService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.Import;
//import org.springframework.http.MediaType;
//import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
//import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
//import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
//import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
//import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
//import static org.springframework.restdocs.payload.JsonFieldType.STRING;
//import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@AutoConfigureMockMvc
//@AutoConfigureRestDocs
//@Import(SecurityConfig.class)
//@SpringBootTest
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//public class PinControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Autowired
//    private PinService pinService;
//    @Autowired
//    private MemberService memberService;
//
//    @BeforeEach
//    void init() {
//        SignupMember.Request request = new SignupMember.Request("mobi@gmail.com", "mobi123", Gender.M, "mobi", "20-29" , "mobi.jpg");
//        memberService.signup(request);
//    }
//    @DisplayName("핀 등록")
//    @Test
//    void addPin() throws Exception {
//        String requestJson = "{\"x\":\"12.3\", \"y\":\"42.1\", \"email\":\"mobi@gmail.com\",\"postId\":\"1\"}";
//        String responseJson ="댓글 작성이 완료 되었습니다.";
//        mockMvc.perform(RestDocumentationRequestBuilders.post("/pin")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(requestJson))
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andDo(document("PIN" ,
//                        preprocessRequest(prettyPrint()) ,
////                        preprocessResponse(prettyPrint()) ,
//                        resource(ResourceSnippetParameters.builder()
//                                .tag("핀 API")
//                                .summary("핀 생성")
//                                .requestFields(
//                                        fieldWithPath("x").type(STRING).description("x"),
//                                        fieldWithPath("y").type(STRING).description("y"),
//                                        fieldWithPath("email").type(STRING).description("이메일"),
//                                        fieldWithPath("postId").type(STRING).description("글 번호")
//                                )
//                                .build()
//                        )
//                ));
//
//
//    }
//}
