package com.malikoyv.core.repositories;

public interface ICatalogData {
    IAuthorRepository getAuthors();
    IBookRepository getBooks();
    IReviewRepository getReviews();
    IUserRepository getUsers();
    IEditionRepository getEditions();
    ISubjectRepository getSubjects();
    IRatingRepository getRatings();
    ISummaryRepository getSummaries();
    ICountRepository getCounts();
}
