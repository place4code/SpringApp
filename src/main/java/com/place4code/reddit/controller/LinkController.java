package com.place4code.reddit.controller;

import com.place4code.reddit.repo.LinkRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LinkController {


    private LinkRepo linkRepo;

    public LinkController(LinkRepo linkRepo) {
        this.linkRepo = linkRepo;
    }

    //index
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("links", linkRepo.findAll());
        return "index";
    }



}
