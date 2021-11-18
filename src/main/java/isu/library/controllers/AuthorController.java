package isu.library.controllers;

import isu.library.model.entity.Author;
import isu.library.model.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @GetMapping("/authors")
    public String listAuthors(ModelMap modelMap) {
        modelMap.put("authors", authorService.findAll());
        return "authors";
    }

    @GetMapping("/author")
    public String addAuthor(@RequestParam(name = "author_id", required = false, defaultValue = "") Integer authorId, ModelMap modelMap) {
        if (authorId == null) {
            modelMap.put("author", new Author());
        } else {
            modelMap.put("author", authorService.findAuthorById(authorId).get());
        }
        return "author";
    }

    @PostMapping("/author")
    public String updateAuthor(@ModelAttribute(value="author") Author author,  ModelMap modelMap) {
        authorService.addAuthor(author);
        return "redirect:/authors";
    }

    @GetMapping("/authorDelete")
    public String removeAuthor(@RequestParam(name = "author_id", required = false, defaultValue = "") Integer authorId) {
        authorService.removeAuthor(authorId);
        return "redirect:/authors";
    }
}
