package com.place4code.reddit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController {

    @GetMapping("/")
    public String hi(Model model, HttpServletRequest request) {
        model.addAttribute("message", "Message from controller (hi)");
        return "index";
    }

}
