package isu.library.model.service;


import isu.library.model.entity.BookOrder;

import java.util.Optional;

public interface BookOrderService {
    public int addBookOrder(int library_id, int book_id, int count);
    public Iterable<BookOrder> findAll();

    public Iterable<BookOrder> findByLibraryId(int library_id);
    public Iterable<BookOrder> findByTitleId(int title_id);
    public Iterable<BookOrder> findByTitleIdAndLibraryId(int title_id, int library_id);
    public Optional<BookOrder> findById(int id);
    public void removeById(int id);
}
