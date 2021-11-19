package isu.library.model.service.vote;

import isu.library.model.entity.Book;
import isu.library.model.entity.library.Library;
import isu.library.model.entity.vote.Vote;

public interface VoteService {
    Iterable<Vote> findVotes(Integer userId, Integer libraryId);

    void saveNewVote(Book book, Library library);

    void incrementVote(Integer voteId);

    Iterable<Vote> findVotesInLibrary(Integer libraryId);

    void deleteVote(Integer voteId);
}
