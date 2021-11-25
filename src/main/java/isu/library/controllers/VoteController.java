/*
    IIS - projekt 2021
    Zadani: Knihovna
    Autori: Tomas Korbar <xkorba02>, Ondrej Babec <xbabec00>
 */
package isu.library.controllers;

import isu.library.model.entity.Person;
import isu.library.model.entity.library.Library;
import isu.library.model.entity.vote.Vote;
import isu.library.model.service.library.LibraryService;
import isu.library.model.service.user.PersonService;
import isu.library.model.service.vote.UserVoteService;
import isu.library.model.service.vote.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
public class VoteController {

    @Autowired
    VoteService voteService;

    @Autowired
    UserVoteService userVoteService;

    @Autowired
    LibraryService libraryService;

    @Autowired
    PersonService personService;

    @GetMapping("/votes")
    public String votes(@RequestParam(name = "library_id", required = false, defaultValue = "-1") Integer libraryId,
                        @RequestParam(name = "vote_id", required = false, defaultValue = "-1") Integer voteId,
                        Authentication authentication,
                        ModelMap modelMap) {
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        Person person = personService.findPersonByUsername(username).get();
        if (authentication != null && ((UserDetails) authentication.getPrincipal()).getAuthorities().contains(new SimpleGrantedAuthority("ROLE_LIBRARIAN"))) {
            modelMap.put("librarian_lib", person.getLibraryId());
        }

        if (voteId != -1) {
            Iterable<Vote> uv = voteService.findVotes(person.getId(), libraryId);
            boolean cont = true;
            for (Vote v : uv) {
                if (v.getId() == voteId && !v.isEnabled()) {
                    cont = false;
                    break;
                }
            }
            if (cont) {
                userVoteService.saveNewUserVote(voteId, person.getId());
                voteService.incrementVote(voteId);
            }
        }

        if (libraryId != -1) {
            if (authentication != null && (((UserDetails) authentication.getPrincipal()).getAuthorities().contains(
                    new SimpleGrantedAuthority("ROLE_LIBRARIAN")) || ((UserDetails) authentication.getPrincipal())
                    .getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN")))) {
                modelMap.put("votes", voteService.findVotesInLibrary(libraryId));
            } else {
                modelMap.put("votes", voteService.findVotes(person.getId(), libraryId));
            }
            modelMap.put("library_id", libraryId);
        }
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_LIBRARIAN"))) {
            modelMap.put("libraries", new Library[]{libraryService.findLibraryById(person.getLibraryId())});
        } else {
            modelMap.put("libraries", libraryService.findAll());
        }


        return "votes";
    }

    @GetMapping("/deleteVote")
    public String deleteVote(@RequestParam(name = "library_id", required = false, defaultValue = "-1") Integer libraryId,
                             @RequestParam(name = "vote_id", required = false, defaultValue = "-1") Integer voteId,
                             Authentication authentication,
                             ModelMap modelMap) {
        if (voteId != -1) {
            userVoteService.deleteByVoteId(voteId);
            voteService.clearVote(voteId);
        }
        return "redirect:/votes?library_id=" + libraryId;
    }
}
