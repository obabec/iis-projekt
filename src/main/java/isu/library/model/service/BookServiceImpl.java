package isu.library.model.service;

import isu.library.model.entity.Book;
import isu.library.model.query.BookQueryBuilder;
import isu.library.model.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.Date;
import java.util.List;


@Transactional
@Service("bookService")
public class BookServiceImpl implements BookService {

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Iterable<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Iterable<Book> findByName(String name, String libraryName) {
        String query = (new BookQueryBuilder()).filterByName(name).filterByLibrary(libraryName).getQuery();
        return ((List<Book>) entityManager.createNativeQuery(query, Book.class).getResultList());
    }

    @Override
    public Iterable<Book> findAvailableByName(String name, String libraryName) {
        String query = (new BookQueryBuilder()).filterByName(name).filterByLibrary(libraryName).filterByAvailability().getQuery();
        return ((List<Book>) entityManager.createNativeQuery(query, Book.class).getResultList());
    }

    @Override
    public Iterable<Book> findByAuthorName(String authorName, String libraryName) {
        String query = (new BookQueryBuilder()).filterByAuthor(authorName).filterByLibrary(libraryName).getQuery();
        return ((List<Book>) entityManager.createNativeQuery(query, Book.class).getResultList());
    }

    @Override
    public Iterable<Book> findAvailableBooks(String libraryName) {
        String query = (new BookQueryBuilder()).filterByAvailability().filterByLibrary(libraryName).getQuery();
        return ((List<Book>) entityManager.createNativeQuery(query, Book.class).getResultList());
    }

    @Override
    public Iterable<Book> findAvailableBooks(Integer libraryId) {
        String query = (new BookQueryBuilder()).filterByAvailability().filterByLibraryId(libraryId).getQuery();
        return ((List<Book>) entityManager.createNativeQuery(query, Book.class).getResultList());
    }

    @Override
    public boolean isBookAvailable(Integer bookId) {
        String query = (new BookQueryBuilder()).filterByAvailability().filterById(bookId).getQuery();
        return !((List<Book>) entityManager.createNativeQuery(query, Book.class).getResultList()).isEmpty();
    }

    @Override
    public Iterable<Book> findByGenre(String genre, String libraryName) {
        String query = (new BookQueryBuilder()).filterByGenre(genre).filterByLibrary(libraryName).getQuery();
        return ((List<Book>) entityManager.createNativeQuery(query, Book.class).getResultList());
    }

    @Override
    public Iterable<Book> findAvailableByGenre(String genre, String libraryName) {
        String query = (new BookQueryBuilder()).filterByGenre(genre).filterByLibrary(libraryName).filterByAvailability().getQuery();
        return ((List<Book>) entityManager.createNativeQuery(query, Book.class).getResultList());
    }

    @Override
    public Iterable<Book> findAllInLibrary(String libraryName) {
        String query = (new BookQueryBuilder()).filterByLibrary(libraryName).getQuery();
        return ((List<Book>) entityManager.createNativeQuery(query, Book.class).getResultList());
    }

    @Override
    public Iterable<Book> findAvailableByAuthorName(String authorName, String libraryName) {
        String query = (new BookQueryBuilder()).filterByAuthor(authorName).filterByLibrary(libraryName).filterByAvailability().getQuery();
        return ((List<Book>) entityManager.createNativeQuery(query, Book.class).getResultList());
    }

    @Override
    public Iterable<String> findAllGenres() {
        return bookRepository.findAllGenres();
    }

    @Override
    public Book findById(int id) {
        return bookRepository.findById(id);
    }

    @Override
    public Iterable<Book> executeQuery(String query) {
        return ((List<Book>) entityManager.createNativeQuery(query, Book.class).getResultList());
    }

    @Override
    public int addNewBook(Integer libraryId, String name, Date release, String isbn, String publisher, String genre, Short rate) {
        Book b = new Book(libraryId, name, release, isbn, publisher, genre, rate);
        bookRepository.save(b);
        return b.getId();
    }

    @Override
    public int addNewBook(String name, Date release, String isbn, String publisher, String genre, Short rate) {
        Book b = new Book(name,release,isbn,publisher,genre,rate);
        bookRepository.save(b);
        return b.getId();
    }

    @Override
    public void updateBook(Book book) {
        bookRepository.save(book);
    }

    @Override
    public void removeById(int id) {
        bookRepository.deleteById(id);
    }

    @Override
    public Iterable<String> findBooksByLibraryId(Integer libraryId) {
        return bookRepository.findBookNamesInLibrary(libraryId);
    }
}
