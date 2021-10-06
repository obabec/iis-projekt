package isu.library.model.service;

import isu.library.model.entity.Book;

public interface BookService {
    public Iterable<Book> findAll();
    public Iterable<Book> findByName(String name);
}
