package com.place4code.reddit.service;


import com.place4code.reddit.model.User;
import com.place4code.reddit.repo.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;

@Service
public class UserService {

    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepo userRepo;
    private final BCryptPasswordEncoder encoder;
    private final RoleService roleService;

    public UserService(UserRepo userRepo, RoleService roleService) {
        this.userRepo = userRepo;
        this.roleService = roleService;
        encoder = new BCryptPasswordEncoder();
    }

    public User register(User user) {

        //set the password
        String secret = "{bcrypt}" + encoder.encode(user.getPassword());
        user.setPassword(secret);
        user.setEnabled(true);

        //add the roles
        user.addRole(roleService.findByName("ROLE_USER"));

        //save user
        save(user);

        //send the activation email
        sendActivationEmail(user);

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

    public void sendActivationEmail(User user) {
        // to do
    }
}
