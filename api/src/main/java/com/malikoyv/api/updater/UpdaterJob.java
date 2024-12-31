package com.malikoyv.api.updater;

import com.malikoyv.api.services.AuthorService;
import com.malikoyv.api.services.BookService;
import com.malikoyv.api.services.RatingService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class UpdaterJob implements IUpdaterJob {
    private final AuthorService authorService;
    private final BookService bookService;
    private final RatingService ratingService;
    private final Random r = new Random();

    public UpdaterJob(AuthorService authorService, BookService bookService, RatingService ratingService) {
        this.authorService = authorService;
        this.bookService = bookService;
        this.ratingService = ratingService;
    }

    @Scheduled(cron = "0 0 * * * ?") // Run every hour
    public void updateAuthors() {
        System.out.println("Updating authors from Open Library...");

        authorService.updateAuthorsFromOpenLibrary(String.valueOf((char)(r.nextInt(26) + 'a')));
        System.out.println("Authors update completed.");
    }

    @Scheduled(cron = "0 30 * * * ?") // Run every hour at 30 minutes
    public void updateBooks() {
        System.out.println("Updating books from Open Library...");
        bookService.updateBooksFromOpenLibrary(String.valueOf((char)(r.nextInt(26) + 'a')));
        System.out.println("Books update completed.");
    }

    @Scheduled(cron = "0 */5 * * * ?") // Run every 5 minutes
    public void updateRating() {
        System.out.println("Updating ratings for all books...");
        bookService.getAllBooks().forEach(book -> {
            try {
                ratingService.updateRatingByBookKey(book.key());
                System.out.println("Updated rating for book: " + book.title());
            } catch (Exception e) {
                System.err.println("Failed to update rating for book key: " + book.key() + " - " + e.getMessage());
            }
        });
        System.out.println("Ratings update completed.");
    }

}
