package com.place4code.reddit.service;

import com.place4code.reddit.model.Link;
import com.place4code.reddit.repo.LinkRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LinkService {

    private LinkRepo linkRepo;

    public LinkService(LinkRepo linkRepo) {
        this.linkRepo = linkRepo;
    }

    public List<Link> findAll() {
        return linkRepo.findAll();
    }

    public Optional<Link> findById(Long id) {
        return linkRepo.findById(id);
    }

    public Link save(Link link) {
        return linkRepo.save(link);
    }

    public List<Link> findAllByUserId(Long id) {
        return linkRepo.findAllByUserId(id);
    }

    public void delete(Link link) {
        linkRepo.delete(link);
    }

    public void deleteById(Long id) {
        linkRepo.deleteById(id);
    }
}
