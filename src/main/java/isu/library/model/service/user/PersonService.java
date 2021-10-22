package isu.library.model.service.user;

import isu.library.model.entity.Person;

import java.util.Optional;

public interface PersonService {
    Optional<Person> findPersonByUsername(String username);
    Iterable<Person> findPersonByUsernameNotNull();
    void deleteById(Integer id);
    Optional<Person> findPersonById(Integer id);
    void updatePerson(Person person);
    Optional<Person> findPersonById(int id);
    Iterable<Person> findAll();
    Iterable<Person> findPersonsByRole(String role);

}
