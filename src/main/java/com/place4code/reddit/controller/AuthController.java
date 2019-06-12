package com.place4code.reddit.controller;

import com.place4code.reddit.model.Comment;
import com.place4code.reddit.model.Link;
import com.place4code.reddit.model.User;
import com.place4code.reddit.service.CommentService;
import com.place4code.reddit.service.LinkService;
import com.place4code.reddit.service.UserService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class AuthController {

    private UserService userService;
    private LinkService linkService;
    private CommentService commentService;

    public AuthController(UserService userService, LinkService linkService, CommentService commentService) {
        this.userService = userService;
        this.linkService = linkService;
        this.commentService = commentService;
    }

    @GetMapping("/login")
    public String login() {
        return "auth/login";
    }

    @GetMapping("/profile")
    public String profile() {
        return "auth/profile";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";
    }

    @PostMapping("/register")
    public String registerNewUser(@Valid User user, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        if( bindingResult.hasErrors() ) {
            model.addAttribute("user",user);
            model.addAttribute("validationErrors", bindingResult.getAllErrors());
            return "auth/register";
        } else {
            // register new user
            User newUser = userService.register(user);
            redirectAttributes
                    .addAttribute("id", newUser.getId())
                    .addFlashAttribute("success",true);
            return "redirect:/register";
        }
    }

    @GetMapping("/activate/{email}/{activationCode}")
    public String activate(@PathVariable String email, @PathVariable String activationCode) {

        Optional<User> tempUser = userService.findByEmailAndActivationCode(email, activationCode);
        if (tempUser.isPresent()) {
            User user = tempUser.get();
            user.setEnabled(true);
            user.setConfirmPassword(user.getPassword());
            userService.save(user);
            userService.sendWelcomeEmail(user);
            return "auth/activated";
        }
        //if doesn't exists:
        return "redirect:/";
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

        model.addAttribute("counterComments", comments.size());
        model.addAttribute("counterLinks", links.size());
        model.addAttribute("email", user.getEmail());

        return "auth/profile";
    }

}
