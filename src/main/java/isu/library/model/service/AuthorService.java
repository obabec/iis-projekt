package isu.library.model.service;

import isu.library.model.entity.Author;

import java.util.Optional;

public interface AuthorService {
    Optional<Author> findAuthorById(int integer);
    Iterable<Author> findAll();
}
