/*
    IIS - projekt 2021
    Zadani: Knihovna
    Autori: Tomas Korbar <xkorba02>, Ondrej Babec <xbabec00>
 */
package isu.library.controllers;

import isu.library.model.entity.Author;
import isu.library.model.entity.Authorship;
import isu.library.model.entity.Book;
import isu.library.model.entity.Person;
import isu.library.model.entity.library.Library;
import isu.library.model.query.BookQueryBuilder;
import isu.library.model.service.AuthorService;
import isu.library.model.service.AuthorshipService;
import isu.library.model.service.BookService;
import isu.library.model.service.library.LibraryService;
import isu.library.model.service.user.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/*
 * Controller for home endpoint listing all books.
 */
@Controller
public class HomeController {
    @Autowired
    private BookService bookService;

    @Autowired
    private PersonService personService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private AuthorshipService authorshipService;

    @Autowired
    private LibraryService libraryService;

    @GetMapping("/")
    public String home(@RequestParam(name = "book_name", required = false, defaultValue = "") String bookName,
                       @RequestParam(name = "library_name", required = false, defaultValue = "") String libraryName,
                       @RequestParam(name = "available", required = false, defaultValue = "off") String available,
                       @RequestParam(name = "book_genre", required = false, defaultValue = "") String bookGenre,
                       @RequestParam(name = "author_name", required = false, defaultValue = "") String authorName,
                       @RequestParam(name = "before", required = false, defaultValue = "off") String before,
                       @RequestParam(name = "release_date", required = false, defaultValue = "") String releaseDate,
                       @RequestParam(name = "isbn", required = false, defaultValue = "") String isbn,
                       @RequestParam(name = "publisher", required = false, defaultValue = "") String publisher,
                       @RequestParam(name = "message", required = false, defaultValue = "") String message,
                       @RequestParam(name = "rate", required = false, defaultValue = "-1") Integer rating,
                       Authentication authentication,
                       ModelMap modelMap) {

        if (authentication != null && ((UserDetails) authentication.getPrincipal()).getAuthorities().contains(new SimpleGrantedAuthority("ROLE_LIBRARIAN"))) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            Person user = personService.findPersonByUsername(username).get();
            modelMap.put("librarian_lib", user.getLibraryId());
        }
        BookQueryBuilder builder = new BookQueryBuilder();
        if (!authorName.isEmpty()) {
            builder = builder.filterByAuthor(authorName);
            modelMap.put("author_name", authorName);
        }

        if (rating != -1) {
            builder = builder.filterByRate(rating);
            modelMap.put("rate", rating);
        }
        if (!releaseDate.isEmpty()) {
            if (before.equals("on")) {
                modelMap.put("before", before);
                builder = builder.filterByReleaseUnder(Integer.parseInt(releaseDate));
            } else {
                builder = builder.filterByReleaseAbove(Integer.parseInt(releaseDate));
            }
            modelMap.put("release_date", releaseDate);
        }
        if (!isbn.isEmpty()) {
            builder = builder.filterByIsbn(isbn);
            modelMap.put("isbn", isbn);
        }
        if (!publisher.isEmpty()) {
            builder = builder.filterByPublisher(publisher);
            modelMap.put("publisher", publisher);
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
        builder.filterBooks();
        modelMap.put("message", message);
        TitleController.filterBooks(modelMap, builder, bookService, authorshipService, authorService);
        Iterable<Book> books = (Iterable<Book>) modelMap.get("books");

        for (Book b : books) {
            b.setLibraryName(libraryService.findLibraryById(b.getLibraryId()).getName());
        }
        modelMap.put("books", books);
        return "home";
    }

    @ExceptionHandler(Exception.class)
    public String handleError(HttpServletRequest req, Exception ex) {
        return "error";
    }
}
