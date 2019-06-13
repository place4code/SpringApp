package com.place4code.reddit;


import com.place4code.reddit.storage.FileStorage;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;

import javax.annotation.Resource;

@SpringBootApplication
@EnableTransactionManagement
public class RedditApplication implements CommandLineRunner {

	@Resource
	FileStorage fileStorage;

	public static void main(String[] args) {
		SpringApplication.run(RedditApplication.class, args);
		System.out.println("For test");
	}


	// TODO * Configuring this bean should not be needed once Spring Boot's Thymeleaf starter includes configuration
	// TODO   for thymeleaf-extras-springsecurity5 (instead of thymeleaf-extras-springsecurity4)
	@Bean
	public SpringSecurityDialect securityDialect() {
		return new SpringSecurityDialect();
	}

	@Override
	public void run(String... args) throws Exception {
		fileStorage.deleteAll();
		fileStorage.init();
	}


}
