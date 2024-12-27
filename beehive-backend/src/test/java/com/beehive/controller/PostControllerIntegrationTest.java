package com.beehive.controller;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PostControllerIntegrationTest {

    private final Logger log = LoggerFactory.getLogger(PostControllerIntegrationTest.class);

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAllPosts() throws Exception {
        long startTime = System.currentTimeMillis();

        mockMvc.perform(MockMvcRequestBuilders
                        .get("http://localhost:8097/api/v1.0/posts/all")
                        .param("username", "mockuser")
                        .with(SecurityMockMvcRequestPostProcessors.anonymous()))
                .andExpect(status().isOk())
                .andDo(result -> {
                    long endTime = System.currentTimeMillis();
                    long duration = endTime - startTime;
                    log.info("Request duration: {} ms", duration);
                });
    }
}