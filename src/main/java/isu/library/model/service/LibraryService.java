package isu.library.model.service;

import isu.library.model.entity.Library;

public interface LibraryService {
    Iterable<Library> findAll();
}
