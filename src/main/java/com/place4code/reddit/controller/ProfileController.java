package com.place4code.reddit.controller;

import com.place4code.reddit.model.Comment;
import com.place4code.reddit.model.Link;
import com.place4code.reddit.model.User;
import com.place4code.reddit.repo.CommentRepo;
import com.place4code.reddit.repo.LinkRepo;
import com.place4code.reddit.storage.FileStorage;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class ProfileController implements HandlerExceptionResolver {

    private LinkRepo linkService;
    private CommentRepo commentService;
    private FileStorage fileStorage;

    public ProfileController(LinkRepo linkService, CommentRepo commentService, FileStorage fileStorage) {
        this.linkService = linkService;
        this.commentService = commentService;
        this.fileStorage = fileStorage;
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
            model.addAttribute("avatar", user.getLogin());
        } else {
            model.addAttribute("avatar", "demo");
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
    public String uploadMultipartFile(@RequestParam("uploadfile") MultipartFile file, Model model) {
        //try save picture and redirect to profile
        try {
            fileStorage.store(file);
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String login = user.getLogin();
            return "redirect:/user/" + login;
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
        }
        return "auth/edit_photo";
    }

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        System.out.println("resolveException");
        ModelAndView modelAndView = new ModelAndView("auth/edit_photo");
        if (ex instanceof MaxUploadSizeExceededException) {
            modelAndView.getModel().put("message", "FAIL! File size exceeds limit!");
        }
        return modelAndView;
    }
}
