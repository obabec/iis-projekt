package isu.library.model.service;

import isu.library.model.entity.Library;
import isu.library.model.repository.LibraryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("libraryService")
public class LibraryServiceImpl implements LibraryService{
    @Autowired
    private LibraryRepository libraryRepository;

    @Override
    public Iterable<Library> findAll() {
        return libraryRepository.findAll();
    }
}
