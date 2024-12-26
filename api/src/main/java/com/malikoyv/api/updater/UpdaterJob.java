package com.malikoyv.api.updater;

import com.malikoyv.api.services.AuthorService;
import com.malikoyv.api.services.BookService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class UpdaterJob implements IUpdaterJob {
    private final AuthorService authorService;
    private final BookService bookService;

    public UpdaterJob(AuthorService authorService, BookService bookService) {
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @Scheduled(cron = "0 0 * * * ?") // Run every hour
    public void updateAuthors() {
        System.out.println("Updating authors from Open Library...");
        authorService.updateAuthorsFromOpenLibrary();
        System.out.println("Authors update completed.");
    }

    @Scheduled(cron = "0 30 * * * ?") // Run every hour at 30 minutes
    public void updateBooks() {
        System.out.println("Updating books from Open Library...");
        bookService.updateBooksFromOpenLibrary();
        System.out.println("Books update completed.");
    }
}
