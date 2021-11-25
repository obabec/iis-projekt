/*
    IIS - projekt 2021
    Zadani: Knihovna
    Autori: Tomas Korbar <xkorba02>, Ondrej Babec <xbabec00>
 */
package isu.library.model.repository.vote;

import isu.library.model.entity.vote.UserVote;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository("userVoteRepository")
public interface UserVoteRepository extends CrudRepository<UserVote, Integer> {
    Iterable<UserVoteRepository> findUserVotesByUserId(Integer userId);

    @Modifying
    @Query(value = "DELETE FROM user_vote v WHERE v.vote_id = :vote_id", nativeQuery = true)
    void deleteByVoteId(@Param("vote_id") int voteId);
}
