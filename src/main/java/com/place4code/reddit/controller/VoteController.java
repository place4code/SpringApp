package com.place4code.reddit.controller;

import com.place4code.reddit.model.Link;
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

//        //try to find a link
//        Optional<Link> tempLink= linkService.findById(id);
//        // Who is logged
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        if (tempLink.isPresent()) {
//
//            Link link = tempLink.get();
//
//            Optional<Vote> tempVote = voteService.findByUserIdAndLinkAndDirection(user.getId(), link, direction);
//
//            if (!tempVote.isPresent()) {
//
//                Optional<Vote> reverseVoteTemp = voteService.findByUserIdAndLinkAndDirection(user.getId(), link, (short)(direction * (-1)));
//
//                if (reverseVoteTemp.isPresent()) {
//
//                    Vote reverseVote = reverseVoteTemp.get();
//                    voteService.delete(reverseVote);
//
//                    link.setVotesCounter( votesCounter + (short)(direction * (-1)));
//                    linkService.save(link);
//
//                    return votesCounter + (direction * (-1));
//
//                } else {
//
//                    voteService.save(new Vote(user.getId(), link, direction));
//                    link.setVotesCounter( votesCounter + (direction * (-1)));
//                    linkService.save(link);
//
//                    return votesCounter + (direction * (-1));
//
//                }
//
//
//
//            }
//
//
//        }
//        //return counter
//        return votesCounter;

        //try to find a link
        Optional<Link> tempLink= linkService.findById(id);

        if (tempLink.isPresent()) {

            //save vote
            Link link = tempLink.get();

            //update counter
            link.setVotesCounter(votesCounter + direction);
            linkService.save(link);

            return votesCounter + direction;

        }
        //return counter
        return votesCounter;
    }



}
