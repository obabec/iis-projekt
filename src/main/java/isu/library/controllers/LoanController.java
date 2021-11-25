/*
    IIS - projekt 2021
    Zadani: Knihovna
    Autori: Tomas Korbar <xkorba02>, Ondrej Babec <xbabec00>
 */
package isu.library.controllers;

import isu.library.model.entity.Person;
import isu.library.model.entity.reservation.Reservation;
import isu.library.model.service.BookService;
import isu.library.model.service.reservation.ReservationService;
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

import java.time.LocalDate;

/*
 * Controller for endpoints managing loaning of books.
 */
@Controller
public class LoanController {

    @Autowired
    ReservationService reservationService;

    @Autowired
    PersonService personService;

    @Autowired
    BookService bookService;

    @PostMapping("/reservations/createLoan")
    public String createLoan(@ModelAttribute(value = "reservation") Reservation reservation,
                             ModelMap modelMap,
                             Authentication authentication) {
        if (authentication != null && ((UserDetails) authentication.getPrincipal()).getAuthorities().contains(new SimpleGrantedAuthority("ROLE_LIBRARIAN"))) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            Person user = personService.findPersonByUsername(username).get();
            modelMap.put("librarian_lib", user.getLibraryId());
            // Osetreni refreshe - asi by to chtelo vymyslet lepe a udelat dotaz na databazi jestli je kniha aktualne dostupna
            if (!bookService.isBookAvailable(reservation.getBookId())) {
                modelMap.put("readers", personService.findPersonsByRole("READER"));
                modelMap.put("reservation", new Reservation());
                modelMap.put("message", "Kniha je jiz zapujcena");
                modelMap.put("availableBooks", bookService.findAvailableBooks(user.getLibraryId()));
                return "createLoan";
            }
            modelMap.put("readers", personService.findPersonsByRole("READER"));
            modelMap.put("reservation", new Reservation());
            int id = reservationService.saveNewLoan(reservation.getBookId(), reservation.getPersonId(), LocalDate.now());
            if (id == -1) {
                modelMap.put("message", "Vypujcku se nepodarilo vytvorit");
            } else {
                modelMap.put("message", "Vypujcka byla evidovana v systemu");
            }
            modelMap.put("availableBooks", bookService.findAvailableBooks(user.getLibraryId()));
            return "createLoan";
        } else {
            return "redirect:/";
        }

    }

    @GetMapping("/reservations/createLoan")
    public String createLoanForm(Authentication authentication,
                                 ModelMap modelMap) {
        if (authentication != null && ((UserDetails) authentication.getPrincipal()).getAuthorities().contains(new SimpleGrantedAuthority("ROLE_LIBRARIAN"))) {
            String username = ((UserDetails) authentication.getPrincipal()).getUsername();
            Person user = personService.findPersonByUsername(username).get();
            modelMap.put("availableBooks", bookService.findAvailableBooks(user.getLibraryId()));
            modelMap.put("readers", personService.findPersonsByRole("READER"));
            modelMap.put("reservation", new Reservation());
            return "createLoan";
        } else {
            return "redirect:/";
        }
    }
}
