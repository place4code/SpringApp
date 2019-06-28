package com.place4code.reddit.service;

import com.place4code.reddit.model.Link;
import com.place4code.reddit.model.Vote;
import com.place4code.reddit.repo.VoteRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VoteService {

    private VoteRepo voteRepo;

    public VoteService(VoteRepo voteRepo) {
        this.voteRepo = voteRepo;
    }

    public void save(Vote vote) {
        voteRepo.save(vote);
    }

    public Optional<Vote> findByUserIdAndLink(Long id, Link link) {
        return voteRepo.findByUserIdAndLink(id, link);
    }

    public void delete(Vote vote) {
        voteRepo.delete(vote);
    }

    public Optional<Vote> findByUserIdAndLinkAndDirection(Long id, Link link, short direction) {
        return voteRepo.findByUserIdAndLinkAndDirection(id, link, direction);
    }
}
