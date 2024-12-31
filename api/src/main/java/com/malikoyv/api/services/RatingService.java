package com.malikoyv.api.services;

import com.malikoyv.client.IBooksClient;
import com.malikoyv.client.contract.BookDto;
import com.malikoyv.client.contract.BookPagedResultDto;
import com.malikoyv.client.contract.RatingDto;
import com.malikoyv.client.mappers.CountsMapper;
import com.malikoyv.client.mappers.SummaryMapper;
import com.malikoyv.core.model.*;
import com.malikoyv.core.repositories.ICatalogData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingService {

    private final ICatalogData db;
    private final IBooksClient booksClient;

    @Autowired
    public RatingService(ICatalogData db, IBooksClient booksClient) {
        this.db = db;
        this.booksClient = booksClient;
    }

    public Rating getRatingByBookId(Long bookId) {
        return db.getRatings().findByBookId(bookId)
                .orElseThrow(() -> new RuntimeException("Rating not found for Book ID: " + bookId));
    }

    public void updateRatingByBookKey(String key) {
        Book book = db.getBooks().findByKey(key);

        RatingDto response = booksClient.getRatings(book.getKey().split("/")[2]);

        if (response != null) {
            Counts dbCounts = new CountsMapper().map(response.countsDto());
            Summary summary = new SummaryMapper().map(response.summaryDto());

            db.getSummaries().save(new Summary(summary.getAverage(), summary.getCount(), summary.getSortableAverage()));
            db.getCounts().save(new Counts(dbCounts.getOneStar(), dbCounts.getTwoStars(), dbCounts.getThreeStars(), dbCounts.getFourStars(), dbCounts.getFiveStars()));
            db.getRatings().save(new Rating(book, summary, dbCounts));
            System.out.println("Saved/Updated rating: " + response);
        }
    }

    private RatingDto toDto(Rating rating) {
        return new RatingDto(
                new SummaryMapper().map(rating.getSummary()),
                new CountsMapper().map(rating.getCounts())
        );
    }
}