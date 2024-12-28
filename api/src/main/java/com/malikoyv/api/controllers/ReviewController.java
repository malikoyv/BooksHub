package com.malikoyv.api.controllers;

import com.malikoyv.client.contract.ReviewDto;
import com.malikoyv.api.services.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("/user/{userId}/book/{bookId}")
    public long createReview(
            @PathVariable long userId,
            @PathVariable long bookId,
            @RequestBody ReviewDto dto
    ) {
        return reviewService.saveReview(userId, bookId, dto);
    }

    @GetMapping("/book/{bookId}")
    public List<ReviewDto> getReviewsForBook(@PathVariable long bookId) {
        return reviewService.getReviewsForBook(bookId);
    }

    @GetMapping("/user/{userId}")
    public List<ReviewDto> getReviewsByUser(@PathVariable long userId) {
        return reviewService.getReviewsByUser(userId);
    }

    @PutMapping("/{reviewId}")
    public void updateReview(@PathVariable long reviewId, @RequestBody ReviewDto dto) {
        reviewService.updateReview(reviewId, dto);
    }

    @DeleteMapping("/{reviewId}")
    public void deleteReview(@PathVariable long reviewId) {
        reviewService.deleteReview(reviewId);
    }
}
