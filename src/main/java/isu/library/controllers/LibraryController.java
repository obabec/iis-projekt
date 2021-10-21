package isu.library.controllers;

import isu.library.model.entity.Library;
import isu.library.model.entity.Person;
import isu.library.model.query.LibraryQueryBuilder;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Time;

@Controller
public class LibraryController {

    @Autowired
    private LibraryService libraryService;

    @Autowired
    private PersonService personService;

    @GetMapping("/libraries")
    public String libraries(@RequestParam(name="library_name", required = true, defaultValue = "") String libraryName,
                            @RequestParam(name="library_tag", required = true, defaultValue = "") String libraryTag,
                            @RequestParam(name="library_city", required = true, defaultValue = "") String libraryCity,
                            ModelMap modelMap) {
        LibraryQueryBuilder builder = new LibraryQueryBuilder();
        if (!libraryName.isEmpty()) {
            modelMap.put("lib_name", libraryName);
            builder = builder.findByName(libraryName);
        }
        if (!libraryTag.isEmpty()) {
            modelMap.put("lib_tag", libraryTag);
            builder = builder.findByTag(libraryTag);
        }

        if (!libraryCity.isEmpty()) {
            modelMap.put("lib_city", libraryCity);
            builder = builder.findByCity(libraryCity);
        }

        modelMap.put("libraries", libraryService.executeQuery(builder.getGuery()));
        return "libraries";
    }

    @GetMapping("/library")
    public String library(@RequestParam(name="library_id", required = false, defaultValue = "") Integer libraryId,
                          Authentication authentication,
                          ModelMap modelMap) {
        if (authentication != null && ((UserDetails)authentication.getPrincipal()).getAuthorities().contains(new SimpleGrantedAuthority("ROLE_LIBRARIAN"))) {
            String username = ((UserDetails)authentication.getPrincipal()).getUsername();
            Person user = personService.findPersonByUsername(username).get();
            if (!user.getLibraryId().equals(libraryId)) {
                return "home";
            }
        }
        if (libraryId == null) {
            Library library = new Library();
            library.setOpenFrom(Time.valueOf("08:10:00"));
            library.setOpenTo(Time.valueOf("08:10:00"));
            modelMap.put("library", library);
        } else {
            modelMap.put("library", libraryService.findLibraryById(libraryId));
        }
        return "library";
    }

    @PostMapping("/library")
    public String updateLibrary(@ModelAttribute(value="library") Library library,
                                Authentication authentication,
                                ModelMap modelMap) {
        if (authentication != null && ((UserDetails)authentication.getPrincipal()).getAuthorities().contains(new SimpleGrantedAuthority("ROLE_LIBRARIAN"))) {
            String username = ((UserDetails)authentication.getPrincipal()).getUsername();
            Person user = personService.findPersonByUsername(username).get();
            if (!user.getLibraryId().equals(library.getId())) {
                return "home";
            }
        }
        if (library.getId() == null) {
            libraryService.addNewLibrary(library.getName(), library.getTag(), library.getStreet(), library.getCity(),
                    library.getStreetNumber(), library.getOpenFrom(), library.getOpenTo(), library.getDescription());
        } else {
            libraryService.updateLibrary(library);
        }
        modelMap.put("library", library);
        return "library";
    }

    @GetMapping("/deleteLibrary")
    public String deleteLibrary(@RequestParam(name="library_id", required = true, defaultValue = "") Integer libraryId,
                                Authentication authentication,
                                ModelMap modelMap) {
        if (authentication != null && ((UserDetails)authentication.getPrincipal()).getAuthorities().contains(new SimpleGrantedAuthority("ROLE_LIBRARIAN"))) {
            return "home";
        }
        libraryService.deleteLibraryById(libraryId);
        modelMap.put("libraries", libraryService.findAll());
        return "libraries";
    }
}
