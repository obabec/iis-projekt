package isu.library.controllers;

import isu.library.model.entity.Book;
import isu.library.model.service.BookService;
import isu.library.model.service.LibraryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BookController {
    @Autowired
    private BookService bookService;

    @Autowired
    private LibraryService libraryService;

    @PostMapping("/book")
    public String book_creation(@ModelAttribute(value="book") Book book, ModelMap modelMap) {
        modelMap.put("libraries", libraryService.findAll());
        bookService.addNewBook(1, book.getName(), book.getRelease(), book.getIsbn(), book.getPublisher(), book.getGenre(), book.getRate());
        return "book_creation";
    }

    @GetMapping("/book")
    public String book_creation(ModelMap modelMap) {
        modelMap.put("libraries", libraryService.findAll());
        return "book_creation";
    }

    @GetMapping("/book/{id}")
    public String book_creation(@PathVariable("id") int bookId, ModelMap modelMap) {
        Book found_book = bookService.findById(bookId);
        modelMap.put("book", found_book);
        modelMap.put("library_name", libraryService.)
        return "book_creation";
    }

}
