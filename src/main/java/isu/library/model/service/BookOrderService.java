/*
    IIS - projekt 2021
    Zadani: Knihovna
    Autori: Tomas Korbar <xkorba02>, Ondrej Babec <xbabec00>
 */
package isu.library.model.service;


import isu.library.model.entity.BookOrder;

import java.util.Optional;

public interface BookOrderService {
    int addBookOrder(int library_id, int book_id, int count);

    Iterable<BookOrder> findAll();

    Iterable<BookOrder> findByLibraryId(int library_id);

    Iterable<BookOrder> findByTitleId(int title_id);

    Iterable<BookOrder> findByTitleIdAndLibraryId(int title_id, int library_id);

    Optional<BookOrder> findById(int id);

    void removeById(int id);
}
