package com.malikoyv.core.repositories;

import org.springframework.stereotype.Repository;

@Repository
public class CatalogData implements ICatalogData {
    private final IBookRepository bookRepository;
    private final IAuthorRepository authorRepository;
    private final IReviewRepository reviewRepository;
    private final IUserRepository userRepository;
    private final IEditionRepository editionRepository;
    private final ISubjectRepository subjectRepository;
    private final IRatingRepository ratingRepository;
    private final ICountRepository countRepository;
    private final ISummaryRepository summaryRepository;

    public CatalogData(IBookRepository bookRepository, IAuthorRepository authorRepository, IReviewRepository reviewRepository, IUserRepository userRepository, IEditionRepository editionRepository, ISubjectRepository subjectRepository, IRatingRepository ratingRepository, ICountRepository countRepository, ISummaryRepository summaryRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.editionRepository = editionRepository;
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
    public IReviewRepository getReviews() {
        return reviewRepository;
    }

    @Override
    public IUserRepository getUsers() {
        return userRepository;
    }

    @Override
    public IEditionRepository getEditions() {
        return editionRepository;
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
