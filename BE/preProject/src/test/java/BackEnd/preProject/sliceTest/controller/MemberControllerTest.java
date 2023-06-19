package BackEnd.preProject.sliceTest.controller;

import BackEnd.preProject.member.dto.MemberDto;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class MemberControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @Test
    void signupTest() throws Exception{
        //Given
        MemberDto.Signup signup = new MemberDto.Signup("Kang-Dongwoo","genius",
                "ehddn@naver.com","testPassword1!", "testPassword1!");


        String content = gson.toJson(signup);

        //When
        ResultActions actions =
                mockMvc.perform(
                        post("/signup")  // (4)
                        .accept(MediaType.APPLICATION_JSON) // (5)
                        .contentType(MediaType.APPLICATION_JSON) // (6)
                        .content(content));
        //Then
        actions
                .andExpect(status().isCreated())
                .andExpect(header().string("Location",is(startsWith("/signup"))));
    }

    @Test
    void patchMemberTest() throws Exception {
        MemberDto.Signup signup = new MemberDto.Signup("Kang-Dongwoo","genius",
                "ehddn@naver.com","testPassword1!", "testPassword1!");


        String content = gson.toJson(signup);

        //When
        ResultActions postActions =
                mockMvc.perform(
                        post("/signup")  // (4)
                                .accept(MediaType.APPLICATION_JSON) // (5)
                                .contentType(MediaType.APPLICATION_JSON) // (6)
                                .content(content));



    }


}
