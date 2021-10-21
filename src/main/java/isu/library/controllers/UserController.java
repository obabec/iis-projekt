package isu.library.controllers;

import isu.library.model.entity.Library;
import isu.library.model.entity.Person;
import isu.library.model.service.LibraryService;
import isu.library.model.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private PersonService personService;

    @Autowired
    private LibraryService libraryService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/users")
    public String userManagement(ModelMap modelMap) {
        modelMap.put("users", personService.findPersonByUsernameNotNull());
        return "users";
    }

    @GetMapping("/userDelete")
    public String deleteUser(@RequestParam(name="user_id", required = true, defaultValue = "") Integer userId,
            ModelMap modelMap) {
        personService.deleteById(userId);
        modelMap.put("users", personService.findPersonByUsernameNotNull());
        return "users";
    }

    @GetMapping("/userUpdate")
    public String update(@RequestParam(name="user_id", required = true, defaultValue = "") Integer userId,
                             ModelMap modelMap) {
        Optional<Person> person = personService.findPersonById(userId);
        if (person.isEmpty()) {
            modelMap.put("users", personService.findPersonByUsernameNotNull());
            return "users";
        }
        List<String> roles = Arrays.asList("ADMIN", "LIBRARIAN", "READER");
        modelMap.put("libraries", libraryService.findAll());
        modelMap.put("user", person.get());
        modelMap.put("roles", roles);
        return "userUpdate";
    }

    @PostMapping("/userUpdate")
    public String update(@ModelAttribute(value="user") Person person,
                         ModelMap modelMap) {
        if (person.getPassword().isEmpty()) {
            person.setPassword(personService.findPersonById(person.getId()).get().getPassword());
        } else {
            person.setPassword(bCryptPasswordEncoder.encode(person.getPassword()));
        }
        personService.updatePerson(person);
        modelMap.put("users", personService.findPersonByUsernameNotNull());
        return "users";
    }
}
