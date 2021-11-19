package isu.library.model.service;


import isu.library.model.entity.Authorship;
import isu.library.model.repository.AuthorshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
public class AuthoshipServiceImpl implements AuthorshipService {

    @Autowired
    AuthorshipRepository repository;

    @Override
    public void addNewAuthorship(int author_id, int book_id) {
        repository.save(new Authorship(author_id, book_id));
    }

    @Override
    public Iterable<Authorship> findAuthorshipByBookId(int book_id) {
        return repository.findAuthorshipByBookId(book_id);
    }

    @Override
    public void removeAuthorshipsByBookId(int book_id) {
        repository.removeAuthorshipByBookId(book_id);
    }
}
