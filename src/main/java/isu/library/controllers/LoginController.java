/*
    IIS - projekt 2021
    Zadani: Knihovna
    Autori: Tomas Korbar <xkorba02>, Ondrej Babec <xbabec00>
 */
package isu.library.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/*
 * Controller for endpoint managing login of user into the system.
 */
@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(@RequestParam(name = "error", required = false, defaultValue = "") String error, ModelMap modelMap) {
        if (error.length() != 0) {
            modelMap.put("message", "Špatné přihlašovací údaje");
        }
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam(name = "username", required = true, defaultValue = "") String username,
                        @RequestParam(name = "password", required = true, defaultValue = "") String password) {
        return "redirect:/";
    }
}
