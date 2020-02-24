package main.peppa.springframework.recipe.controllers;

import lombok.extern.slf4j.Slf4j;
import main.peppa.springframework.recipe.commands.IngredientCommand;
import main.peppa.springframework.recipe.commands.RecipeCommand;
import main.peppa.springframework.recipe.commands.UnitOfMeasureCommand;
import main.peppa.springframework.recipe.model.Ingredient;
import main.peppa.springframework.recipe.services.IngredientService;
import main.peppa.springframework.recipe.services.RecipeService;
import main.peppa.springframework.recipe.services.UnitOfMeasureService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService uomService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService, UnitOfMeasureService uomService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.uomService = uomService;
    }

    @GetMapping("/recipe/{id}/ingredients")
    public String viewIngredients(@PathVariable String id, Model model){
        RecipeCommand recipe = recipeService.findCommandById(Long.valueOf(id));
        model.addAttribute("recipe", recipe);
        return "recipe/ingredient/list";
    }

    @GetMapping("/recipe/{recipe_id}/ingredient/{ingredient_id}/show")
    public String showIngredient(@PathVariable String recipe_id,
                                 @PathVariable String ingredient_id,
                                 Model model){
        model.addAttribute("ingredient", ingredientService.findCommandByIdAndRecipeId(Long.valueOf(ingredient_id),Long.valueOf(recipe_id)));
        log.info("show ing "+ingredientService.findCommandByIdAndRecipeId(Long.valueOf(ingredient_id),Long.valueOf(recipe_id)).getRecipeId());
        return "recipe/ingredient/show";
    }

    @GetMapping("/recipe/{recipe_id}/ingredient/{ingredient_id}/update")
    public String updateIngredient(@PathVariable String recipe_id,
                                   @PathVariable String ingredient_id,
                                   Model model){
        IngredientCommand ingredient = ingredientService.findCommandByIdAndRecipeId(Long.valueOf(ingredient_id),Long.valueOf(recipe_id));
        log.info("tata"+ingredient.getRecipeId());
        model.addAttribute("ingredient", ingredient);
        model.addAttribute("uomList", uomService.findAll());
        return "recipe/ingredient/ingredientform";
    }

    @PostMapping("/recipe/{recipe_id}/ingredient")
    public String updateExistingIngredient(@PathVariable String recipe_id,
                                             @ModelAttribute IngredientCommand ingredient) {
        log.info("toto"+ingredient.getRecipeId());
        IngredientCommand saved = ingredientService.save(ingredient);
        return "redirect:/recipe/" + saved.getRecipeId() + "/ingredient/"+ saved.getId() + "/show";
    }

    @GetMapping("/recipe/{recipe_id}/ingredient/new")
    public String createIngredient(@PathVariable String recipe_id,
                                 Model model){
        //init new ingredient command with recipe id because its needed in hidden field
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(Long.valueOf(recipe_id));

        //init uom
        ingredientCommand.setUom(new UnitOfMeasureCommand());

        model.addAttribute("ingredient", ingredientCommand);
        model.addAttribute("uomList", uomService.findAll());
        return "recipe/ingredient/ingredientform";
    }

    @GetMapping("/recipe/{recipe_id}/ingredient/{ingredient_id}/delete")
    public String deleteIngredient(@PathVariable String recipe_id,
                                           @PathVariable String ingredient_id) {
        ingredientService.deleteById(Long.valueOf(ingredient_id), Long.valueOf(recipe_id));
        return "redirect:/recipe/" + recipe_id + "/ingredients";
    }
}
