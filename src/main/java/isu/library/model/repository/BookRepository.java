package isu.library.model.repository;

import isu.library.model.entity.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("bookRepository")
public interface BookRepository extends CrudRepository<Book, Integer> {


    @Query(value = "SELECT DISTINCT b.genre FROM Book b")
    Iterable<String> findAllGenres();

    // Hledani knihy dle jmena - working
    @Query(value = "SELECT * FROM book b WHERE b.name LIKE CONCAT('%', :bookName, '%') AND library_id IN (select id from library where name like CONCAT('%', :libraryName, '%'))", nativeQuery = true)
    Iterable<Book> findBookByName(@Param("bookName") String bookName, @Param("libraryName") String libraryName);

    @Query(value = "SELECT * FROM book b WHERE b.name LIKE CONCAT('%', :bookName, '%') AND " +
            "library_id IN (select id from library where name like CONCAT('%', :libraryName, '%'))" +
            "AND library_id IN (select id from library where name like CONCAT('%', :libraryName, '%'))", nativeQuery = true)
    Iterable<Book> findAvailableBookByName(@Param("bookName") String bookName, @Param("libraryName") String libraryName);
    // Hledani knihy dle autora - Working
    @Query(value = "SELECT b.id, b.name, b.release, b.isbn, b.publisher, b.genre, b.rate FROM book b " +
            "INNER JOIN authorship a ON b.id = a.book_id INNER JOIN author p ON a.author_id = p.id WHERE CONCAT(p.name, ' ', p.surname) " +
            "LIKE CONCAT('%', :authorName, '%') AND library_id IN " +
            "(select id from library where name like CONCAT('%', :libraryName, '%'))", nativeQuery = true)
    Iterable<Book> findByAuthorName(@Param("authorName") String authorName, @Param("libraryName") String libraryName);

    @Query(value = "SELECT b.id, b.name, b.release, b.isbn, b.publisher, b.genre, b.rate FROM book b " +
            "INNER JOIN authorship a ON b.id = a.book_id INNER JOIN author p ON a.author_id = p.id WHERE CONCAT(p.name, ' ', p.surname) " +
            "LIKE CONCAT('%', :authorName, '%') AND library_id IN " +
            "(select id from library where name like CONCAT('%', :libraryName, '%')) AND " +
            "b.id NOT IN (SELECT book_id FROM blocking WHERE CURRENT_DATE BETWEEN date_from AND date_to)", nativeQuery = true)
    Iterable<Book> findAvailableByAuthorName(@Param("authorName") String authorName, @Param("libraryName") String libraryName);

    //Nalezeni knih dle zanru
    @Query(value = "SELECT * FROM book b WHERE b.genre LIKE CONCAT('%', :genre, '%') AND library_id IN (select id from library where name like CONCAT('%', :libraryName, '%'))", nativeQuery = true)
    Iterable<Book> findByGenre(@Param("genre") String genre, @Param("libraryName") String libraryName);

    @Query(value = "SELECT * FROM book b WHERE b.genre LIKE CONCAT('%', :genre, '%') AND library_id IN " +
            "(select id from library where name like CONCAT('%', :libraryName, '%')) AND " +
            "b.id NOT IN (SELECT book_id FROM blocking WHERE CURRENT_DATE BETWEEN date_from AND date_to)", nativeQuery = true)
    Iterable<Book> findAvailableByGenre(@Param("genre") String genre, @Param("libraryName") String libraryName);

    //Nalezeni knih dle dostupnosti - Working
    @Query(value = "SELECT * FROM book b WHERE b.id NOT IN (SELECT book_id FROM blocking WHERE CURRENT_DATE BETWEEN date_from AND date_to)" +
            "AND library_id IN (select id from library where name like CONCAT('%', :libraryName, '%'))", nativeQuery = true)
    Iterable<Book> findAvailableBooks(@Param("libraryName") String libraryName);

    //Nalezeni vsech knih v dane knihovne - Working
    @Query(value = "SELECT * FROM book b WHERE library_id IN (select id from library where name like CONCAT('%', :libraryName, '%'))", nativeQuery = true)
    Iterable<Book> findAllInLibrary(@Param("libraryName") String libraryName);

    @Query(value="SELECT * FROM book b WHERE b.id = :id", nativeQuery = true)
    Book findById(@Param("id") int id);


    @Query(value = "SELECT DISTINCT b.name FROM book b WHERE b.library_id = :library_id", nativeQuery = true)
    Iterable<String> findBookNamesInLibrary(@Param("library_id") Integer libraryId);

}
