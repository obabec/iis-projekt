/*
    IIS - projekt 2021
    Zadani: Knihovna
    Autori: Tomas Korbar <xkorba02>, Ondrej Babec <xbabec00>
 */
package isu.library.model.repository;

import isu.library.model.entity.Author;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AuthorRepository extends CrudRepository<Author, Integer> {
    @Query(value = "SELECT * FROM author WHERE id = :id", nativeQuery = true)
    Optional<Author> findAuthorById(@Param("id") Integer authorId);

    @Query(value = "SELECT * FROM author", nativeQuery = true)
    Iterable<Author> findAll();
}
