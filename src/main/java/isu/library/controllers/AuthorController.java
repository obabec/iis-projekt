package isu.library.controllers;

import isu.library.model.entity.Author;
import isu.library.model.entity.Person;
import isu.library.model.service.AuthorService;
import isu.library.model.service.user.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AuthorController {

    @Autowired
    private PersonService personService;

    @Autowired
    private AuthorService authorService;

    @GetMapping("/authors")
    public String listAuthors(Authentication authentication, ModelMap modelMap) {
        modelMap.put("authors", authorService.findAll());
        if (authentication != null && ((UserDetails)authentication.getPrincipal()).getAuthorities().contains(new SimpleGrantedAuthority("ROLE_LIBRARIAN"))) {
            String username = ((UserDetails)authentication.getPrincipal()).getUsername();
            Person user = personService.findPersonByUsername(username).get();
            modelMap.put("librarian_lib", user.getLibraryId());
        }
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
    public String updateAuthor(@ModelAttribute(value = "author") Author author, ModelMap modelMap) {
        authorService.addAuthor(author);
        return "redirect:/authors";
    }

    @GetMapping("/authorDelete")
    public String removeAuthor(@RequestParam(name = "author_id", required = false, defaultValue = "") Integer authorId) {
        authorService.removeAuthor(authorId);
        return "redirect:/authors";
    }
}
