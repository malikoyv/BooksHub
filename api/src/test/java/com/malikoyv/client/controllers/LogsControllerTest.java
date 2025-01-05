package com.malikoyv.client.controllers;

import com.malikoyv.api.controllers.LogsController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class LogsControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private LogsController logsController;

    private static final Path LOG_FILE_PATH = Paths.get("logs/application.log");

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(logsController).build();

        Files.createDirectories(LOG_FILE_PATH.getParent());
    }

    @Test
    public void testGetLogs() throws Exception {
        List<String> mockLogs = Arrays.asList(
                "INFO: Application started",
                "WARN: Low memory warning",
                "ERROR: NullPointerException"
        );

        Files.write(LOG_FILE_PATH, mockLogs);

        int page = 0;
        int size = 2;

        mockMvc.perform(get("/api/logs")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(size))
                .andExpect(jsonPath("$[0]").value("INFO: Application started"))
                .andExpect(jsonPath("$[1]").value("WARN: Low memory warning"));

        Files.deleteIfExists(LOG_FILE_PATH);
    }

    @Test
    public void testGetLogsOutOfBounds() throws Exception {
        List<String> mockLogs = Arrays.asList(
                "INFO: Application started",
                "WARN: Low memory warning",
                "ERROR: NullPointerException"
        );

        Files.write(LOG_FILE_PATH, mockLogs);

        int page = 5;
        int size = 2;

        mockMvc.perform(get("/api/logs")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));

        Files.deleteIfExists(LOG_FILE_PATH);
    }
}
