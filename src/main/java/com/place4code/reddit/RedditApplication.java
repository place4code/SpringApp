package com.place4code.reddit;


import com.place4code.reddit.service.StorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;

@SpringBootApplication
@EnableTransactionManagement
@EnableConfigurationProperties(StorageProperties.class)
public class RedditApplication{

	@Autowired
	private AutowireCapableBeanFactory autowireCapableBeanFactory;

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



	/*@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.deleteAll();
			storageService.init();
		};
	}*/

}
