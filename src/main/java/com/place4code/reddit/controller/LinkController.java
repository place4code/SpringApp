package com.place4code.reddit.controller;

import com.place4code.reddit.model.Link;
import com.place4code.reddit.repo.LinkRepo;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
public class LinkController {


    private LinkRepo linkRepo;

    public LinkController(LinkRepo linkRepo) {
        this.linkRepo = linkRepo;
    }

    //index
    @GetMapping("/")
    public String index(HttpServletRequest request, Model model) {

        int page = 0;
        int size = 2;


        if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
            page = Integer.parseInt(request.getParameter("page")) - 1;
        }

        if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
            size = Integer.parseInt(request.getParameter("size"));
        }


        model.addAttribute("links", linkRepo.findAll(PageRequest.of(page, size)));
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
