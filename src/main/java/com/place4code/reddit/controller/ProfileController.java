package com.place4code.reddit.controller;

import com.place4code.reddit.model.*;
import com.place4code.reddit.repo.CommentRepo;
import com.place4code.reddit.repo.LinkRepo;
import com.place4code.reddit.service.FavService;
import com.place4code.reddit.service.LikeService;
import com.place4code.reddit.service.StorageService;
import com.place4code.reddit.service.UserService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class ProfileController {

    private LinkRepo linkService;
    private CommentRepo commentService;
    private final StorageService storageService;
    private UserService userService;
    private LikeService likeService;
    private FavService favService;


    public ProfileController(LinkRepo linkService, CommentRepo commentService, StorageService storageService, UserService userService, LikeService likeService, FavService favService) {
        this.linkService = linkService;
        this.commentService = commentService;
        this.storageService = storageService;
        this.userService = userService;
        this.likeService = likeService;
        this.favService = favService;
    }

    @Secured({"ROLE_USER"})
    @GetMapping("/user/{login}")
    public String showAccount(@PathVariable String login, Model model) {

        // Who is logged
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        // profile owner
        User profileOwner = userService.findByLogin(login);

        boolean isOwner = false;
        if (login.equals(user.getLogin())) isOwner = true;

        //find user's links
        List<Link> links = linkService.findAllByUserId(profileOwner.getId());

        //find user's comments
        List<Comment> comments = commentService.findAllByCreatedBy(profileOwner.getEmail());

        //find user's likes
        List<Likes> likes = likeService.findAllByUserId(profileOwner.getId());

        //find user' favourite
        List<Fav> favourites = favService.findAllByUserId(profileOwner.getId());

        if (profileOwner.isAvatar()) {
            model.addAttribute("avatar", profileOwner.getLogin() + ".jpg");
        } else {
            model.addAttribute("avatar", "demo.jpg");
        }

        model.addAttribute("owner", isOwner);
        model.addAttribute("comments", comments);
        model.addAttribute("likes", likes);
        model.addAttribute("favourites", favourites);
        model.addAttribute("counterComments", comments.size());
        model.addAttribute("links", links);
        model.addAttribute("email", profileOwner.getEmail());
        model.addAttribute("profileOwner", profileOwner);

        return "auth/profile";
    }

    @Secured({"ROLE_USER"})
    @GetMapping("/edit/photo")
    public String editPhotoForm() {
        return "auth/edit_photo";
    }

    @Secured({"ROLE_USER"})
    @PostMapping("/edit/photo")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!file.isEmpty()) {
            if (file.getContentType().equalsIgnoreCase("image/jpeg")) {

                storageService.store(file);

                Optional<User> tempUser = userService.findByEmail(user.getEmail());
                if (tempUser.isPresent()) {
                    User userToSave = tempUser.get();
                    userToSave.setAvatar(true);
                    userToSave.setConfirmPassword(user.getPassword());
                    userService.save(userToSave);
                }


                redirectAttributes.addFlashAttribute("message",
                        "The photo has been changed");
                redirectAttributes.addFlashAttribute("error",
                        false);



                return "redirect:/user/" + user.getLogin();

            } else {

                redirectAttributes.addFlashAttribute("message",
                        "The file must be a picture");
                redirectAttributes.addFlashAttribute("error",
                        true);

            }
        }

        return "redirect:/user/" + user.getLogin();

    }

}
