package com.fantasticfour.shareyourrecipes;

import java.util.HashSet;
import java.util.Set;

import com.fantasticfour.shareyourrecipes.configs.AuditorAwareImpl;
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
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableAsync
@EnableAutoConfiguration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")

public class ShareyourrecipesApplication {
	@Bean
	public AuditorAware<String> auditorAware() {
		return new AuditorAwareImpl();
	}

	public static void main(String[] args) {
		System.setProperty("jasypt.encryptor.password", "dummy");

		SpringApplication.run(ShareyourrecipesApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(RoleRepo roleRepo, UserRepo userRepo, EmailService emailService,
			PasswordEncoder encoder) throws Exception {
		return args -> {
			if (roleRepo.findByName(ERole.ROLE_USER) == null) {
				roleRepo.save(new Role(ERole.ROLE_USER));
			}
			if (roleRepo.findByName(ERole.ROLE_ADMIN) == null) {
				roleRepo.save(new Role(ERole.ROLE_ADMIN));
			}
			if (userRepo != null) {
				if (!userRepo.findByEmail("lvl3").isPresent()) {
					User me = new User();
					me.setEmail("lvl3");
					me.setPassword(encoder.encode("1234"));
					// me.setBlocked(false);
					me.setEnable(true);
					Set<Role> roles = new HashSet<>();
					roles.add(roleRepo.findByName(ERole.ROLE_ADMIN));
					me.setRoles(roles);
					userRepo.save(me);
				}
			} else {
				System.out.println("null");
			}
			// emailService.testSendEmail("loithui162@gmail.com", "content");
		};
	}

}
