package isu.library.controllers;

import isu.library.model.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
    @Autowired
    private BookService bookService;

    @GetMapping("/")
    public String home(@RequestParam(name="book_name", required = false, defaultValue = "") String bookName,
                       @RequestParam(name="library_name", required = false, defaultValue = "") String libraryName,
                       @RequestParam(name="reserved", required = false, defaultValue = "false") String reserved,
                       @RequestParam(name="book_genre", required = false, defaultValue = "") String bookGenre,
                       @RequestParam(name="author_name", required = false, defaultValue = "") String authorName,
                       @RequestParam(name="filter_option", required = false, defaultValue = "") String filterOption,
                       ModelMap modelMap) {
        if (reserved.isEmpty()) {
            switch (filterOption) {
                case "bookName":
                    modelMap.put("books", bookService.findAvailableByName(bookName, libraryName));
                    break;
                case "bookGenre":
                    modelMap.put("books", bookService.findAvailableByGenre(bookGenre, libraryName));
                    break;
                case "authorName":
                    modelMap.put("books", bookService.findAvailableByAuthorName(authorName, libraryName));
                    break;
                default:
                    modelMap.put("books", bookService.findAvailableBooks(libraryName));
            }
        } else {
            switch (filterOption) {
                case "bookName":
                    modelMap.put("books", bookService.findByName(bookName, libraryName));
                    break;
                case "bookGenre":
                    modelMap.put("books", bookService.findByGenre(bookGenre, libraryName));
                    break;
                case "authorName":
                    modelMap.put("books", bookService.findByAuthorName(authorName, libraryName));
                    break;
                default:
                    modelMap.put("books", bookService.findAllInLibrary(libraryName));
            }
        }
        return "home";
    }
}
