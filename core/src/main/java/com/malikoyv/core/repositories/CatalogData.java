package com.malikoyv.core.repositories;

public class CatalogData implements ICatalogData {
    private final IBookRepository bookRepository;
    private final IAuthorRepository authorRepository;
    private final IReviewRepository reviewRepository;
    private final IUserRepository userRepository;
    private final IEditionRepository editionRepository;

    public CatalogData(IBookRepository bookRepository, IAuthorRepository authorRepository, IReviewRepository reviewRepository, IUserRepository userRepository, IEditionRepository editionRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.editionRepository = editionRepository;
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
}
