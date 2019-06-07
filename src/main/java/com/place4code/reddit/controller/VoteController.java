package com.place4code.reddit.controller;

import com.place4code.reddit.model.Link;
import com.place4code.reddit.model.Vote;
import com.place4code.reddit.service.LinkService;
import com.place4code.reddit.service.VoteService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class VoteController {

    private VoteService voteService;
    private LinkService linkService;

    public VoteController(VoteService voteService, LinkService linkService) {
        this.voteService = voteService;
        this.linkService = linkService;
    }

    @Secured({"ROLE_USER"})
    @GetMapping("/vote/link/{id}/direction/{direction}/votesCounter/{votesCounter}")
                //vote/link/linkId/direction/direction/votesCounter/votesCounter
    public int vote(@PathVariable Long id,
                    @PathVariable short direction,
                    @PathVariable int votesCounter) {

        //try to find a link
        Optional<Link> tempLink= linkService.findById(id);

        if (tempLink.isPresent()) {

            //save vote
            Link link = tempLink.get();
            Vote vote = new Vote(direction, link);
            voteService.save(vote);

            //update counter
            link.setVotesCounter(votesCounter + direction);
            linkService.save(link);

            return votesCounter + direction;

        }
        //return counter
        return votesCounter;
    }

}
