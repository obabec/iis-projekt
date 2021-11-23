/*
    IIS - projekt 2021
    Zadani: Knihovna
    Autori: Tomas Korbar <xkorba02>, Ondrej Babec <xbabec00>
 */
package isu.library.model.service;

import isu.library.model.entity.Authorship;

public interface AuthorshipService {
    void addNewAuthorship(int author_id, int book_id);

    void removeAuthorshipsByBookId(int book_id);

    Iterable<Authorship> findAuthorshipByBookId(int book_id);
}
