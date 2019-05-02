package com.place4code.reddit;

import com.place4code.reddit.domain.Comment;
import com.place4code.reddit.domain.Link;
import com.place4code.reddit.repo.CommentRepo;
import com.place4code.reddit.repo.LinkRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class RedditApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedditApplication.class, args);
		System.out.println("For test");
	}

	@Bean
	CommandLineRunner runner(LinkRepo linkRepo, CommentRepo commentRepo) {
		return args -> {
			System.out.println("CommandLineRunner");

			Link link = new Link("place4code", "http://place4code.com");
			linkRepo.save(link);

			Comment comment = new Comment("this is a home page place4code", link);
			commentRepo.save(comment);

			link.addComment(comment);

		};
	}

}
