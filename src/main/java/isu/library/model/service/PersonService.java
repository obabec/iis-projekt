package isu.library.model.service;

import isu.library.model.entity.Book;

public interface PersonService {
    Iterable<Book> findAuthorsForBooks(Iterable<Book> books);
}
