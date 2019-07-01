package com.place4code.reddit.service;


import com.place4code.reddit.model.User;
import com.place4code.reddit.repo.UserRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.Optional;

@Service
public class UserService {

    private final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepo userRepo;
    private final BCryptPasswordEncoder encoder;
    private final RoleService roleService;
    private final MailService mailService;

    public UserService(UserRepo userRepo, RoleService roleService, MailService mailService) {
        this.userRepo = userRepo;
        this.roleService = roleService;
        this.mailService = mailService;
        encoder = new BCryptPasswordEncoder();
    }

    public User register(User user) {

        //set the password
        String secret = "{bcrypt}" + encoder.encode(user.getPassword());
        user.setPassword(secret);
        user.setConfirmPassword(secret);

        // random String for activation account
        //user.setActivationCode(String.valueOf(UUID.randomUUID()));

        user.setAvatar(false);
        user.setEnabled(true);

        //add the roles
        user.addRole(roleService.findByName("ROLE_USER"));

        //save user
        save(user);

        //send the activation email
        //sendActivationEmail(user);

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
        mailService.sendActivationEmail(user);
    }

    public void sendWelcomeEmail(User user) {
        mailService.sendWelcomeEmail(user);
    }

    public Optional<User> findByEmailAndActivationCode(String email, String activationCode) {
        return userRepo.findByEmailAndActivationCode(email,activationCode);
    }

    public boolean uniqueEmail(String email) {
        return !userRepo.findByEmail(email).isPresent();
    }

    public boolean uniqueLogin(String login) {
        return !userRepo.findByLogin(login).isPresent();
    }

    public User findByLogin(String login) {
        return userRepo.findByLogin(login).get();
    }

    public Optional<User> findByLoginX(String login) {
        return userRepo.findByLogin(login);
    }

    public Optional<User> findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

}
