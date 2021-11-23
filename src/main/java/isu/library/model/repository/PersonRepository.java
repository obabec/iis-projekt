/*
    IIS - projekt 2021
    Zadani: Knihovna
    Autori: Tomas Korbar <xkorba02>, Ondrej Babec <xbabec00>
 */
package isu.library.model.repository;

import isu.library.model.entity.Person;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("personRepository")
public interface PersonRepository extends CrudRepository<Person, Integer> {
    Optional<Person> findPersonByUsername(String username);

    Iterable<Person> findPersonByUsernameNotNull();

    void deleteById(Integer id);

    Optional<Person> findPersonById(Integer id);

    Iterable<Person> findPersonByRole(String role);
}
