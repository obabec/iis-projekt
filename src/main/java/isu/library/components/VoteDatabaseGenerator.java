/*
    IIS - projekt 2021
    Zadani: Knihovna
    Autori: Tomas Korbar <xkorba02>, Ondrej Babec <xbabec00>
 */
package isu.library.components;

import isu.library.model.entity.Book;
import isu.library.model.entity.library.Library;
import isu.library.model.service.BookService;
import isu.library.model.service.library.LibraryService;
import isu.library.model.service.vote.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class VoteDatabaseGenerator {

    @Autowired
    VoteService voteService;

    @Autowired
    BookService bookService;

    @Autowired
    LibraryService libraryService;

    @EventListener(ApplicationReadyEvent.class)
    public void generateVotes() {
        Iterable<Book> titles = bookService.findAllTitles();
        Iterable<Library> libraries = libraryService.findAll();
        for (Book b : titles) {
            for (Library library : libraries) {
                voteService.saveNewVote(b, library);
            }
        }
    }

}
