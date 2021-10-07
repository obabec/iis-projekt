package isu.library.model.repository;

import isu.library.model.entity.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("bookRepository")
public interface BookRepository extends CrudRepository<Book, Integer> {

    /*@Query(value = "SELECT * FROM book b WHERE b.name = ?1")
    Iterable<Book> findBookByName();*/
    @Query(value = "SELECT * FROM book b WHERE b.name LIKE CONCAT('%', :book_name, '%') ", nativeQuery = true)
    Iterable<Book> findBookByName(@Param("book_name") String book_name);
}
