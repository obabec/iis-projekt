/*
    IIS - projekt 2021
    Zadani: Knihovna
    Autori: Tomas Korbar <xkorba02>, Ondrej Babec <xbabec00>
 */
package isu.library.controllers;

import isu.library.model.entity.Person;
import isu.library.model.service.library.LibraryService;
import isu.library.model.service.user.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/*
 * Controller for endpoints managing user accounts.
 */
@Controller
public class UserController {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private PersonService personService;
    @Autowired
    private LibraryService libraryService;

    @GetMapping("/users")
    public String userManagement(Authentication authentication, ModelMap modelMap) {
        modelMap.put("users", personService.findPersonByUsernameNotNull());
        modelMap.put("my_id", personService.findPersonByUsername(authentication.getName()).get().getId());
        if (authentication != null && ((UserDetails) authentication.getPrincipal()).getAuthorities().contains(new SimpleGrantedAuthority("ROLE_LIBRARIAN"))) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            Person user = personService.findPersonByUsername(username).get();
            modelMap.put("librarian_lib", user.getLibraryId());
        }
        return "users";
    }

    @GetMapping("/userDelete")
    public String deleteUser(@RequestParam(name = "user_id", required = true, defaultValue = "") Integer userId,
                             Authentication authentication,
                             ModelMap modelMap) {
        if (userId != personService.findPersonByUsername(authentication.getName()).get().getId()) {
            personService.deleteById(userId);
        }
        return "redirect:/users";
    }

    @GetMapping("/userUpdate")
    public String update(@RequestParam(name = "user_id", required = true, defaultValue = "") Integer userId,
                         Authentication authentication,
                         ModelMap modelMap) {
        if (userId == null) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            Person user = personService.findPersonByUsername(username).get();
            userId = user.getId();
        }
        if (authentication != null && ((UserDetails) authentication.getPrincipal()).getAuthorities().contains(new SimpleGrantedAuthority("ROLE_LIBRARIAN"))) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            Person user = personService.findPersonByUsername(username).get();
            modelMap.put("librarian_lib", user.getLibraryId());
        }
        Optional<Person> person = personService.findPersonById(userId);
        if (person.isEmpty()) {
            modelMap.put("users", personService.findPersonByUsernameNotNull());
            return "users";
        }
        List<String> roles = Arrays.asList("ADMIN", "LIBRARIAN", "READER", "DISTRIBUTOR");
        modelMap.put("libraries", libraryService.findAll());
        modelMap.put("user", person.get());
        modelMap.put("roles", roles);
        return "userUpdate";
    }

    @PostMapping("/userUpdate")
    public String update(@ModelAttribute(value = "user") Person person,
                         Authentication authentication,
                         ModelMap modelMap) {
        modelMap.put("my_id", personService.findPersonByUsername(authentication.getName()).get().getId());
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
