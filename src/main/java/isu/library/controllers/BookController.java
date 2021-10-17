package isu.library.controllers;

import isu.library.model.entity.Book;
import isu.library.model.entity.Person;
import isu.library.model.service.BookService;
import isu.library.model.service.LibraryService;
import isu.library.model.service.PersonService;
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

import java.util.Collections;

@Controller
public class BookController {
    @Autowired
    private BookService bookService;

    @Autowired
    private LibraryService libraryService;

    @Autowired
    private PersonService personService;

    @PostMapping("/book")
    public String book_creation(@ModelAttribute(value="book") Book book, ModelMap modelMap) {
        modelMap.put("libraries", libraryService.findAll());
        modelMap.put("book", book);
        modelMap.put("library_id", book.getLibraryId());
        int id = bookService.addNewBook(book.getLibraryId(), book.getName(), book.getRelease(), book.getIsbn(), book.getPublisher(), book.getGenre(), book.getRate());
        return "redirect:/book/" + id;
    }

    @PostMapping("/book/{id}")
    public String book_update(@ModelAttribute(value="book") Book book, @PathVariable("id") int bookId, ModelMap modelMap) {
        modelMap.put("libraries", libraryService.findAll());
        modelMap.put("library_id", book.getLibraryId()-1);
        book.setId(bookId);
        modelMap.put("book", book);
        bookService.updateBook(book);
        return "book_creation";
    }

    @GetMapping("/book")
    public String book_creation(@RequestParam(name="library_id", required = false, defaultValue = "") Integer libraryId,
                                Authentication authentication,
                                ModelMap modelMap) {
        Book book = new Book();
        if (authentication != null && ((UserDetails)authentication.getPrincipal()).getAuthorities().contains(new SimpleGrantedAuthority("ROLE_LIBRARIAN"))) {
            String username = ((UserDetails)authentication.getPrincipal()).getUsername();
            Person user = personService.findPersonByUsername(username).get();
            libraryId = user.getLibraryId();
        }
        if (libraryId != null) {
            modelMap.put("library_id", libraryId - 1);
            modelMap.put("libraries", Collections.singletonList(libraryService.findLibraryById(libraryId)));
        } else {
            modelMap.put("library_id", 0);
            modelMap.put("libraries", libraryService.findAll());
        }
        modelMap.put("book", book);
        return "book_creation";
    }

    @GetMapping("/book/{id}")
    public String book_creation(@PathVariable("id") int bookId, ModelMap modelMap) {
        Book found_book = bookService.findById(bookId);
        modelMap.put("book", found_book);
        modelMap.put("library_id", found_book.getLibraryId()-1);
        modelMap.put("libraries", libraryService.findAll());
        return "book_creation";
    }

}
