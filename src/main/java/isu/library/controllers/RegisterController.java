package isu.library.controllers;

import isu.library.model.entity.Person;
import isu.library.model.entity.reservation.Reservation;
import isu.library.model.service.user.MyUserDetailsService;
import isu.library.model.service.reservation.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;
import java.util.Optional;

@Controller
public class RegisterController {

    @Autowired
    MyUserDetailsService userDetailsService;

    @Autowired
    ReservationService reservationService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/register")
    public String register(@RequestParam(name="book_id", required = false, defaultValue = "-1") Integer bookId,
                           ModelMap modelMap) {
        modelMap.put("message", "");
        modelMap.put("book_id", bookId);
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam(name="username", required = true, defaultValue = "") String username,
                           @RequestParam(name="password", required = true, defaultValue = "") String password,
                           @RequestParam(name="password2", required = true, defaultValue = "") String password2,
                           @RequestParam(name="name", required = true, defaultValue = "") String name,
                           @RequestParam(name="surname", required = true, defaultValue = "") String surname,
                           @RequestParam(name="birthdate", required = true, defaultValue = "") String birthdate,
                           @RequestParam(name="book_id", required = false, defaultValue = "-1") Integer bookId,
                           ModelMap modelMap) {
        if ((username == null || password == null || password2 == null || name == null || surname == null || birthdate == null) ||
            username.length() == 0 || password.length() < 8 || name.length() == 0 || surname.length() == 0) {
                modelMap.put("message", "Please enter all values");
                return "register";
        } else {
            try{
                Date.valueOf(birthdate);
            } catch (Exception e) {
                modelMap.put("message", "Please enter all values");
                return "register";
            }
        }

        if (password.equals(password2)) {
            Person newUser = new Person(name, surname, Date.valueOf(birthdate), "READER", username, bCryptPasswordEncoder.encode(password), null);
            try {
                userDetailsService.registerNewUserAccount(newUser);
            } catch (Exception e) {
                modelMap.put("message", "Account with such username already exists");
                return "register";
            }

            if (bookId != -1 ) {
                Optional<Reservation> latestRes = reservationService.findReservationByLatestDate(bookId);
                if (latestRes.isEmpty()) {
                    reservationService.saveNewReservation(bookId, newUser.getId(), null);
                } else {
                    reservationService.saveNewReservation(bookId, newUser.getId(), latestRes.get().getDateTo().toLocalDate().plusDays(1));
                }
                modelMap.put("message", "Account was successfully created, reservation for the book was created and " +
                        "bound to your new account.");
            } else {
                modelMap.put("message", "Account was successfully created");
            }
        } else {
            modelMap.put("message", "Passwords do not match");
        }
        return "register";
    }
}
