package com.wsb.wsbfinalproject2022.projects;

import com.wsb.wsbfinalproject2022.authority.Person;
import junit.framework.TestCase;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import static org.hamcrest.Matchers.*;

import java.util.Collections;

import java.util.List;


import static java.nio.file.Paths.get;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(ProjectController.class)
public class ProjectControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProjectService projectService;

    @Test
    public void testIndex() throws Exception {
        Project project = new Project();
        project.setCode("CODE");

        List<Project> projects = List.of(project);

        doReturn(projects).when(projectService).findAll(any());
        doReturn(Collections.emptySet()).when(projectService).findAll();

        mockMvc.perform((RequestBuilder) get("/projects"))
                .andExpect(status().isOk())
                .andExpect(view().name("projects/index"))
                .andExpect(model().attribute("projects", projects))
                .andExpect(model().attribute("creators", Collections.emptySet()));
    }



}