/*
    IIS - projekt 2021
    Zadani: Knihovna
    Autori: Tomas Korbar <xkorba02>, Ondrej Babec <xbabec00>
 */
package isu.library.controllers;

import isu.library.model.entity.Book;
import isu.library.model.entity.BookOrder;
import isu.library.model.entity.Person;
import isu.library.model.service.AuthorshipService;
import isu.library.model.service.BookOrderService;
import isu.library.model.service.BookService;
import isu.library.model.service.library.LibraryService;
import isu.library.model.service.user.PersonService;
import isu.library.model.service.vote.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

/*
 * Controller for endpoint managing orders of books.
 */
@Controller
public class OrderController {

    @Autowired
    private BookOrderService bookOrderService;

    @Autowired
    private BookService bookService;

    @Autowired
    private LibraryService libraryService;

    @Autowired
    private AuthorshipService authorshipService;

    @Autowired
    private VoteService voteService;

    @Autowired
    private PersonService personService;

    @GetMapping("/orders")
    public String getOrders(@RequestParam(name = "library", required = false, defaultValue = "") Integer libraryId,
                            @RequestParam(name = "title", required = false, defaultValue = "") Integer titleId,
                            Authentication authentication,
                            ModelMap model) {

        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        Person user = personService.findPersonByUsername(username).get();
        if (((UserDetails) authentication.getPrincipal()).getAuthorities().contains(new SimpleGrantedAuthority("ROLE_LIBRARIAN"))) {
            model.put("librarian_lib", user.getLibraryId());
        }
        model.put("choosen_title", 0);
        model.put("choosen_library", 0);
        Iterable<BookOrder> orders = null;
        if (user.getLibraryId() != null) {
            model.put("possible_libraries", libraryService.findLibraryById(user.getLibraryId()));
            model.put("choosen_library", user.getLibraryId());
            if (titleId != null) {
                model.put("choosen_title", titleId);
                orders = bookOrderService.findByTitleIdAndLibraryId(titleId, user.getLibraryId());
            } else {
                orders = bookOrderService.findByLibraryId(user.getLibraryId());
            }
        } else {
            model.put("possible_libraries", libraryService.findAll());
            if (titleId != null) {
                model.put("choosen_title", titleId);
                if (libraryId != null) {
                    model.put("choosen_library", libraryId);
                    orders = bookOrderService.findByTitleIdAndLibraryId(titleId, libraryId);
                } else {
                    orders = bookOrderService.findByTitleId(titleId);
                }
            } else if (libraryId != null) {
                model.put("choosen_library", libraryId);
                orders = bookOrderService.findByLibraryId(libraryId);
            } else {
                orders = bookOrderService.findAll();
            }
        }

        for (BookOrder ord : orders) {
            ord.setTitleName(bookService.findById(ord.getTitleId()).getName());
            ord.setLibraryName(libraryService.findLibraryById(ord.getLibraryId()).getName());
        }
        model.put("possible_titles", bookService.findAllTitles());
        model.put("orders", orders);

        return "orders";
    }

    @GetMapping("/order/{title}")
    public String createOrder(@RequestParam(name = "count", required = true, defaultValue = "") Integer count,
                              @PathVariable("title") int titleId,
                              Authentication authentication) {
        if (authentication == null || !authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_LIBRARIAN")))
            return "redirect:/forbidden";

        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        Person user = personService.findPersonByUsername(username).get();
        int libraryId = user.getLibraryId();
        bookOrderService.addBookOrder(libraryId, titleId, count);
        return "redirect:/orders";
    }

    @GetMapping("/order/{id}/fulfill")
    public String finishOrder(@PathVariable("id") int orderId, ModelMap modelMap) {
        BookOrder order = bookOrderService.findById(orderId).get();
        Book template = bookService.findById(order.getTitleId());
        for (int i = 0; i < order.getCount(); i++) {
            int newBookId = bookService.addNewBook(order.getLibraryId(), template.getName(), template.getRelease(), template.getIsbn(),
                    template.getPublisher(), template.getGenre(), template.getRate());
            authorshipService.findAuthorshipByBookId(template.getId()).forEach(orig_auth -> authorshipService.addNewAuthorship(orig_auth.getAuthorId(), newBookId));

        }

        bookOrderService.removeById(orderId);
        return "redirect:/orders";
    }

    @GetMapping("/order/{id}/delete")
    public String deleteOrder(@PathVariable("id") int orderId) {
        bookOrderService.removeById(orderId);
        return "redirect:/orders";
    }

}
