package isu.library.model.service;

import isu.library.model.entity.Library;

import java.sql.Date;
import java.sql.Time;

public interface LibraryService {
    Iterable<Library> findAll();
    void addNewLibrary(String name, String tag, String streed, String city, String streetNumber, Time fromTime, Time toTime, String description);
    void deleteLibraryByName(String name);
    Iterable<Library> executeQuery(String query);
    void updateLibrary(Library library);
    Library findLibraryById(Integer id);
}
