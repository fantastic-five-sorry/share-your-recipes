package com.fantasticfour.shareyourrecipes;

import java.time.LocalDateTime;
import java.util.*;

import com.fantasticfour.shareyourrecipes.configs.AuditorAwareImpl;
import com.fantasticfour.shareyourrecipes.domains.PurchasedRecipe;
import com.fantasticfour.shareyourrecipes.domains.PurchasedRecipeId;
import com.fantasticfour.shareyourrecipes.domains.Recipe;
import com.fantasticfour.shareyourrecipes.domains.Role;
import com.fantasticfour.shareyourrecipes.domains.User;
import com.fantasticfour.shareyourrecipes.domains.enums.ERole;
import com.fantasticfour.shareyourrecipes.recipes.repositories.PurchasedRecipeRepository;
import com.fantasticfour.shareyourrecipes.recipes.repositories.RecipeRepository;
import com.fantasticfour.shareyourrecipes.recipes.services.RecipeService;
import com.fantasticfour.shareyourrecipes.user.RoleRepo;
import com.fantasticfour.shareyourrecipes.user.UserRepo;
import com.fantasticfour.shareyourrecipes.user.emailsender.EmailService;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableAsync
@EnableAutoConfiguration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableScheduling
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
			PasswordEncoder encoder, RecipeRepository recipeRepo, PurchasedRecipeRepository purRecipeRepo, RecipeService recipeService)
			throws Exception {
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
			// Recipe r = new Recipe();

			// r.setCreator(userRepo.findByEmail("lvl3").get());
			// Map<String, String> ingredients = new HashMap<>();

			// ingredients.put("Hanh`", "100g");
			// ingredients.put("Hanh`1", "100g");
			// ingredients.put("Hanh`2", "100g");
			// ingredients.put("Hanh`3", "100g");
			// List<String> steps = new ArrayList<>();

			// steps.add("Buowc1: dot ");
			// steps.add("Buowc2: dot ");
			// steps.add("Buowc3: dot ");
			// steps.add("Buowc4: dot ");
			// r.setIngredients(ingredients);

			// Recipe rSaved = recipeRepo.saveAndFlush(r);

			// System.out.println("Da them vao $$$$$$" + rSaved.getId());
			// emailService.testSendEmail("loithui162@gmail.com", "content");

			// -- save pur re
			// PurchasedRecipeId id = new PurchasedRecipeId();
			// id.setRecipe(recipeRepo.findById(1001L).get());
			// id.setUser(userRepo.findByEmail("lvl3").get());
			// PurchasedRecipe pr = new PurchasedRecipe();
			// pr.setPurchasedAt(LocalDateTime.now());
			// pr.setId(id);
			// purRecipeRepo.save(pr);



			// purRecipeRepo.findByCreatorEmail("lvl3").forEach(System.out::println);
			// List<Recipe> recipesByCreator = recipeRepo.findByCreatorId(Long.valueOf(1000));
			// System.out.println(recipesByCreator.get(0).getId());
			// recipeService.deleteRecipe(recipeService.findById(recipesByCreator.get(0).getId()));

		};
	}

}
