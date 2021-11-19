package isu.library.model.service;

import isu.library.model.entity.Author;
import isu.library.model.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    AuthorRepository repository;

    @Override
    public Optional<Author> findAuthorById(int id) {
        return repository.findAuthorById(id);
    }

    @Override
    public Iterable<Author> findAll() {
        return repository.findAll();
    }

    @Override
    public void addAuthor(Author author) {
        if (author.getId() == 0) {
            repository.save(new Author(author));
        } else {
            repository.save(author);
        }
    }

    @Override
    public void removeAuthor(int authorId) {
        repository.deleteById(authorId);
    }
}
