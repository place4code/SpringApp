package com.place4code.reddit.controller;

import com.place4code.reddit.model.Comment;
import com.place4code.reddit.model.User;
import com.place4code.reddit.service.CommentService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@Secured({"ROLE_USER"})
public class CommentController {

    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    @GetMapping("/comment/{id}")
    public String editCommentForm(@PathVariable Long id, Model model) {

        //logged user
        User tempUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Optional<Comment> tempComment = commentService.findById(id);

        if (tempComment.isPresent()) {
            Comment comment = tempComment.get();
            //user's comment?
            if (comment.getLogin().equalsIgnoreCase(tempUser.getLogin())) {
                //if yes use can edit comment
                model.addAttribute("comment", comment);
                model.addAttribute("commenId", comment.getId());
                return "comment/edit";
            }

        }

        // Just in case
        return "redirect:/";
    }


    @PostMapping("/comment/{id}")
    public String editComment(@Valid Comment comment, @PathVariable Long id, RedirectAttributes redirectAttributes) {


        System.out.println(comment.getLink());
        System.out.println(comment.getId());
        System.out.println(comment.getLogin());
        System.out.println(comment.getBody());


        //logged user
        User tempUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Optional<Comment> tempComment = commentService.findById(id);

        if (tempComment.isPresent()) {

            // I must this way because MyTime class
            Comment commentToSave = tempComment.get();
            commentToSave.setLogin(tempUser.getLogin());
            commentToSave.setBody(comment.getBody());
            commentToSave.setId(id);

            commentService.save(commentToSave);

            redirectAttributes.addFlashAttribute("message",
                    "The comment has been edited");
            return "redirect:/link/" + comment.getLink().getId();

        }

        return "redirect:/";
    }


}
