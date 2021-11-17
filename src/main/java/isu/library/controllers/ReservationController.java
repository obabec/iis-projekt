package isu.library.controllers;

import isu.library.model.entity.Person;
import isu.library.model.entity.reservation.Reservation;
import isu.library.model.service.user.PersonService;
import isu.library.model.service.reservation.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
public class ReservationController {

    @Autowired
    ReservationService reservationService;

    @Autowired
    PersonService personService;

    private static final int RESERVATION_DURATION = 2;
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

    @GetMapping("/reservationExtend")
    public String reservationExtend(@RequestParam(name="reservation_id", required = true, defaultValue = "") Integer reservationId,
                                    Authentication authentication,
                                    ModelMap modelMap) {
        Optional<Reservation> reservation = reservationService.findReservationById(reservationId);
        if (reservation.isPresent()) {
            reservation.get().setDateTo(Date.valueOf(reservation.get().getDateTo().toLocalDate().plusDays(7)));
            reservationService.updateReservation(reservation.get());
        }
        return "redirect:/reservationSummary";
    }

    @GetMapping("/reservations/delete")
    public String deleteReservation(@RequestParam(name="reservation_id", required = true, defaultValue = "") Integer reservation_id,
                                    Authentication authentication,
                                    ModelMap modelMap) {
        if (reservation_id == null) {
            return "redirect:/error";
        }
        Optional<Reservation> res = reservationService.findReservationById(reservation_id);
        if (!res.isPresent()) {
            return "redirect:/error";
        }

        if (!authentication.isAuthenticated()) {
            return "redirect:/error";
        } else if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            Person person = personService.findPersonByUsername(authentication.getName()).get();
            if (person.getId() != res.get().getPersonId()){
                return "redirect:/error";
            }
        }
        reservationService.deleteReservation(reservation_id);
        return "redirect:/reservationSummary";
    }


    @GetMapping("/reservations/create")
    public String createReservation(@RequestParam(name="book_id", required = true, defaultValue = "") Integer bookId,
                                    Authentication authentication,
                                    ModelMap modelMap) {
        String username = ((UserDetails)authentication.getPrincipal()).getUsername();
        Integer personId = personService.findPersonByUsername(username).get().getId();
        Optional<Reservation> res = reservationService.findReservationByBookIdAndPersonId(bookId, personId);

        if (res.isPresent() && (res.get().getDateTo().toLocalDate().isAfter(LocalDate.now()) || res.get().getDateTo().toLocalDate().isEqual(LocalDate.now()))) {
            return "redirect:/?message=Rezervace na knihu jiz existuje!";
        } else {
            Optional<Reservation> latestRes = reservationService.findReservationByLatestDate(bookId);
            if (latestRes.isEmpty()) {
                reservationService.saveNewReservation(bookId, personId, null);
                return "redirect:/?message=Rezervace byla vytvorena";
            } else {
                reservationService.saveNewReservation(bookId, personId, latestRes.get().getDateTo().toLocalDate().plusDays(1));
                return "redirect:/?message=Kniha je aktualne rezervovana, pridavame vas do fronty cekatelu.";
            }
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
            modelMap.put("librarian_lib", user.getLibraryId());
            if (!user.getLibraryId().equals(libraryId)) {
                return "home";
            }
        }
        if (reservationId != null) {
            if (action == 1) {
                Optional<Reservation> res = reservationService.findReservationById(reservationId);
                List<Reservation> laterReservations = StreamSupport.stream(reservationService.findReservationsByBookIdAndDateFromGreaterThan(res.get().getBookId(),
                        Date.valueOf(LocalDate.now())).spliterator(), false).sorted(Comparator.comparing(Reservation::getDateFrom)).collect(Collectors.toList());
                int startDays = 15;
                for (Reservation laterRes : laterReservations) {
                    if (laterRes.getId() != res.get().getId()) {
                        laterRes.setDateFrom(Date.valueOf(LocalDate.now().plusDays(startDays)));
                        laterRes.setDateTo(Date.valueOf(LocalDate.now().plusDays(startDays + RESERVATION_DURATION)));
                        reservationService.updateReservation(laterRes);
                        startDays += 3;
                    }
                }
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
