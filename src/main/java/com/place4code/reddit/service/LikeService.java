package com.place4code.reddit.service;

import com.place4code.reddit.model.Likes;
import com.place4code.reddit.model.Link;
import com.place4code.reddit.repo.LikeRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikeService {

    private LikeRepo likeRepo;

    public LikeService(LikeRepo likeRepo) {
        this.likeRepo = likeRepo;
    }

    public void save(Likes like) {
        likeRepo.save(like);
    }

    public Optional<Likes> findByUserId(Long id) {
        return likeRepo.findByUserId(id);
    }

    public Optional<Likes> findFirstByUserId(Long id) {
        return likeRepo.findFirstByUserId(id);
    }

    public Optional<Likes> findFirstByLinkAndUserId(Link link, Long id) {
        return likeRepo.findFirstByLinkAndUserId(link, id);
    }

    public void delete(Likes like) {
        likeRepo.delete(like);
    }
}
