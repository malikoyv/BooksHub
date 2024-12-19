package com.malikoyv.core.repositories;

public interface ICatalogData {
    IAuthorRepository getAuthors();
    IBookRepository getBooks();
    IReviewRepository getReviews();
    IUserRepository getUsers();
    IEditionRepository getEditions();
}
