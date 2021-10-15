package isu.library.controllers;

import isu.library.model.entity.Book;
import isu.library.model.query.BookQueryBuilder;
import isu.library.model.service.BookService;
import isu.library.model.service.LibraryService;
import isu.library.model.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private BookService bookService;

    @Autowired
    private LibraryService libraryService;

    @Autowired
    private PersonService personService;

    @GetMapping("/")
    public String home(@RequestParam(name="book_name", required = false, defaultValue = "") String bookName,
                       @RequestParam(name="library_name", required = false, defaultValue = "") String libraryName,
                       @RequestParam(name="available", required = false, defaultValue = "off") String available,
                       @RequestParam(name="book_genre", required = false, defaultValue = "") String bookGenre,
                       @RequestParam(name="author_name", required = false, defaultValue = "") String authorName,
                       @RequestParam(name="before", required = false, defaultValue = "off") String before,
                       @RequestParam(name="release_date", required = false, defaultValue = "") String releaseDate,
                       @RequestParam(name="isbn", required = false, defaultValue = "") String isbn,
                       @RequestParam(name="publisher", required = false, defaultValue = "") String publisher,
                       @RequestParam(name="genre", required = false, defaultValue = "") String genre,
                       ModelMap modelMap) {
        BookQueryBuilder builder = new BookQueryBuilder();
        if (!releaseDate.isEmpty()) {
            if (before.equals("on")) {
                modelMap.put("before", before);
                builder.filterByReleaseUnder(Integer.parseInt(releaseDate));
            } else {
                builder.filterByReleaseAbove(Integer.parseInt(releaseDate));
            }
            modelMap.put("release_date", releaseDate);
        }
        if (!isbn.isEmpty()) {
            builder.filterByIsbn(isbn);
            modelMap.put("isbn", isbn);
        }
        if (!publisher.isEmpty()) {
            builder.filterByPublisher(publisher);
            modelMap.put("publisher", publisher);
        }
        if (!genre.isEmpty()) {
            builder.filterByGenre(genre);
            modelMap.put("genre", genre);
        }
        if (!authorName.isEmpty()) {
            builder = builder.filterByAuthor(authorName);
            modelMap.put("author_name", authorName);
        }
        if (!bookName.isEmpty()) {
            builder = builder.filterByName(bookName);
            modelMap.put("book_name", bookName);
        }
        if (!libraryName.isEmpty()) {
            builder = builder.filterByLibrary(libraryName);
            modelMap.put("library_name", libraryName);
        }
        if (available.equals("on")) {
            builder = builder.filterByAvailability();
            modelMap.put("available", "on");
        }
        if (!bookGenre.isEmpty()) {
            builder = builder.filterByGenre(bookGenre);
            modelMap.put("book_genre", bookGenre);
        }
        Iterable<Book> books = bookService.executeQuery(builder.getQuery());
        books = personService.findAuthorsForBooks(books);
        modelMap.put("books", books);
        return "home";
    }
}
