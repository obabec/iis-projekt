package isu.library.model.service.vote;

import isu.library.model.entity.library.Library;
import isu.library.model.entity.vote.Vote;
import isu.library.model.repository.LibraryRepository;
import isu.library.model.repository.vote.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Transactional
@Service
public class VoteServiceImpl implements VoteService {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private LibraryRepository libraryRepository;

    @Override
    public Iterable findVotes(Integer userId, Integer libraryId) {
        Iterable<Vote> allVotes = voteRepository.findVotesByLibraryId(libraryId);
        List<Integer> blockedVotes = StreamSupport.stream(voteRepository.findBlockedVotes(libraryId, userId).spliterator(), false)
                .map(Vote::getId).collect(Collectors.toList());
        for (Vote vote : allVotes) {
            if (blockedVotes.contains(vote.getId())) {
                vote.setEnabled(false);
            }
        }
        return allVotes;
    }

    @Override
    public Iterable<Vote> findAllVotes() {
        return voteRepository.findAll();
    }

    @Override
    public void saveNewVote(String name, Integer libraryId) {
        List<String> names = StreamSupport.stream(voteRepository.findVotesByLibraryId(libraryId).spliterator(), false).map(Vote::getBookName).collect(Collectors.toList());
        Optional<Library> library = libraryRepository.findById(libraryId);
        if (!names.contains(name)) {
            library.ifPresent(value -> voteRepository.save(new Vote(name, value, 0)));
        }
    }

    @Override
    public void saveNewVote(String name, Library library) {
        voteRepository.save(new Vote(name, library, 0));
    }

    @Override
    public void incrementVote(Integer voteId) {
        Optional<Vote> vote = voteRepository.findVoteById(voteId);
        if (vote.isPresent()) {
            vote.get().setVoteAmount(vote.get().getVoteAmount() + 1);
            voteRepository.save(vote.get());
        }
    }

    @Override
    public Iterable<Vote> findVotesInLibrary(Integer libraryId) {
        return voteRepository.findVotesByLibraryId(libraryId);
    }

    @Override
    public void deleteVote(Integer voteId) {
        voteRepository.deleteById(voteId);
    }

}
