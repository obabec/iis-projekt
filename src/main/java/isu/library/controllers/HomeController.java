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
                       @RequestParam(name="reserved", required = false, defaultValue = "false") String reserved,
                       @RequestParam(name="book_genre", required = false, defaultValue = "") String bookGenre,
                       @RequestParam(name="author_name", required = false, defaultValue = "") String authorName,
                       ModelMap modelMap) {
        BookQueryBuilder builder = new BookQueryBuilder();
        if (!authorName.isEmpty()) {
            builder = builder.filterByAuthor(authorName);
        }
        if (!bookName.isEmpty()) {
            builder = builder.filterByName(bookName);
        }
        if (!libraryName.isEmpty()) {
            builder = builder.filterByLibrary(libraryName);
        }
        if (reserved.equals("true")) {
            builder = builder.filterByAvailability();
        }
        if (!bookGenre.isEmpty()) {
            builder = builder.filterByGenre(bookGenre);
        }

        Iterable<Book> books = bookService.executeQuery(builder.getQuery());
        books = personService.findAuthorsForBooks(books);
        modelMap.put("books", books);
        return "home";
    }
}
