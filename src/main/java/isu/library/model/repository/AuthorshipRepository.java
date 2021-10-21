package isu.library.model.repository;

import isu.library.model.entity.Authorship;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorshipRepository extends CrudRepository<Authorship, Integer> {

    @Query(value = "SELECT * FROM authorship WHERE book_id = :book_id", nativeQuery = true)
    Iterable<Authorship> findAuthorshipByBookId(@Param("book_id") Integer bookId);

    @Modifying
    @Query(value = "DELETE FROM authorship WHERE book_id = :book_id", nativeQuery = true)
    void removeAuthorshipByBookId(@Param("book_id") Integer bookId);
}
