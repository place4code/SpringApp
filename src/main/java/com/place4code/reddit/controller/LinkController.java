package com.place4code.reddit.controller;

import com.place4code.reddit.model.Link;
import com.place4code.reddit.repo.LinkRepo;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/links")
public class LinkController {


    private LinkRepo linkRepo;

    public LinkController(LinkRepo linkRepo) {
        this.linkRepo = linkRepo;
    }

    // list
    @GetMapping("/")
    public List<Link> list() {
        return linkRepo.findAll();
    }

    // CRUD
    @PostMapping("/create")
    public Link create(@ModelAttribute Link link) {
        return linkRepo.save(link);
    }

    @GetMapping("/{id}")
    public Optional<Link> read(@PathVariable Long id) {

        return linkRepo.findById(id);

        /*
        Optional<Link> temp = linkRepo.findById(id);
        if (temp.isPresent()) {
            return temp.get();
        } else {
            throw new RuntimeException("Link doesn't found");
        }
        */

    }

    @PutMapping("/{id}")
    public Link update(@ModelAttribute Link link) {
        return linkRepo.save(link);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        linkRepo.deleteById(id);
    }

}
