package com.malikoyv.core.repositories;

import org.springframework.stereotype.Repository;

@Repository
public class CatalogData implements ICatalogData {
    private final IBookRepository bookRepository;
    private final IAuthorRepository authorRepository;
    private final ISubjectRepository subjectRepository;
    private final IRatingRepository ratingRepository;
    private final ICountRepository countRepository;
    private final ISummaryRepository summaryRepository;

    public CatalogData(IBookRepository bookRepository, IAuthorRepository authorRepository, ISubjectRepository subjectRepository, IRatingRepository ratingRepository, ICountRepository countRepository, ISummaryRepository summaryRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.subjectRepository = subjectRepository;
        this.ratingRepository = ratingRepository;
        this.countRepository = countRepository;
        this.summaryRepository = summaryRepository;
    }


    @Override
    public IAuthorRepository getAuthors() {
        return authorRepository;
    }

    @Override
    public IBookRepository getBooks() {
        return bookRepository;
    }

    @Override
    public ISubjectRepository getSubjects() {
        return subjectRepository;
    }

    @Override
    public IRatingRepository getRatings() {
        return ratingRepository;
    }

    @Override
    public ISummaryRepository getSummaries() {
        return summaryRepository;
    }

    @Override
    public ICountRepository getCounts() {
        return countRepository;
    }
}
