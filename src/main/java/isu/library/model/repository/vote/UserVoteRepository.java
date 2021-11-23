/*
    IIS - projekt 2021
    Zadani: Knihovna
    Autori: Tomas Korbar <xkorba02>, Ondrej Babec <xbabec00>
 */
package isu.library.model.repository.vote;

import isu.library.model.entity.vote.UserVote;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("userVoteRepository")
public interface UserVoteRepository extends CrudRepository<UserVote, Integer> {
    Iterable<UserVoteRepository> findUserVotesByUserId(Integer userId);
}
