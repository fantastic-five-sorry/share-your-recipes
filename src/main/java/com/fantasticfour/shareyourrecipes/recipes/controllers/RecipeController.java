package com.fantasticfour.shareyourrecipes.recipes.controllers;

import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import com.fantasticfour.shareyourrecipes.account.Utils;
import com.fantasticfour.shareyourrecipes.domains.enums.RecipeStatus;
import com.fantasticfour.shareyourrecipes.domains.recipes.Recipe;
import com.fantasticfour.shareyourrecipes.recipes.dtos.CreateRecipeDTO;
import com.fantasticfour.shareyourrecipes.recipes.dtos.RecipeDTO;
import com.fantasticfour.shareyourrecipes.recipes.dtos.UpdateRecipeDTO;
import com.fantasticfour.shareyourrecipes.recipes.repositories.RecipeRepository;
import com.fantasticfour.shareyourrecipes.recipes.services.RecipeService;
import com.fantasticfour.shareyourrecipes.storages.recipe.RecipeStorageService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {
    Logger logger = LoggerFactory.getLogger(RecipeController.class);

    private final RecipeService recipeService;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private RecipeRepository recipeRepo;

    @Autowired
    RecipeStorageService recipeStorageService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;

    }

    @GetMapping(value = "/search")
    public List<RecipeDTO> fullTextSearch(@RequestParam(value = "q") String searchKey) {
        logger.info("Search key " + searchKey);
        FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(entityManager);

        QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory()
                .buildQueryBuilder()
                .forEntity(Recipe.class)
                .get();

        org.apache.lucene.search.Query query = queryBuilder
                .keyword()
                .wildcard()
                .onFields("title")
                .matching(searchKey)
                .createQuery();

        org.hibernate.search.jpa.FullTextQuery jpaQuery = fullTextEntityManager.createFullTextQuery(query,
                Recipe.class);

        List<Recipe> listResult = jpaQuery.getResultList();
        return listResult.stream().map(RecipeDTO::new).collect(Collectors.toList());

    }

    @GetMapping("")
    public ResponseEntity<?> recipe(Pageable pageable) {

        // try {

        // } catch (Exception e) {
        // //TODO: handle exception
        // return ResponseEntity.badRequest().body("error: " + "Recipes is empty");
        // }
        Page<RecipeDTO> recipes = recipeService.findAll(pageable);
        return new ResponseEntity<Page<RecipeDTO>>(recipes, HttpStatus.OK);
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<?> findRecipeBySlug(@PathVariable("slug") String slug) {
        // Long id = Long.parseLong(idRecipe);

        RecipeDTO recipeDTO;
        try {
            recipeDTO = recipeService.getRecipeBySlug(slug);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("error: " + e.getMessage());
        }
        return new ResponseEntity<RecipeDTO>(recipeDTO, HttpStatus.OK);
    }

    @PostMapping("/slug/{slug}/{upVote}")
    public ResponseEntity<?> taoSuaLike(@PathVariable("slug") String slug, @PathVariable("upVote") Long upVote) {
        // Long id = Long.parseLong(idRecipe);

        RecipeDTO recipeDTO;
        try {
            recipeDTO = recipeService.getRecipeBySlug(slug);
            Recipe recipe = recipeService.findById(recipeDTO.getId());
            recipe.setUpVoteCount(upVote);
            recipeRepo.save(recipe);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("error: " + e.getMessage());
        }
        return new ResponseEntity<RecipeDTO>(recipeDTO, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createRecipe(@RequestBody CreateRecipeDTO recipe) {
        try {
            recipeService.createRecipe(recipe);
        } catch (Exception e) {
            // e.printStackTrace();
            return ResponseEntity.badRequest().body("error: " + e.getMessage());
        }
        return ResponseEntity.ok().body("message: " + "add recipe success");
    }

    @PostMapping(value = "/", consumes = { "multipart/form-data" })
    public ResponseEntity<?> handleChangeAvatar(Authentication authentication,
            @RequestParam("image") MultipartFile file, @RequestParam("title") String title,
            @RequestParam("steps") String steps, @RequestParam("ingredients") String ingredients,
            @RequestParam("guideUrl") String guideUrl, RedirectAttributes redirectAttributes) {
        try {
            Long uid = Utils.getIdFromRequest(authentication)
                    .orElseThrow(() -> new IllegalStateException("User not found"));
            if (file.getContentType().contains("image")) {
                logger.info("upload file wt: " + file.getContentType());
                List<String> stepsList = new ObjectMapper().readValue(steps, List.class);
                Map<String, String> ingredsMap = new ObjectMapper().readValue(ingredients, Map.class);
                String fileName = recipeStorageService.store(file);
                // update avt url to user
                String recipesImg = "/storage/recipe/" + fileName;
                CreateRecipeDTO recipe = new CreateRecipeDTO();
                recipe.setImage(recipesImg);
                recipe.setIngredients(ingredsMap);
                recipe.setSteps(stepsList);
                recipe.setGuideVideoString(guideUrl);
                recipe.setCreatorId(uid);
                recipe.setTitle(title);
                recipeService.createRecipe(recipe);
            } else {
                throw new IllegalStateException("Not image type");
            }
            return ResponseEntity.ok().body("Success");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping(value = "/", consumes = { "multipart/form-data" })
    public ResponseEntity<?> handleUpdateRecipe(Authentication authentication,
            @RequestParam(name = "image", required = false) MultipartFile file, @RequestParam(name = "id") Long id,
            @RequestParam(name = "title") String title, @RequestParam(name = "steps") String steps,
            @RequestParam(name = "ingredients") String ingredients, @RequestParam(name = "guideUrl") String guideUrl,
            RedirectAttributes redirectAttributes) {
        try {
            Recipe recipe = recipeService.findById(id);
            if (recipe == null)
                throw new IllegalStateException("Not found recipe");

            Long uid = Utils.getIdFromRequest(authentication)
                    .orElseThrow(() -> new IllegalStateException("User not found"));

            if (!recipe.getCreator().getId().equals(uid))
                throw new IllegalStateException("You dont have permission");

            if (file != null && file.getContentType().contains("image")) {
                String fileName = recipeStorageService.store(file);
                String recipesImg = "/storage/recipe/" + fileName;
                recipe.setImage(recipesImg);
            } else {
                List<String> stepsList = new ObjectMapper().readValue(steps, List.class);
                Map<String, String> ingredsMap = new ObjectMapper().readValue(ingredients, Map.class);
                recipe.setIngredients(ingredsMap);
                recipe.setSteps(stepsList);
                recipe.setGuideVideoUrl(guideUrl);
                recipe.setTitle(title);
            }
            recipeRepo.save(recipe);
            return ResponseEntity.ok().body("Success");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // @PutMapping("/update")
    // public ResponseEntity<?> updateRecipe(@RequestBody UpdateRecipeDTO
    // updateRecipeDTO) {
    // try {
    // recipeService.updateRecipe(updateRecipeDTO);
    // } catch (Exception e) {
    // return ResponseEntity.badRequest().body("error: " + e.getMessage());
    // }
    // return ResponseEntity.ok().body("message: " + "update recipe success");
    // }

    @DeleteMapping("/{idRecipe}")
    public ResponseEntity<?> deleteRecipe(@PathVariable("idRecipe") Long idRecipe) {

        try {
            recipeService.deleteRecipe(idRecipe);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("error: " + e.getMessage());
        }
        return ResponseEntity.ok().body("message: " + "delete recipe success");

    }

    @GetMapping("/{idRecipe}")
    public ResponseEntity<?> findRecipeById(@PathVariable("idRecipe") Long idRecipe) {
        RecipeDTO recipeDTO;
        try {
            recipeDTO = recipeService.viewRecipeById(idRecipe);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("error: " + e.getMessage());
        }
        return new ResponseEntity<RecipeDTO>(recipeDTO, HttpStatus.OK);
    }

    @GetMapping("/myRecipe")
    public Page<RecipeDTO> getMyRecipes(Authentication auth, Pageable page) {

        try {
            Long uid = Utils.getIdFromRequest(auth).orElseThrow(() -> new IllegalStateException("User not found"));
            return recipeService.findByCreator(uid, page);

        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping("/myRecipe/approved")
    public Page<RecipeDTO> getMyRecipesApproved(Authentication auth, Pageable page) {

        try {
            Long uid = Utils.getIdFromRequest(auth).orElseThrow(() -> new IllegalStateException("User not found"));
            return recipeService.findAllApprovedRecipesByCreator(uid, page);

        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping("/myRecipe/pending")
    public Page<RecipeDTO> getMyRecipesPending(Authentication auth, Pageable page) {

        try {
            Long uid = Utils.getIdFromRequest(auth).orElseThrow(() -> new IllegalStateException("User not found"));
            return recipeService.findAllNotApprovedRecipesByCreator(uid, page);

        } catch (Exception e) {
            return null;
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<?> findByStatus(@PathVariable("status") RecipeStatus status, Pageable pageable) {
        try {
            return new ResponseEntity<Page<RecipeDTO>>(recipeService.findByStatus(status.toString(), pageable),
                    HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("error: " + e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/approve/{id}")
    public ResponseEntity<?> approveRecipe(@PathVariable("id") Long id) {

        try {
            logger.info("ID: " + id);
            Recipe recipe = recipeRepo.findById(id).orElseThrow(() -> new IllegalStateException("recipe not found"));
            recipe.setStatus(RecipeStatus.APPROVED);
            recipeRepo.save(recipe);
            return ResponseEntity.ok().body("Success");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
