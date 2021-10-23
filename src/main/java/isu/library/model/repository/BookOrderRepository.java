package isu.library.model.repository;

import isu.library.model.entity.BookOrder;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BookOrderRepository extends CrudRepository<BookOrder, Integer> {

    @Query(value="SELECT * FROM book_order b WHERE b.library_id = :library_id", nativeQuery = true)
    Iterable<BookOrder> findByLibraryId(@Param("library_id") Integer library_id);

    @Query(value="SELECT * FROM book_order b WHERE b.title_id = :title_id", nativeQuery = true)
    Iterable<BookOrder> findByTitleId(@Param("title_id") Integer title_id);

    @Query(value="SELECT * FROM book_order b WHERE b.title_id = :title_id and b.library_id = :library_id", nativeQuery = true)
    Iterable<BookOrder> findByTitleIdAndLibraryId(@Param("title_id") Integer title_id, @Param("library_id") Integer library_id);

    @Modifying
    @Query(value="DELETE FROM book_order b WHERE b.id = :id", nativeQuery = true)
    void removeById(@Param("id") int id);
}
