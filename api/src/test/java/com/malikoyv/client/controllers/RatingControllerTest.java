package com.malikoyv.client.controllers;

import com.malikoyv.api.controllers.RatingController;
import com.malikoyv.api.services.RatingService;
import com.malikoyv.client.contract.RatingDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RatingControllerTest {

    private MockMvc mockMvc;

    @Mock
    private RatingService ratingService;

    @InjectMocks
    private RatingController ratingController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(ratingController).build();
    }

    @Test
    public void testGetRating() throws Exception {
        String key = "bookKey";
        RatingDto expectedRatingDto = new RatingDto(
                new RatingDto.SummaryDto(4.5, 10, 45.0),
                new RatingDto.CountsDto(1, 2, 3, 4, 5)
        );

        when(ratingService.getRatingByBookKey(key)).thenReturn(expectedRatingDto);

        mockMvc.perform(get("/api/ratings/" + key)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}