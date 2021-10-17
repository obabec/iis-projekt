package isu.library.controllers;

import isu.library.model.entity.Person;
import isu.library.model.service.PersonService;
import isu.library.model.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ReservationController {

    @Autowired
    ReservationService reservationService;

    @Autowired
    PersonService personService;
    //todo: je potreba vyresit kdy budeme mazat prosle rezervace

    @GetMapping("/reservationSummary")
    public String reservations(Authentication authentication,
                               ModelMap modelMap) {

        if (authentication != null && ((UserDetails)authentication.getPrincipal()).getAuthorities().contains(new SimpleGrantedAuthority("ROLE_READER"))) {
            String username = ((UserDetails)authentication.getPrincipal()).getUsername();
            Person user = personService.findPersonByUsername(username).get();
            modelMap.put("reservations", reservationService.findAllUserReservations(user.getId()));
            return "reservation_summary";
        } else {
            return "home";
        }
    }

    @GetMapping("/reservations")
    public String reservations(@RequestParam(name="library_id", required = true, defaultValue = "") Integer libraryId,
                               @RequestParam(name="reservation_id", required = false, defaultValue = "") Integer reservationId,
                               @RequestParam(name="action", required = false, defaultValue = "") Integer action,
                               Authentication authentication,
                               ModelMap modelMap) {

        if (authentication != null && ((UserDetails)authentication.getPrincipal()).getAuthorities().contains(new SimpleGrantedAuthority("ROLE_LIBRARIAN"))) {
            String username = ((UserDetails)authentication.getPrincipal()).getUsername();
            Person user = personService.findPersonByUsername(username).get();
            if (!user.getLibraryId().equals(libraryId)) {
                return "home";
            }
        }
        if (reservationId != null) {
            if (action == 1) {
                reservationService.switchToBorrow(reservationId);
            } else {
                reservationService.deleteReservation(reservationId);
            }
        }
        modelMap.put("library", libraryId);
        modelMap.put("reservations", reservationService.findAllReservationsInLibrary(libraryId));
        return "reservations";
    }

}
