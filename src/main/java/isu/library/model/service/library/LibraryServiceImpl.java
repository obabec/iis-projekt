/*
    IIS - projekt 2021
    Zadani: Knihovna
    Autori: Tomas Korbar <xkorba02>, Ondrej Babec <xbabec00>
 */
package isu.library.model.service.library;

import isu.library.model.entity.library.Library;
import isu.library.model.repository.LibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Time;
import java.util.List;

@Transactional
@Service("libraryService")
public class LibraryServiceImpl implements LibraryService {
    @PersistenceContext
    EntityManager entityManager;
    @Autowired
    private LibraryRepository libraryRepository;

    @Override
    public Iterable<Library> findAll() {
        return libraryRepository.findAll();
    }

    @Override
    public void addNewLibrary(String name, String tag, String street, String city, String streetNumber, Time fromTime, Time toTime, String description) {
        Library l = new Library(name, tag, street, city, streetNumber, fromTime, toTime, description);
        libraryRepository.save(l);
    }

    @Override
    public void updateLibrary(Library library) {
        libraryRepository.save(library);
    }

    @Override
    public Library findLibraryById(Integer id) {
        Library lib = new Library();
        return libraryRepository.findById(id).orElse(lib);
    }

    @Override
    public void deleteLibraryById(Integer id) {
        libraryRepository.deleteLibraryById(id);
    }

    @Override
    public Iterable<Library> executeQuery(String query) {
        return ((List<Library>) entityManager.createNativeQuery(query, Library.class).getResultList());
    }

}
