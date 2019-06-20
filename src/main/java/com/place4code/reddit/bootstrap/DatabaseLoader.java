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
import java.util.stream.Stream;

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
        links.put("Securing Spring Boot APIs and SPAs with OAuth 2.0","https://auth0.com/blog/securing-spring-boot-apis-and-spas-with-oauth2/?utm_source=reddit&utm_medium=sc&utm_campaign=springboot_spa_securing");
        links.put("Easy way to detect Device in Java Web Application using Spring Mobile - Source code to download from GitHub","https://www.opencodez.com/java/device-detection-using-spring-mobile.htm");
        links.put("Tutorial series about building microservices with SpringBoot (with Netflix OSS)","https://medium.com/@marcus.eisele/implementing-a-microservice-architecture-with-spring-boot-intro-cdb6ad16806c");
        links.put("Detailed steps to send encrypted email using Java / Spring Boot - Source code to download from GitHub","https://www.opencodez.com/java/send-encrypted-email-using-java.htm");
        links.put("Build a Secure Progressive Web App With Spring Boot and React","https://dzone.com/articles/build-a-secure-progressive-web-app-with-spring-boo");
        links.put("Building Your First Spring Boot Web Application - DZone Java","https://dzone.com/articles/building-your-first-spring-boot-web-application-ex");
        links.put("Building Microservices with Spring Boot Fat (Uber) Jar","https://jelastic.com/blog/building-microservices-with-spring-boot-fat-uber-jar/");
        links.put("Spring Cloud GCP 1.0 Released","https://cloud.google.com/blog/products/gcp/calling-java-developers-spring-cloud-gcp-1-0-is-now-generally-available");
        links.put("Simplest way to Upload and Download Files in Java with Spring Boot - Code to download from Github","https://www.opencodez.com/uncategorized/file-upload-and-download-in-java-spring-boot.htm");
        links.put("Add Social Login to Your Spring Boot 2.0 app","https://developer.okta.com/blog/2018/07/24/social-spring-boot");
        links.put("File download example using Spring REST Controller","https://www.jeejava.com/file-download-example-using-spring-rest-controller/");

        links.forEach((k,v) -> {
            Link link = new Link(k, v);
            link.setUser(users.get("exampleUser"));
            linkRepo.save(link);
            // add comments to the link:
            Comment spring = new Comment("Thank you for this link related to Spring Boot. I love it, great post!",link);
            Comment security = new Comment("I love that you're talking about Spring Security",link);
            Comment pwa = new Comment("What is this Progressive Web App thing all about? PWAs sound really cool.",link);

            Stream.of(spring, security, pwa).forEach(c -> {
                c.setLogin("bootstrap");
                commentRepo.save(c);
                link.addComment(c);
            });

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
