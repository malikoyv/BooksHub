package com.malikoyv.api.services;

import com.malikoyv.client.mappers.ReviewMapper;
import com.malikoyv.client.contract.ReviewDto;
import com.malikoyv.core.model.Review;
import com.malikoyv.core.model.Book;
import com.malikoyv.core.model.User;
import com.malikoyv.core.repositories.ICatalogData;
import com.malikoyv.core.repositories.IReviewRepository;
import com.malikoyv.core.repositories.IBookRepository;
import com.malikoyv.core.repositories.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {
    private final ICatalogData db;
    private final ReviewMapper reviewMapper;

    public ReviewService(ICatalogData db, ReviewMapper reviewMapper) {
        this.db = db;
        this.reviewMapper = reviewMapper;
    }

    public long saveReview(long userId, long bookId, ReviewDto dto) {
        User user = db.getUsers().findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Book book = db.getBooks().findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        Review review = reviewMapper.map(dto);
        review.setUser(user);
        review.setBook(book);

        db.getReviews().save(review);
        return review.getId();
    }

    public ReviewDto getReview(long id) {
        Review review = db.getReviews().findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        return reviewMapper.toDto(review);
    }

    public List<ReviewDto> getReviewsForBook(long bookId) {
        return db.getReviews().findByBookId(bookId).stream()
                .map(reviewMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ReviewDto> getReviewsByUser(long userId) {
        return db.getReviews().findByUserId(userId).stream()
                .map(reviewMapper::toDto)
                .collect(Collectors.toList());
    }

    public void updateReview(long reviewId, ReviewDto dto) {
        Review review = db.getReviews().findById(reviewId)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        reviewMapper.map(dto, review);
        db.getReviews().save(review);
    }

    public void deleteReview(long id) {
        db.getReviews().deleteById(id);
    }
}

