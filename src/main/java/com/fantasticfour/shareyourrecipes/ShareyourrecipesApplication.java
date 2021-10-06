package com.fantasticfour.shareyourrecipes;

import com.fantasticfour.shareyourrecipes.domains.ERole;
import com.fantasticfour.shareyourrecipes.domains.Role;
import com.fantasticfour.shareyourrecipes.domains.User;
import com.fantasticfour.shareyourrecipes.emailsender.EmailService;
import com.fantasticfour.shareyourrecipes.user.RoleRepo;
import com.fantasticfour.shareyourrecipes.user.UserRepo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EnableAutoConfiguration
public class ShareyourrecipesApplication {

	public static void main(String[] args) {
		System.setProperty("jasypt.encryptor.password", "dummy");

		SpringApplication.run(ShareyourrecipesApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(RoleRepo roleRepo, UserRepo userRepo, EmailService emailService) throws Exception {
		return args -> {
			if (roleRepo.findByName(ERole.ROLE_USER) == null) {
				roleRepo.save(new Role(ERole.ROLE_USER));
			}
			if (roleRepo.findByName(ERole.ROLE_ADMIN) == null) {
				roleRepo.save(new Role(ERole.ROLE_ADMIN));
			}
			if (!userRepo.findByEmail("lvl").isPresent()) {
				User me = new User();
				me.setEmail("lvl");
				me.setPassword("1234");
				// me.setBlocked(false);
				me.setEnable(true);
				me.getRoles().add(new Role(ERole.ROLE_ADMIN));
				userRepo.save(me);
			}
			// emailService.testSendEmail("loithui162@gmail.com", "content");
		};
	}

}
