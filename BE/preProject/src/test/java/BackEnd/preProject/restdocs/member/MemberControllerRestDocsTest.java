package BackEnd.preProject.restdocs.member;


import BackEnd.preProject.member.controller.MemberController;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
@WebMvcTest(MemberController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class MemberControllerRestDocsTest {
    @Autowired
    private MockMvc mockMvc;

//    @MockBean

    @Test
    public void signupTest() throws Exception {
//Exception        //given
//
//        //when
//        ResultActions actions =
//                mockMvc.perform(
//
//                );
//        //then
//        actions
//                .andExpect()
    }
}
