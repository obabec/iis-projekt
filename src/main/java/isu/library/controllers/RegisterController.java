package isu.library.controllers;

import isu.library.model.entity.Person;
import isu.library.model.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Date;

@Controller
public class RegisterController {

    @Autowired
    MyUserDetailsService userDetailsService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/register")
    public String register(ModelMap modelMap) {
        modelMap.put("message", "");
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam(name="username", required = true, defaultValue = "") String username,
                           @RequestParam(name="password", required = true, defaultValue = "") String password,
                           @RequestParam(name="password2", required = true, defaultValue = "") String password2,
                           @RequestParam(name="name", required = true, defaultValue = "") String name,
                           @RequestParam(name="surname", required = true, defaultValue = "") String surname,
                           @RequestParam(name="birthdate", required = true, defaultValue = "") String birthdate,
                           ModelMap modelMap) {
        if (password.equals(password2)) {
            Person new_user = new Person(name, surname, Date.valueOf(birthdate), "USER", username, bCryptPasswordEncoder.encode(password), null);
            try {
                userDetailsService.registerNewUserAccount(new_user);
            } catch (Exception e) {
                modelMap.put("message", "Account with such username already exists");
                return "register";
            }
            modelMap.put("message", "Account was successfully created");
        } else {
            modelMap.put("message", "Passwords do not match");
        }
        return "register";
    }
}
