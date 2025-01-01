package com.malikoyv.core.repositories;

public interface ICatalogData {
    IAuthorRepository getAuthors();
    IBookRepository getBooks();
    IEditionRepository getEditions();
    ISubjectRepository getSubjects();
    IRatingRepository getRatings();
    ISummaryRepository getSummaries();
    ICountRepository getCounts();
}
