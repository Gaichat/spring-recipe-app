package main.peppa.springframework.recipe.controllers;

import main.peppa.springframework.recipe.commands.RecipeCommand;
import main.peppa.springframework.recipe.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IngredientController {

    private final RecipeService recipeService;

    public IngredientController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    @RequestMapping("/recipe/{id}/ingredients")
    public String viewIngredients(@PathVariable String id, Model model){
        RecipeCommand recipe = recipeService.findCommandById(Long.valueOf(id));
        model.addAttribute("recipe", recipe);
        return "recipe/ingredient/list";
    }


}
