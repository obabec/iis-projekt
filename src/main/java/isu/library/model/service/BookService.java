package isu.library.model.service;

import isu.library.model.entity.Book;

import java.sql.Date;

public interface BookService {
    Iterable<Book> findAll();
    Iterable<Book> findByName(String name, String libraryName);
    Iterable<Book> findAvailableByName(String name, String libraryName);
    Iterable<Book> findByAuthorName(String authorName, String libraryName);
    Iterable<Book> findAvailableBooks(String libraryName);
    Iterable<Book> findByGenre(String genre, String libraryName);
    Iterable<Book> findAvailableByGenre(String genre, String libraryName);
    Iterable<Book> findAllInLibrary(String libraryName);
    Iterable<Book> findAvailableByAuthorName(String authorName, String libraryName);
    Iterable<String> findAllGenres();
    Iterable<Book> executeQuery(String query);
    void addNewBook(Integer libraryId, String name, Date release, String isbn, String publisher, String genre, Short rate);
}
