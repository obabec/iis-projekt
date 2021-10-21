package isu.library.model.repository;


import isu.library.model.entity.Library;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("libraryRepository")
public interface LibraryRepository extends CrudRepository<Library, Integer> {
    void deleteLibraryByName(String name);
    void deleteLibraryById(Integer id);
}
