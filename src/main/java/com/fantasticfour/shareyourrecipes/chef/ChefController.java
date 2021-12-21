package com.fantasticfour.shareyourrecipes.chef;

import com.fantasticfour.shareyourrecipes.domains.recipes.Recipe;
import com.fantasticfour.shareyourrecipes.recipes.dtos.RecipeDTO;
import com.fantasticfour.shareyourrecipes.recipes.services.RecipeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/chef")
@PreAuthorize("hasRole('CHEF')")
public class ChefController {

    @Autowired
    RecipeService recipeService;

    @GetMapping(value = { "/recipe", "" })
    public String showRecipeList() {
        return "chef/recipe";
    }

    @GetMapping("/info")
    public String showChefInfo() {

        return "chef/info";
    }

    @GetMapping("/createRecipe")
    public String showCreateRecipe() {
        return "recipe/create";
    }

    @GetMapping("/addRecipe")
    public String showAddRecipe() {
        return "chef/addRecipe";
    }

    @GetMapping("/update/{id}")
    public String updateRecipePage(@PathVariable("id") Long id, Model model) {
        Recipe r = recipeService.findById(id);
        if (r != null) {
            RecipeDTO recipe = new RecipeDTO(r);
            model.addAttribute("recipe", recipe);
            return "chef/updateRecipe";
        }
        return "404";
    }

    @GetMapping("/delete")
    public String deleteRecipePage() {
        return null;
    }
}
