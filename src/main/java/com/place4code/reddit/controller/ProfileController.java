package com.place4code.reddit.controller;

import com.place4code.reddit.model.Comment;
import com.place4code.reddit.model.Link;
import com.place4code.reddit.model.User;
import com.place4code.reddit.repo.CommentRepo;
import com.place4code.reddit.repo.LinkRepo;
import com.place4code.reddit.service.StorageService;
import com.place4code.reddit.storage.FileStorage;
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

@Controller
public class ProfileController {

    private LinkRepo linkService;
    private CommentRepo commentService;
    private FileStorage fileStorage;
    private final StorageService storageService;


    public ProfileController(LinkRepo linkService, CommentRepo commentService, FileStorage fileStorage, StorageService storageService) {
        this.linkService = linkService;
        this.commentService = commentService;
        this.fileStorage = fileStorage;
        this.storageService = storageService;
    }

    @Secured({"ROLE_USER"})
    @GetMapping("/user/{login}")
    public String showAccount(@PathVariable String login, Model model) {

        // Who is logged
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //find user's links
        List<Link> links = linkService.findAllByUserId(user.getId());
        if (!links.isEmpty()) {
            model.addAttribute("links", links);
        }

        //find user's comments
        List<Comment> comments = commentService.findAllByCreatedBy(user.getEmail());

        if (user.isAvatar()) {
            model.addAttribute("avatar", user.getLogin() + ".jpg");
        } else {
            model.addAttribute("avatar", "demo.jpg");
        }
        model.addAttribute("comments", comments);
        model.addAttribute("counterComments", comments.size());
        model.addAttribute("counterLinks", links.size());
        model.addAttribute("email", user.getEmail());

        return "auth/profile";
    }

    @Secured({"ROLE_USER"})
    @GetMapping("/edit/photo")
    public String editPhotoForm() {
        return "auth/edit_photo";
    }




    @PostMapping("/edit/photo")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes, User user) {

        if (!file.isEmpty()) {
            if (file.getContentType().equalsIgnoreCase("image/jpeg")) {

                storageService.store(file);
/*
                redirectAttributes.addFlashAttribute("message",
                        "You successfully uploaded " + file.getOriginalFilename() + "!");*/
                return "redirect:/user/" + user.getLogin();

            } else {

                redirectAttributes.addFlashAttribute("message",
                        "The file must be a picture");

            }
        }

        return "redirect:/edit/photo";

    }

}
