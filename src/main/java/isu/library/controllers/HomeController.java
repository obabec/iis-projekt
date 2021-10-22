package isu.library.controllers;

import isu.library.model.entity.Author;
import isu.library.model.entity.Authorship;
import isu.library.model.entity.Book;
import isu.library.model.entity.Person;
import isu.library.model.query.BookQueryBuilder;
import isu.library.model.service.AuthorService;
import isu.library.model.service.AuthorshipService;
import isu.library.model.service.BookService;
import isu.library.model.service.user.PersonService;
import isu.library.model.service.vote.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

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
    private VoteService voteService;

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
                       @RequestParam(name="message", required = false, defaultValue = "") String message,
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
        modelMap.put("message", message);
        Iterable<Book> books = bookService.executeQuery(builder.getQuery());
        for (Book b: books) {
            b.setAuthors(new ArrayList<Integer>());
            b.setAuthors_names(new ArrayList<String>());
            for (Authorship authorship: authorshipService.findAuthorshipByBookId(b.getId())) {
                Author author = authorService.findAuthorById(authorship.getAuthorId()).get();
                b.getAuthors().add(author.getId());
                b.getAuthors_names().add(author.getName() + " " + author.getSurname());
            }
        }

        modelMap.put("books", books);
        return "home";
    }
}
