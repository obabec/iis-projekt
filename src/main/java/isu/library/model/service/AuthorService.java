package isu.library.model.service;

import isu.library.model.entity.Author;
import isu.library.model.entity.Person;

import java.util.Optional;

public interface AuthorService {

    Optional<Author> findAuthorById(int integer);
    Iterable<Author> findAll();
    void addAuthor(Author author);
    void removeAuthor(int authorId);
}
