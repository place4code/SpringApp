package com.place4code.reddit.service;

import com.place4code.reddit.model.Vote;
import com.place4code.reddit.repo.VoteRepo;
import org.springframework.stereotype.Service;

@Service
public class VoteService {

    private VoteRepo voteRepo;

    public VoteService(VoteRepo voteRepo) {
        this.voteRepo = voteRepo;
    }

    public void save(Vote vote) {
        voteRepo.save(vote);
    }
}
