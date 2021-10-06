package controller;

import java.util.List;

import com.fantasticfour.shareyourrecipes.domains.Recipe;
import com.fantasticfour.shareyourrecipes.service.RecipeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RecipeController {

    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }
    

    @GetMapping("/")
    public String recipe(Model model) {
        List<Recipe> recipes = recipeService.findAll();

        System.out.println(recipes);
        model.addAttribute("recipes", recipes);
        
        return "index";
    } 

}
