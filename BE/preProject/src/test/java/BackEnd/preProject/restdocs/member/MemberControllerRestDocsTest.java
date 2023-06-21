package BackEnd.preProject.restdocs.member;


import BackEnd.preProject.member.controller.MemberController;
import BackEnd.preProject.member.dto.SignupDto;
import BackEnd.preProject.member.entity.Member;
import BackEnd.preProject.member.mapper.MemberMapper;
import BackEnd.preProject.member.service.MemberService;
import com.google.gson.Gson;

import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;


import java.util.List;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;

import static BackEnd.preProject.utils.ApiDocumentUtils.getRequestPreProcessor;
import static BackEnd.preProject.utils.ApiDocumentUtils.getResponsePreProcessor;

import static org.mockito.BDDMockito.given;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
@WebMvcTest(MemberController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class MemberControllerRestDocsTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @MockBean
    private MemberMapper mapper;

    @Autowired
    private Gson gson;


    @WithMockUser
    @Test
    public void signupTest() throws Exception {
//given
        SignupDto signupDto = new SignupDto();
        signupDto.setUsername("testUsernametesft");
        signupDto.setEmail("testffftttt@test.com");
        signupDto.setNickname("testftttt");
        signupDto.setPassword("testPassword1!");
        signupDto.setCheckPassword("testPassword1!");

        Member signup = mapper.signupDtoToMember(signupDto);

        String content = gson.toJson(signupDto);
        given(memberService.signup(Mockito.any(Member.class))).willReturn(signup);


        ResultActions actions = mockMvc.perform(
                post("/signup").with(csrf())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );

        //then

        actions

                .andExpect(status().isCreated())
                .andDo(document(
                        "회원 가입",
                        getRequestPreProcessor(),
                        getResponsePreProcessor(),
                        requestFields(List.of(
                                fieldWithPath("username").type(JsonFieldType.STRING).description("아이디"), // (7-5)
                                fieldWithPath("nickname").type(JsonFieldType.STRING).description("별명"),
                                fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                                fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호"),
                                fieldWithPath("checkPassword").type(JsonFieldType.STRING).description("비밀번호 확인")
                        ))

//                        responseFields(List.of(
//                                fieldWithPath("data").type(JsonFieldType.OBJECT).description("응답 데이터"),
//                                fieldWithPath("data.username").type(JsonFieldType.STRING).description("아이디"),
//                                fieldWithPath("data.nickname").type(JsonFieldType.STRING).description("닉네임"),
//                                fieldWithPath("data.email").type(JsonFieldType.STRING).description("이메일"),
//                                fieldWithPath("data.createdAt").type(JsonFieldType.STRING).description("가입"))
                        )



                );
    }
}
