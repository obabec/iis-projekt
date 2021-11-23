/*
    IIS - projekt 2021
    Zadani: Knihovna
    Autori: Tomas Korbar <xkorba02>, Ondrej Babec <xbabec00>
 */
package isu.library.model.service;

import isu.library.model.entity.Author;

import java.util.Optional;

public interface AuthorService {

    Optional<Author> findAuthorById(int integer);

    Iterable<Author> findAll();

    void addAuthor(Author author);

    void removeAuthor(int authorId);
}
