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
    public String home(@RequestParam(name="book_name", required = false, defaultValue = "") String bookName, ModelMap modelMap) {
        if (bookName.isEmpty()) {
            modelMap.put("books", bookService.findAll());
        } else {
            modelMap.put("books", bookService.findByName(bookName));
        }

        return "home";
    }
}
