package isu.library.model.repository;

import isu.library.model.entity.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("personRepository")
public interface PersonRepository extends CrudRepository<Person, Integer> {
    @Query(value = "SELECT p.surname FROM person p INNER JOIN authorship a ON a.person_id = p.id WHERE a.book_id = :bookId", nativeQuery = true)
    Iterable<String> findAuthorsForBook(@Param("bookId") Integer bookId);

    Optional<Person> findPersonByUsername(String username);

}
