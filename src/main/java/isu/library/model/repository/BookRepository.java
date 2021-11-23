/*
    IIS - projekt 2021
    Zadani: Knihovna
    Autori: Tomas Korbar <xkorba02>, Ondrej Babec <xbabec00>
 */
package isu.library.model.repository;

import isu.library.model.entity.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("bookRepository")
public interface BookRepository extends CrudRepository<Book, Integer> {


    @Query(value = "SELECT * FROM book b WHERE b.library_id IS NULL", nativeQuery = true)
    Iterable<Book> findAllTitles();

    @Query(value = "SELECT * FROM book b WHERE b.id = :id", nativeQuery = true)
    Book findById(@Param("id") int id);


    @Query(value = "SELECT DISTINCT b.name FROM book b WHERE b.library_id = :library_id", nativeQuery = true)
    Iterable<String> findBookNamesInLibrary(@Param("library_id") Integer libraryId);
}
