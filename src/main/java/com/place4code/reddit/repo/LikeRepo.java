package com.place4code.reddit.repo;

import com.place4code.reddit.model.Likes;
import com.place4code.reddit.model.Link;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepo extends JpaRepository<Likes,Long> {
    Optional<Likes> findByUserId(Long id);

    Optional<Likes> findFirstByUserId(Long id);

    Optional<Likes> findFirstByLinkAndUserId(Link link, Long id);
}
