package isu.library.model.service;

import isu.library.model.entity.Book;
import isu.library.model.entity.Person;

import java.util.Optional;

public interface PersonService {
    Iterable<Book> findAuthorsForBooks(Iterable<Book> books);
    Optional<Person> findPersonByUsername(String username);
}
