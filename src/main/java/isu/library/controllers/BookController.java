package isu.library.controllers;

import isu.library.model.entity.Book;
import isu.library.model.entity.Person;
import isu.library.model.entity.library.Library;
import isu.library.model.service.AuthorService;
import isu.library.model.service.AuthorshipService;
import isu.library.model.service.BookService;
import isu.library.model.service.library.LibraryService;
import isu.library.model.service.reservation.ReservationService;
import isu.library.model.service.user.PersonService;
import isu.library.model.service.vote.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Collections;

@Controller
public class BookController {
    @Autowired
    private BookService bookService;

    @Autowired
    private LibraryService libraryService;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private PersonService personService;

    @Autowired
    private AuthorshipService authorshipService;

    @Autowired
    private VoteService voteService;

    @Autowired
    private ReservationService reservationService;

    @PostMapping("/book")
    public String bookCreation(@ModelAttribute(value = "book") Book book, ModelMap modelMap) {
        int id = bookService.addNewBook(book.getLibraryId(), book.getName(), book.getRelease(), book.getIsbn(), book.getPublisher(), book.getGenre(), book.getRate());
        for (Integer author_id : book.getAuthors()) {
            authorshipService.addNewAuthorship(author_id, id);
        }
        return "redirect:/book/" + id;
    }

    @PostMapping("/book/{id}")
    public String bookUpdate(@ModelAttribute(value = "book") Book book, @PathVariable("id") int bookId, ModelMap modelMap) {
        modelMap.put("libraries", libraryService.findAll());
        modelMap.put("chosen_library", libraryService.findLibraryById(book.getLibraryId()));
        modelMap.put("possible_authors", authorService.findAll());
        ArrayList<String> chosen_authors = new ArrayList<>();
        for (Integer id : book.getAuthors()) {
            chosen_authors.add(String.valueOf(authorService.findAuthorById(id).get().getId()));
        }
        modelMap.put("chosen_authors", chosen_authors);
        book.setId(bookId);
        authorshipService.removeAuthorshipsByBookId(bookId);
        for (Integer author_id : book.getAuthors()) {
            authorshipService.addNewAuthorship(author_id, bookId);
        }
        modelMap.put("book", book);
        bookService.updateBook(book);
        return "book_creation";
    }

    @GetMapping("/book/{id}/delete")
    public String bookDelete(@PathVariable("id") int bookId, ModelMap modelMap, Authentication authentication) {
        if (authentication != null) {
            if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_LIBRARIAN"))) {
                if (personService.findPersonByUsername(authentication.getName()).get().getLibraryId() != bookService.findById(bookId).getLibraryId()) {
                    return "redirect:/forbidden";
                }
            } else if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
                return "redirect:/forbidden";
            }
        }
        authorshipService.removeAuthorshipsByBookId(bookId);
        reservationService.deleteByBookId(bookId);
        bookService.removeById(bookId);
        return "redirect:/";
    }

    @GetMapping("/book")
    public String bookCreation(@RequestParam(name = "library_id", required = false, defaultValue = "") Integer realLibraryId,
                               Authentication authentication,
                               ModelMap modelMap) {
        Book book = new Book();
        if (authentication != null && ((UserDetails) authentication.getPrincipal()).getAuthorities().contains(new SimpleGrantedAuthority("ROLE_LIBRARIAN"))) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            Person user = personService.findPersonByUsername(username).get();
            realLibraryId = user.getLibraryId();
        }
        if (realLibraryId != null) {
            modelMap.put("chosen_library", libraryService.findLibraryById(realLibraryId));
            modelMap.put("libraries", Collections.singletonList(libraryService.findLibraryById(realLibraryId)));
        } else {
            for (Library l : libraryService.findAll()) {
                modelMap.put("chosen_library", l);
                break;
            }
            modelMap.put("libraries", libraryService.findAll());
        }
        modelMap.put("possible_authors", authorService.findAll());
        modelMap.put("chosen_authors", new ArrayList<String>());
        modelMap.put("book", book);
        return "book_creation";
    }

    @GetMapping("/book/{id}")
    public String bookCreation(@PathVariable("id") int bookId, ModelMap modelMap) {
        Book found_book = bookService.findById(bookId);
        found_book.setAuthors(new ArrayList<>());
        authorshipService.findAuthorshipByBookId(bookId).forEach(a -> found_book.getAuthors().add(authorService.findAuthorById(a.getAuthorId()).get().getId()));
        modelMap.put("book", found_book);
        modelMap.put("chosen_library", libraryService.findLibraryById(found_book.getLibraryId()));
        modelMap.put("libraries", libraryService.findAll());
        modelMap.put("possible_authors", authorService.findAll());
        ArrayList<String> chosen_authors = new ArrayList<>();
        for (Integer id : found_book.getAuthors()) {
            chosen_authors.add(String.valueOf(authorService.findAuthorById(id).get().getId()));
        }

        modelMap.put("chosen_authors", chosen_authors);
        return "book_creation";
    }

}
