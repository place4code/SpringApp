package com.place4code.reddit.repo;

import com.place4code.reddit.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepo extends JpaRepository<Like,Long> {
}
