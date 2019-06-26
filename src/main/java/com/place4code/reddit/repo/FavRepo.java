package com.place4code.reddit.repo;

import com.place4code.reddit.model.Fav;
import com.place4code.reddit.model.Link;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FavRepo extends JpaRepository<Fav, Long> {

    Optional<Fav> findByLinkAndUserId(Link link, Long id);
}
