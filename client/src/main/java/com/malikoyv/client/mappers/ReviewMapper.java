package com.malikoyv.client.mappers;

import com.malikoyv.client.contract.ReviewDto;
import com.malikoyv.core.model.Review;
import org.springframework.stereotype.Service;

@Service
public class ReviewMapper implements IMapper<ReviewDto, Review> {
    @Override
    public Review map(ReviewDto reviewDto) {
        return map(reviewDto, new Review());
    }

    @Override
    public Review map(ReviewDto reviewDto, Review review) {
        review.setRating(reviewDto.rating());
        review.setContent(reviewDto.content());
        return review;
    }

    public ReviewDto toDto(Review review) {
        return new ReviewDto(review.getId(), review.getContent(), review.getRating());
    }
}
