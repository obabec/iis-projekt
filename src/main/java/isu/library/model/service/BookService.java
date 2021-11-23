/*
    IIS - projekt 2021
    Zadani: Knihovna
    Autori: Tomas Korbar <xkorba02>, Ondrej Babec <xbabec00>
 */
package isu.library.model.service;

import isu.library.model.entity.Book;

import java.sql.Date;

public interface BookService {
    Iterable<Book> findAll();

    Book findById(int id);

    Iterable<Book> executeQuery(String query);

    int addNewBook(Integer libraryId, String name, Date release, String isbn, String publisher, String genre, Short rate);

    int addNewBook(String name, Date release, String isbn, String publisher, String genre, Short rate);

    void updateBook(Book book);

    void removeById(int id);

    Iterable<String> findBooksByLibraryId(Integer libraryId);

    Iterable<Book> findAvailableBooks(Integer libraryId);

    boolean isBookAvailable(Integer bookId);

    Iterable<Book> findAllTitles();
}
