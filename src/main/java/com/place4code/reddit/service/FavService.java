package com.place4code.reddit.service;

import com.place4code.reddit.model.Fav;
import com.place4code.reddit.model.Link;
import com.place4code.reddit.repo.FavRepo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FavService {

    private FavRepo favRepo;

    public FavService(FavRepo favRepo) {
        this.favRepo = favRepo;
    }

    public Optional<Fav> findByLinkAndUserId(Link link, Long id) {
        return favRepo.findByLinkAndUserId(link, id);
    }

    public void save(Fav fav) {
        favRepo.save(fav);
    }

    public void delete(Fav fav) {
        favRepo.delete(fav);
    }

}
