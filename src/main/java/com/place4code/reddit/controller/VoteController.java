package com.place4code.reddit.controller;

import com.place4code.reddit.model.Link;
import com.place4code.reddit.model.Vote;
import com.place4code.reddit.repo.LinkRepo;
import com.place4code.reddit.repo.VoteRepo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class VoteController {

    private VoteRepo voteRepo;
    private LinkRepo linkRepo;

    public VoteController(VoteRepo voteRepo, LinkRepo linkRepo) {
        this.voteRepo = voteRepo;
        this.linkRepo = linkRepo;
    }

                //vote/link/linkId/direction/direction/votesCounter/votesCounter
    @GetMapping("/vote/link/{id}/direction/{direction}/votesCounter/{votesCounter}")
    public int vote(@PathVariable Long id,
                    @PathVariable short direction,
                    @PathVariable int votesCounter) {

        //try to find a link
        Optional<Link> tempLink= linkRepo.findById(id);

        if (tempLink.isPresent()) {

            //save vote
            Link link = tempLink.get();
            Vote vote = new Vote(direction, link);
            voteRepo.save(vote);

            //update counter
            link.setVotesCounter(votesCounter + direction);
            linkRepo.save(link);

            return votesCounter + direction;

        }
        //return counter
        return votesCounter;
    }

}
