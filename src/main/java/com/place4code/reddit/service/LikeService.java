package com.place4code.reddit.service;

import com.place4code.reddit.model.Like;
import com.place4code.reddit.repo.LikeRepo;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

    private LikeRepo likeRepo;

    public LikeService(LikeRepo likeRepo) {
        this.likeRepo = likeRepo;
    }

    public void save(Like like) {
        likeRepo.save(like);
    }
}
