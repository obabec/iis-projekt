package isu.library.model.repository.vote;

import isu.library.model.entity.vote.Vote;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository("voteRepository")
public interface VoteRepository extends CrudRepository<Vote, Integer> {
    @Query(value="SELECT * FROM votes v WHERE v.id IN (SELECT u.vote_id FROM user_vote u " +
            "WHERE u.user_id = :user_id) AND v.library_id = :library_id", nativeQuery = true)
    Iterable<Vote> findBlockedVotes(@Param("library_id" )Integer library, @Param("user_id") Integer userId);
    Iterable<Vote> findVotesByLibraryId(Integer libraryId);
    Optional<Vote> findVoteById(Integer id);
}
