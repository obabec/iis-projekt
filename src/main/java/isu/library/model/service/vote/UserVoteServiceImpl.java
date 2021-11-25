/*
    IIS - projekt 2021
    Zadani: Knihovna
    Autori: Tomas Korbar <xkorba02>, Ondrej Babec <xbabec00>
 */
package isu.library.model.service.vote;

import isu.library.model.entity.vote.UserVote;
import isu.library.model.repository.vote.UserVoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Transactional
@Service
public class UserVoteServiceImpl implements UserVoteService {

    @Autowired
    private UserVoteRepository userVoteRepository;


    @Override
    public void saveNewUserVote(Integer voteId, Integer userId) {
        userVoteRepository.save(new UserVote(voteId, userId));
    }

    @Override
    public void deleteByVoteId(Integer voteId) {
        userVoteRepository.deleteByVoteId(voteId);
    }
}
