package com.place4code.reddit.bootstrap;

import com.place4code.reddit.model.Comment;
import com.place4code.reddit.model.Link;
import com.place4code.reddit.model.Role;
import com.place4code.reddit.model.User;
import com.place4code.reddit.repo.CommentRepo;
import com.place4code.reddit.repo.LinkRepo;
import com.place4code.reddit.repo.RoleRepo;
import com.place4code.reddit.repo.UserRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DatabaseLoader implements CommandLineRunner {

    private LinkRepo linkRepo;
    private CommentRepo commentRepo;
    private RoleRepo roleRepo;
    private UserRepo userRepo;

    private Map<String, User> users = new HashMap<>();

    public DatabaseLoader(LinkRepo linkRepo, CommentRepo commentRepo, RoleRepo roleRepo, UserRepo userRepo) {
        this.linkRepo = linkRepo;
        this.commentRepo = commentRepo;
        this.roleRepo = roleRepo;
        this.userRepo = userRepo;
    }

    @Override
    public void run(String... args) throws Exception {

        addUsersAndRoles();

        Map<String,String> links = new HashMap<>();
        links.put("What do you think about new Ryzen","https://www.amd.com/en/ryzen");
        links.put("Linux für Java-Entwickler?","https://www.techradar.com/news/best-linux-distro-for-developers");
        links.put("iPhone mit dem TV verbinden: Die besten Apple-TV-Alternativen","https://www.pcwelt.de/a/iphone-mit-dem-tv-verbinden-die-besten-apple-tv-alternativen,3439887");
        links.put("Spring-Boot-Tutorial: Java-Apps mit Spring Boot erstellen","https://www.ionos.de/digitalguide/websites/web-entwicklung/spring-boot-tutorial/");

        links.forEach((k,v) -> {
            Link link = new Link(k, v);
            link.setDesc("Lorem Ipsum is simply dummy text of the printing and typesetting industry. " +
                    "Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, " +
                    "when an unknown printer took a galley of type and scrambled it to make a type specimen book. " +
                    "It has survived not only five centuries, but also the leap into electronic typesetting, " +
                    "remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, " +
                    "and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.");
            link.setUser(users.get("exampleUser"));
            linkRepo.save(link);
            // add comments to the link:
            Comment comment1 = new Comment("How do I get Lorem Ipsum text?",link);
            comment1.setLogin("Lucas");
            comment1.setCreatedBy("lucas@gmail.com");
            comment1.setLastModifiedBy("lucas@gmail.com");
            commentRepo.save(comment1);
            link.addComment(comment1);

            Comment comment2 = new Comment("Is Lorem Ipsum real Latin?",link);
            comment2.setLogin("Claudia");
            comment2.setCreatedBy("claudia@gmail.com");
            comment2.setLastModifiedBy("claudia@gmail.com");
            commentRepo.save(comment2);
            link.addComment(comment2);

            Comment comment3 = new Comment("Who came up with Lorem Ipsum?",link);
            comment3.setLogin("exampleUser");
            comment3.setCreatedBy("user@gmail.com");
            comment3.setLastModifiedBy("user@gmail.com");
            commentRepo.save(comment3);
            link.addComment(comment3);

        });

        long linkCount = linkRepo.count();
        System.out.println("Number of links in the database: " + linkCount );

    }

    private void addUsersAndRoles() {

        System.out.println("add user roles and ");
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String secret = "{bcrypt}" + encoder.encode("password");

        Role userRole = new Role("ROLE_USER");
        roleRepo.save(userRole);
        Role adminRole = new Role("ROLE_ADMIN");
        roleRepo.save(adminRole);

        User user = new User("user@gmail.com",secret,true, "exampleUser", true);
        user.addRole(userRole);
        user.setConfirmPassword(secret);
        userRepo.save(user);
        users.put("exampleUser", user);

        User user2 = new User("lucas@gmail.com",secret,true, "Lucas", true);
        user2.addRole(userRole);
        user2.setConfirmPassword(secret);
        userRepo.save(user2);
        users.put("Lucas", user2);

        User user3 = new User("claudia@gmail.com",secret,true, "Claudia", true);
        user3.addRole(userRole);
        user3.setConfirmPassword(secret);
        userRepo.save(user3);
        users.put("Claudia", user3);
/*
        User admin = new User("admin@gmail.com",secret,true, "admin", false);
        admin.addRole(adminRole);
        admin.setConfirmPassword(secret);
        userRepo.save(admin);

        User master = new User("master@gmail.com",secret,true, "master", false);
        master.addRoles(new HashSet<>(Arrays.asList(userRole,adminRole)));
        master.setConfirmPassword(secret);
        userRepo.save(master);*/

    }

}
