package isu.library.model.service;

import isu.library.model.entity.Book;
import isu.library.model.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service("bookService")
public class BookServiceImpl implements BookService{

    @Autowired
    private BookRepository bookRepository;

    @Override
    public Iterable<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Iterable<Book> findByName(String name, String libraryName) {
        return bookRepository.findBookByName(name, libraryName);
    }

    @Override
    public Iterable<Book> findAvailableByName(String name, String libraryName) {
        return bookRepository.findAvailableBookByName(name, libraryName);
    }

    @Override
    public Iterable<Book> findByAuthorName(String authorName, String libraryName) {
        return bookRepository.findByAuthorName(authorName, libraryName);
    }

    @Override
    public Iterable<Book> findAvailableBooks(String libraryName) {
        return bookRepository.findAvailableBooks(libraryName);
    }

    @Override
    public Iterable<Book> findByGenre(String genre, String libraryName) {
        return bookRepository.findByGenre(genre, libraryName);
    }

    @Override
    public Iterable<Book> findAvailableByGenre(String genre, String libraryName) {
       return  bookRepository.findAvailableByGenre(genre, libraryName);
    }

    @Override
    public Iterable<Book> findAllInLibrary(String libraryName) {
        return bookRepository.findAllInLibrary(libraryName);
    }

    @Override
    public Iterable<Book> findAvailableByAuthorName(String authorName, String libraryName) {
        return bookRepository.findAvailableByAuthorName(authorName, libraryName);
    }
}
