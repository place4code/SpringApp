package com.place4code.reddit.controller;

import com.place4code.reddit.model.Link;
import com.place4code.reddit.repo.LinkRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

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

    @GetMapping("/link/{id}")
    public String getLink(@PathVariable Long id, Model model) {
        Optional<Link> tempLink = linkRepo.findById(id);
        if (tempLink.isPresent()) {
            model.addAttribute("link", tempLink.get());
            return "link/link";
        } else {
            return "redirect:/";
        }
    }




}
