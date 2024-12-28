package com.malikoyv.api.controllers;

import com.malikoyv.client.contract.RatingDto;
import com.malikoyv.api.services.RatingService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping("/{bookId}")
    public RatingDto getRating(@PathVariable long bookId) {
        return ratingService.getRatingByBookId(bookId);
    }

    @PostMapping("/{bookId}")
    public void updateRating(@PathVariable long bookId) {
        ratingService.updateRatingByBookId(bookId);
    }
}

