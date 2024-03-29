/*
    IIS - projekt 2021
    Zadani: Knihovna
    Autori: Tomas Korbar <xkorba02>, Ondrej Babec <xbabec00>
 */
package isu.library.controllers;

import isu.library.model.entity.Author;
import isu.library.model.entity.Authorship;
import isu.library.model.entity.Book;
import isu.library.model.entity.library.Library;
import isu.library.model.query.BookQueryBuilder;
import isu.library.model.service.AuthorService;
import isu.library.model.service.AuthorshipService;
import isu.library.model.service.BookService;
import isu.library.model.service.library.LibraryService;
import isu.library.model.service.user.PersonService;
import isu.library.model.service.vote.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

/*
 * Controller for endpoints managing book titles.
 */
@Controller
public class TitleController {

    @Autowired
    BookService bookService;

    @Autowired
    AuthorshipService authorshipService;

    @Autowired
    AuthorService authorService;

    @Autowired
    PersonService personService;

    @Autowired
    LibraryService libraryService;

    @Autowired
    VoteService voteService;

    @GetMapping("/titles")
    public String getTitles(@RequestParam(name = "book_name", required = false, defaultValue = "") String bookName,
                            @RequestParam(name = "book_genre", required = false, defaultValue = "") String bookGenre,
                            @RequestParam(name = "author_name", required = false, defaultValue = "") String authorName,
                            @RequestParam(name = "before", required = false, defaultValue = "off") String before,
                            @RequestParam(name = "release_date", required = false, defaultValue = "") String releaseDate,
                            @RequestParam(name = "isbn", required = false, defaultValue = "") String isbn,
                            @RequestParam(name = "publisher", required = false, defaultValue = "") String publisher,
                            @RequestParam(name = "rate", required = false, defaultValue = "") Integer rate,
                            Authentication authentication,
                            ModelMap modelMap) {
        BookQueryBuilder builder = new BookQueryBuilder();
        if (authentication != null && authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_LIBRARIAN"))) {
            modelMap.put("librarians_library", personService.findPersonByUsername(authentication.getName()).get().getLibraryId());
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
        if (rate != null) {
            builder = builder.filterByRate(rate);
            modelMap.put("rate", rate);
        }
        if (!authorName.isEmpty()) {
            builder = builder.filterByAuthor(authorName);
            modelMap.put("author_name", authorName);
        }
        if (!bookName.isEmpty()) {
            builder = builder.filterByName(bookName);
            modelMap.put("book_name", bookName);
        }
        if (!bookGenre.isEmpty()) {
            builder = builder.filterByGenre(bookGenre);
            modelMap.put("book_genre", bookGenre);
        }
        builder = builder.filterTitles();
        filterBooks(modelMap, builder, bookService, authorshipService, authorService);
        return "titles";
    }

    static void filterBooks(ModelMap modelMap, BookQueryBuilder builder, BookService bookService, AuthorshipService authorshipService, AuthorService authorService) {
        Iterable<Book> books = bookService.executeQuery(builder.getQuery());
        for (Book b : books) {
            b.setAuthors(new ArrayList<Integer>());
            b.setAuthors_names(new ArrayList<String>());
            for (Authorship authorship : authorshipService.findAuthorshipByBookId(b.getId())) {
                Author author = authorService.findAuthorById(authorship.getAuthorId()).get();
                b.getAuthors().add(author.getId());
                b.getAuthors_names().add(author.getName() + " " + author.getSurname());
            }
        }
        modelMap.put("books", books);
    }

    @GetMapping("/title")
    public String titleCreation(ModelMap modelMap) {
        Book book = new Book();
        modelMap.put("book", book);
        modelMap.put("possible_authors", authorService.findAll());
        modelMap.put("chosen_authors", new ArrayList<String>());
        return "title";
    }

    @PostMapping("/title")
    public String titleCreation(@ModelAttribute(value = "book") Book book, ModelMap modelMap) {
        int id = bookService.addNewBook(book.getName(), book.getRelease(), book.getIsbn(), book.getPublisher(), book.getGenre(), book.getRate());
        book.setId(id);
        for (Library lib : libraryService.findAll()) {
            voteService.saveNewVote(book, lib);
        }
        for (Integer author_id : book.getAuthors()) {
            authorshipService.addNewAuthorship(author_id, id);
        }
        return "redirect:/title";
    }

    @GetMapping("/title/{id}")
    public String titleInformation(@PathVariable("id") int bookId, ModelMap modelMap) {
        Book found_book = bookService.findById(bookId);
        if (found_book.getLibraryId() != null) {
            return "redirect:/error";
        }
        found_book.setAuthors(new ArrayList<>());
        authorshipService.findAuthorshipByBookId(bookId).forEach(a -> found_book.getAuthors().add(authorService.findAuthorById(a.getAuthorId()).get().getId()));
        modelMap.put("book", found_book);
        modelMap.put("possible_authors", authorService.findAll());
        ArrayList<String> chosen_authors = new ArrayList<>();
        for (Integer id : found_book.getAuthors()) {
            chosen_authors.add(String.valueOf(authorService.findAuthorById(id).get().getId()));
        }

        modelMap.put("chosen_authors", chosen_authors);
        return "title";
    }

    @GetMapping("/title/{id}/delete")
    public String titleDelete(@PathVariable("id") int bookId, ModelMap modelMap) {
        bookService.removeById(bookId);
        return "redirect:/titles";
    }

    @PostMapping("/title/{id}")
    public String titleEdit(@ModelAttribute(value = "book") Book book, @PathVariable("id") int bookId, ModelMap modelMap) {
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
        return "redirect:/title/" + bookId;
    }

}
