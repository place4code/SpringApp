package com.place4code.reddit.controller;

import com.place4code.reddit.model.Fav;
import com.place4code.reddit.model.Link;
import com.place4code.reddit.model.User;
import com.place4code.reddit.service.FavService;
import com.place4code.reddit.service.LinkService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class FavController {

    private LinkService linkService;
    private FavService favService;

    public FavController(LinkService linkService, FavService favService) {
        this.linkService = linkService;
        this.favService = favService;
    }

    @Secured({"ROLE_USER"})
    @GetMapping("/link/{id}/favourite")
    public int favourite(@PathVariable Long id) {

        //who's logged
        User tempUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //find the link
        Optional<Link> tempLink = linkService.findById(id);

        //if founded
        if (tempLink.isPresent()) {

            // get the link
            Link link = tempLink.get();

            //find favourite
            Optional<Fav> tempFavourite = favService.findByLinkAndUserId(link, tempUser.getId());

            // favourite doesn't exist
            if (!tempFavourite.isPresent()) {
                //save favourite
                Fav fav = new Fav(link, tempUser.getId());
                favService.save(fav);
                return 1;
            } else {
                //was founded already, so delete it
                Fav fav = tempFavourite.get();
                favService.delete(fav);
                return 0;
            }
        }

        return 0;

    }
}
