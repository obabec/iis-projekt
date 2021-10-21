package isu.library.model.service;

import isu.library.model.entity.Authorship;

public interface AuthorshipService {
    void addNewAuthorship(int person_id, int book_id);
    void removeAuthorshipsByBookId(int book_id);
    Iterable<Authorship> findAuthorshipByBookId(int book_id);
}
