package com.malikoyv.core.repositories;

public interface ICatalogData {
    IAuthorRepository getAuthors();
    IBookRepository getBooks();
    ISubjectRepository getSubjects();
    IRatingRepository getRatings();
    ISummaryRepository getSummaries();
    ICountRepository getCounts();
}
