package com.wsb.wsbfinalproject2022.security;

import com.wsb.wsbfinalproject2022.authority.Person;
import com.wsb.wsbfinalproject2022.authority.PersonController;
import com.wsb.wsbfinalproject2022.projects.ProjectController;
import com.wsb.wsbfinalproject2022.projects.ProjectService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import static java.nio.file.Paths.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PersonController.class)
public class SecuredControllerWebMvcIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    ProjectController personController;

    @WithMockUser(value = "spring")
    @Test
    public void givenAuthRequestOnPrivateService_shouldSucceedWith200() throws Exception {
        mvc.perform((RequestBuilder) get("/users/users")).andExpect(status().isOk());
    }
}