package com.place4code.reddit.repo;

import com.place4code.reddit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
}
