package isu.library.controllers;

import isu.library.model.entity.Book;
import isu.library.model.entity.Library;
import isu.library.model.entity.LibraryReservation;
import isu.library.model.entity.Person;
import isu.library.model.query.BookQueryBuilder;
import isu.library.model.query.LibraryQueryBuilder;
import isu.library.model.service.BookService;
import isu.library.model.service.LibraryService;
import isu.library.model.service.PersonService;
import isu.library.model.service.ReservationService;
import isu.library.model.service.AuthorshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private BookService bookService;

    @Autowired
    private PersonService personService;

    @Autowired
    private AuthorshipService authorshipService;

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
                       Authentication authentication,
                       ModelMap modelMap) {
        if (authentication != null && ((UserDetails)authentication.getPrincipal()).getAuthorities().contains(new SimpleGrantedAuthority("ROLE_LIBRARIAN"))) {
            String username = ((UserDetails)authentication.getPrincipal()).getUsername();
            Person user = personService.findPersonByUsername(username).get();
            modelMap.put("librarian_lib", user.getLibraryId());
        }
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
        for (Book b: books) {
            b.setAuthors(new ArrayList<Integer>());
            authorshipService.findAuthorshipByBookId(b.getId()).forEach(a -> b.getAuthors().add(personService.findPersonById(a.getPersonId()).get().getId()));
        }
        modelMap.put("books", books);
        return "home";
    }
}
