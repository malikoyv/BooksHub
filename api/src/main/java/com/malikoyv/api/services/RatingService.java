package com.malikoyv.api.services;

import com.malikoyv.client.IBooksClient;
import com.malikoyv.client.contract.BookDto;
import com.malikoyv.client.contract.BookPagedResultDto;
import com.malikoyv.client.contract.RatingDto;
import com.malikoyv.client.mappers.CountsMapper;
import com.malikoyv.client.mappers.SummaryMapper;
import com.malikoyv.core.model.*;
import com.malikoyv.core.repositories.ICatalogData;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RatingService {

    private final ICatalogData db;
    private final IBooksClient booksClient;

    @Autowired
    public RatingService(ICatalogData db, IBooksClient booksClient) {
        this.db = db;
        this.booksClient = booksClient;
    }

    public RatingDto getRatingByBookKey(String key) {
        Rating rating = db.getRatings().findByBookKey(key);
        if (rating != null) {
            return toDto(rating);
        } else {
            return new RatingDto(
                    new RatingDto.SummaryDto(0.0, 0, 0.0),
                    new RatingDto.CountsDto(0, 0, 0, 0, 0)
            );
        }
    }

    @Transactional
    public void updateRatingByBookKey(String key) {
        try {
            Optional<Book> bookOptional = db.getBooks().findByKey(key);

            if (bookOptional.isEmpty()) {
                return;
            }

            Book book = bookOptional.get();

            String[] keyParts = book.getKey().split("/");
            String bookKey;
            if (keyParts.length >= 3) {
                bookKey = keyParts[2];
            } else {
                bookKey = key;
            }

            RatingDto response = booksClient.getRatings(bookKey);

            if (response != null) {
                Rating existingRating = db.getRatings().findByBookKey(key);
                if (existingRating == null) {
                    Counts dbCounts = new CountsMapper().map(response.countsDto());
                    Summary summary = new SummaryMapper().map(response.summaryDto());

                    db.getSummaries().save(summary);
                    db.getCounts().save(dbCounts);
                    db.getRatings().save(new Rating(book, summary, dbCounts));
                } else {
                    existingRating.setCounts(new CountsMapper().map(response.countsDto()));
                    existingRating.setSummary(new SummaryMapper().map(response.summaryDto()));
                    db.getRatings().save(existingRating);
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to update rating for book key: " + key + " - " + e.getMessage());
            throw e;
        }
    }

    public RatingDto toDto(Rating rating) {
        return new RatingDto(
                new SummaryMapper().map(rating.getSummary()),
                new CountsMapper().map(rating.getCounts())
        );
    }
}