/*
    IIS - projekt 2021
    Zadani: Knihovna
    Autori: Tomas Korbar <xkorba02>, Ondrej Babec <xbabec00>
 */
package isu.library.model.service.vote;

public interface UserVoteService {
    void saveNewUserVote(Integer voteId, Integer userId);
    void deleteByVoteId(Integer voteId);
}
