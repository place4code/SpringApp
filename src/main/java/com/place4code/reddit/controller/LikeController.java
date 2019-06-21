package com.place4code.reddit.controller;

import com.place4code.reddit.model.Like;
import com.place4code.reddit.model.Link;
import com.place4code.reddit.service.LikeService;
import com.place4code.reddit.service.LinkService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class LikeController {

    private LikeService likeService;
    private LinkService linkService;

    public LikeController(LikeService likeService, LinkService linkService) {
        this.likeService = likeService;
        this.linkService = linkService;
    }

    @Secured({"ROLE_USER"})
    @GetMapping("/like/link/{id}/likesCounter/{likesCounter}")
    public int like(@PathVariable Long id, @PathVariable int likesCounter) {

        Optional<Link> tempLink = linkService.findById(id);

        if (tempLink.isPresent()) {

            Link link = tempLink.get();
            Like like = new Like(link);
            likeService.save(like);

            //update counter
            link.setLikesCounter(likesCounter + 1);
            linkService.save(link);

            return likesCounter + 1;
        }

        return likesCounter;

    }
}
