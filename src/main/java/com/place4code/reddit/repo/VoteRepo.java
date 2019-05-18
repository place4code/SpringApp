package com.place4code.reddit.repo;

import com.place4code.reddit.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VoteRepo extends JpaRepository<Vote,Long> {
}
