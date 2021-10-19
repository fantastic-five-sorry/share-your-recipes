package com.fantasticfour.shareyourrecipes;

import java.time.LocalDateTime;
import java.util.*;

import com.fantasticfour.shareyourrecipes.configs.AuditorAwareImpl;
import com.fantasticfour.shareyourrecipes.domains.auth.Role;
import com.fantasticfour.shareyourrecipes.domains.auth.User;
import com.fantasticfour.shareyourrecipes.domains.enums.ERole;
import com.fantasticfour.shareyourrecipes.domains.enums.VotingType;
import com.fantasticfour.shareyourrecipes.domains.recipes.PurchasedRecipe;
import com.fantasticfour.shareyourrecipes.domains.recipes.PurchasedRecipeId;
import com.fantasticfour.shareyourrecipes.domains.recipes.Recipe;
import com.fantasticfour.shareyourrecipes.domains.votings.RecipeVoting;
import com.fantasticfour.shareyourrecipes.recipes.dtos.CreateRecipeDTO;
import com.fantasticfour.shareyourrecipes.recipes.dtos.RecipeDTO;
import com.fantasticfour.shareyourrecipes.recipes.repositories.PurchasedRecipeRepository;
import com.fantasticfour.shareyourrecipes.recipes.repositories.RecipeRepository;
import com.fantasticfour.shareyourrecipes.recipes.services.RecipeService;
import com.fantasticfour.shareyourrecipes.user.RoleRepo;
import com.fantasticfour.shareyourrecipes.user.UserRepo;
import com.fantasticfour.shareyourrecipes.user.emailsender.EmailService;
import com.fantasticfour.shareyourrecipes.votings.repos.RecipeVotingRepo;

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
// import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;


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
				// userRepo.findUserInfoByEmail("loithui162@gmail.com").getEmail();
				if (!userRepo.findByEmail("admin@lvl.gg").isPresent()) {
					User me = new User();
					me.setEmail("admin@lvl.gg");
					me.setPassword(encoder.encode("123456"));
					// me.setBlocked(false);
					me.setEnabled(true);
					Set<Role> roles = new HashSet<>();
					roles.add(roleRepo.findByName(ERole.ROLE_ADMIN));
					me.setRoles(roles);
					userRepo.save(me);
				}
			} else {
				System.out.println("null");
			}
			// CreateRecipeDTO r = new CreateRecipeDTO();
			// r.setTitle("helloo baby");
			// r.setImage("image");
			// r.setGuideVideoString("guideVideoString");
			// r.setCreatorId(userRepo.findByEmail("admin@lvl.gg").get().getId());
			// // r.setCreator(userRepo.findByEmail("admin@lvl.gg").get());
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
			// r.setSteps(steps);

			// recipeService.createRecipe(r);
			// recipeService.deleteRecipe(r);
			

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
	

		
			// recipeService.deleteRecipe(recipeService.findById(Long.valueOf(1001)));
			// List<Recipe> testFindAll = recipeRepo.findAll();
			// for (int i = 0; i < testFindAll.size(); i++) {
			// 	System.out.println(testFindAll.get(i).getId());
			// }
		};
	}

}
