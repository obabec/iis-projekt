/*
    IIS - projekt 2021
    Zadani: Knihovna
    Autori: Tomas Korbar <xkorba02>, Ondrej Babec <xbabec00>
 */
package isu.library.model.repository;


import isu.library.model.entity.library.Library;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("libraryRepository")
public interface LibraryRepository extends CrudRepository<Library, Integer> {
    void deleteLibraryById(Integer id);
}
