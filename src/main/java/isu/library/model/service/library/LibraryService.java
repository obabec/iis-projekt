/*
    IIS - projekt 2021
    Zadani: Knihovna
    Autori: Tomas Korbar <xkorba02>, Ondrej Babec <xbabec00>
 */
package isu.library.model.service.library;

import isu.library.model.entity.library.Library;

import java.sql.Time;

public interface LibraryService {
    Iterable<Library> findAll();

    void addNewLibrary(String name, String tag, String streed, String city, String streetNumber, Time fromTime, Time toTime, String description);

    Iterable<Library> executeQuery(String query);

    void updateLibrary(Library library);

    Library findLibraryById(Integer id);

    void deleteLibraryById(Integer id);
}
