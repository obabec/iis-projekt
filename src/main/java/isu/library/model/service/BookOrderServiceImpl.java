package isu.library.model.service;

import isu.library.model.entity.BookOrder;
import isu.library.model.repository.BookOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Transactional
@Service
public class BookOrderServiceImpl implements BookOrderService {

    @Autowired
    BookOrderRepository repository;

    @Override
    public int addBookOrder(int library_id, int book_id, int count) {
        BookOrder order = new BookOrder(library_id, book_id, count);
        repository.save(order);
        return order.getId();
    }

    @Override
    public Iterable<BookOrder> findAll() {
        return repository.findAll();
    }

    @Override
    public Iterable<BookOrder> findByLibraryId(int library_id) {
        return repository.findByLibraryId(library_id);
    }

    @Override
    public Iterable<BookOrder> findByTitleId(int title_id) {
        return repository.findByTitleId(title_id);
    }

    @Override
    public Optional<BookOrder> findById(int id) {
        return repository.findById(id);
    }

    @Override
    public void removeById(int id) {
        repository.removeById(id);
    }
}
