package com.malikoyv.client.controllers;

import com.malikoyv.api.controllers.SubjectController;
import com.malikoyv.api.services.SubjectService;
import com.malikoyv.core.model.Subject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class SubjectControllerTest {

    private MockMvc mockMvc;

    @Mock
    private SubjectService subjectService;

    @InjectMocks
    private SubjectController subjectController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(subjectController).build();
    }

    @Test
    public void testCreateSubject() throws Exception {
        String name = "Mathematics";
        long expectedId = 1L;

        when(subjectService.saveSubject(name)).thenReturn(expectedId);

        mockMvc.perform(post("/api/subjects")
                        .param("name", name)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(expectedId));
    }

    @Test
    public void testGetAllSubjects() throws Exception {
        List<Subject> subjects = Arrays.asList(
                new Subject("Physics"),
                new Subject("Biology")
        );

        when(subjectService.getAllSubjects()).thenReturn(subjects);

        mockMvc.perform(get("/api/subjects")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(subjects.size()))
                .andExpect(jsonPath("$[0].name").value("Physics"))
                .andExpect(jsonPath("$[1].name").value("Biology"));
    }

    @Test
    public void testDeleteSubject() throws Exception {
        Long id = 1L;

        doNothing().when(subjectService).deleteSubject(id);

        mockMvc.perform(delete("/api/subjects/" + id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(subjectService).deleteSubject(id);
    }
}
