package com.place4code.reddit.repo;

import com.place4code.reddit.model.Link;
import com.place4code.reddit.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VoteRepo extends JpaRepository<Vote,Long> {
    Optional<Vote> findByUserIdAndLink(Long id, Link link);

    Optional<Vote> findByUserIdAndLinkAndDirection(Long id, Link link, short direction);
}
