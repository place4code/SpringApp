package com.place4code.reddit.service;


import com.place4code.reddit.model.User;
import com.place4code.reddit.repo.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;

@Service
public class UserService {

    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User register(User user) {
        return user;
    }

    public User save(User user) {
        return userRepo.save(user);
    }

    @Transactional
    public void saveUsers(User... users) {
        Arrays.stream(users)
                .peek(user -> logger.info("Saving:" + user.getEmail()))
                .forEach(userRepo::save);
    }

}
