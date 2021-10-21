package isu.library.model.service;

import isu.library.model.entity.Book;
import isu.library.model.entity.Person;

import java.util.Optional;

public interface PersonService {
    Optional<Person> findPersonByUsername(String username);
    Optional<Person> findPersonById(int id);
    Iterable<Person> findAll();
}
