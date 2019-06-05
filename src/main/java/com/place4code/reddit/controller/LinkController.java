package com.place4code.reddit.controller;

import com.place4code.reddit.model.Comment;
import com.place4code.reddit.model.Link;
import com.place4code.reddit.repo.CommentRepo;
import com.place4code.reddit.repo.LinkRepo;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@Controller
public class LinkController {


    private LinkRepo linkRepo;
    private CommentRepo commentRepo;

    public LinkController(LinkRepo linkRepo, CommentRepo commentRepo) {
        this.linkRepo = linkRepo;
        this.commentRepo = commentRepo;
    }

    // show all links
    @GetMapping("/")
    public String index(HttpServletRequest request, Model model) {

        int page = 0;
        int size = 10;


        if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
            page = Integer.parseInt(request.getParameter("page")) - 1;
        }

        if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
            size = Integer.parseInt(request.getParameter("size"));
        }


        model.addAttribute("links", linkRepo.findAll(PageRequest.of(page, size)));
        return "index";
    }

    // view a one Link or redirect to the list
    @GetMapping("/link/{id}")
    public String getLink(@PathVariable Long id, Model model) {
        Optional<Link> tempLink = linkRepo.findById(id);
        if (tempLink.isPresent()) {

            Link link = tempLink.get();
            Comment comment = new Comment();
            comment.setLink(link);

            model.addAttribute("comment", comment);
            model.addAttribute("link", link);
            model.addAttribute("success", model.containsAttribute("success"));

            return "link/link";
        } else {
            return "redirect:/";
        }
    }

    // form view to create a new Link
    @GetMapping("/link/submit")
    public String createLinkForm(Model model) {
        model.addAttribute("link", new Link());
        return "link/submit";
    }

    @PostMapping("/link/submit")
    public String createLink(@Valid Link link, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("link", link);
            return "link/submit";
        } else {
            // save new Link in database
            linkRepo.save(link);
            redirectAttributes.addAttribute("id", link.getId())
                              .addFlashAttribute("success", true);

            return "redirect:/link/{id}";
        }

    }

    @Secured({"ROLE_USER"})
    @PostMapping("/link/comments")
    public String createComment(@Valid Comment comment, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            // to do with error
        } else {
            commentRepo.save(comment);
        }

        return "redirect:/link/" + comment.getLink().getId();
    }




}
