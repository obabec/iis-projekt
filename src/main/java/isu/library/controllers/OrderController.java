package isu.library.controllers;

import isu.library.model.entity.Authorship;
import isu.library.model.entity.Book;
import isu.library.model.entity.BookOrder;
import isu.library.model.entity.library.Library;
import isu.library.model.service.AuthorService;
import isu.library.model.service.AuthorshipService;
import isu.library.model.service.BookOrderService;
import isu.library.model.service.BookService;
import isu.library.model.service.library.LibraryService;
import isu.library.model.service.vote.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

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

    @GetMapping("/orders")
    public String getOrders(@RequestParam(name="library", required = false, defaultValue = "") String libraryId,
                            @RequestParam(name="title", required = false, defaultValue = "") String titleId,
                            ModelMap model) {
        ArrayList<BookOrder> orders = new ArrayList<>();

        if (libraryId.isEmpty() && titleId.isEmpty()) {
            bookOrderService.findAll().forEach(ord -> orders.add(ord));
        } else if (!titleId.isEmpty()){
            bookOrderService.findByTitleId(Integer.valueOf(titleId)).forEach(ord -> orders.add(ord));
        } else {
            bookOrderService.findByLibraryId(Integer.valueOf(libraryId)).forEach(ord -> orders.add(ord));
        }

        for (BookOrder ord: orders) {
            ord.setTitleName(bookService.findById(ord.getTitleId()).getName());
            ord.setLibraryName(libraryService.findLibraryById(ord.getLibraryId()).getName());
        }
        model.put("possible_titles", bookService.findAllTitles());
        model.put("possible_libraries", libraryService.findAll());
        model.put("orders", bookOrderService.findAll());
        return "orders";
    }

    @GetMapping("/order")
    public String createOrder(@RequestParam(name="library", required = true, defaultValue = "") Integer libraryId,
                              @RequestParam(name="title", required = true, defaultValue = "") Integer titleId,
                              @RequestParam(name="count", required = true, defaultValue = "") Integer count) {
        bookOrderService.addBookOrder(libraryId, titleId, count);
        return "redirect:/orders";
    }

    @GetMapping("/order/{id}/fulfill")
    public String finishOrder(@PathVariable("id") int orderId, ModelMap modelMap) {
        BookOrder order = bookOrderService.findById(orderId).get();
        Book template = bookService.findById(order.getTitleId());
        for (int i =0; i<order.getCount(); i++){
            int newBookId = bookService.addNewBook(order.getLibraryId(), template.getName(), template.getRelease(), template.getIsbn(),
                                   template.getPublisher(), template.getGenre(), template.getRate());
            authorshipService.findAuthorshipByBookId(template.getId()).forEach(orig_auth -> authorshipService.addNewAuthorship(orig_auth.getAuthorId(), newBookId));

        }
        Library lib = new Library();
        lib.setId(order.getLibraryId());
        voteService.saveNewVote(template, lib);
        bookOrderService.removeById(orderId);
        return "redirect:/orders";
    }

}
