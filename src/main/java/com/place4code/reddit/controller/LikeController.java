package com.place4code.reddit.controller;

import com.place4code.reddit.model.Likes;
import com.place4code.reddit.model.Link;
import com.place4code.reddit.model.User;
import com.place4code.reddit.service.LikeService;
import com.place4code.reddit.service.LinkService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @GetMapping("/link/{id}/likesCounter/{likesCounter}")
    public int like(@PathVariable Long id, @PathVariable int likesCounter) {

        User tempUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Optional<Link> tempLink = linkService.findById(id);

        if (tempLink.isPresent()) {

            Link link = tempLink.get();
            Likes like = new Likes(link, tempUser.getId());
            likeService.save(like);

            //update counter
            link.setLikesCounter(likesCounter + 1);
            linkService.save(link);

            return likesCounter + 1;
        }

        return likesCounter;

    }
}
