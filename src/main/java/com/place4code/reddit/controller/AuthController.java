package com.place4code.reddit.controller;

import com.place4code.reddit.model.User;
import com.place4code.reddit.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
public class AuthController {

    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
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


}
