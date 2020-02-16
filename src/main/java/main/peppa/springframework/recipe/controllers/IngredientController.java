package main.peppa.springframework.recipe.controllers;

import main.peppa.springframework.recipe.commands.RecipeCommand;
import main.peppa.springframework.recipe.services.IngredientService;
import main.peppa.springframework.recipe.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
    }

    @GetMapping
    @RequestMapping("/recipe/{id}/ingredients")
    public String viewIngredients(@PathVariable String id, Model model){
        RecipeCommand recipe = recipeService.findCommandById(Long.valueOf(id));
        model.addAttribute("recipe", recipe);
        return "recipe/ingredient/list";
    }

    @GetMapping
    @RequestMapping("/recipe/{recipe_id}/ingredient/{ingredient_id}/show")
    public String showIngredient(@PathVariable String recipe_id,
                                 @PathVariable String ingredient_id,
                                 Model model){
        model.addAttribute("ingredient", ingredientService.findCommandById(Long.valueOf(ingredient_id)));
        return "recipe/ingredient/show";
    }

}
