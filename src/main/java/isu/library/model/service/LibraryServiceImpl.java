package isu.library.model.service;

import isu.library.model.entity.Library;
import isu.library.model.repository.LibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;

@Transactional
@Service("libraryService")
public class LibraryServiceImpl implements LibraryService{
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
    public void deleteLibraryByName(String name) {
        libraryRepository.deleteLibraryByName(name);
    }

}
