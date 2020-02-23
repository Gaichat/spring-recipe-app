package main.peppa.springframework.recipe.services;

import main.peppa.springframework.recipe.commands.IngredientCommand;
import main.peppa.springframework.recipe.model.Ingredient;

public interface IngredientService {
   // IngredientCommand findCommandById(Long id);

    IngredientCommand save(IngredientCommand ingredient);

    IngredientCommand findCommandByIdAndRecipeId(Long ingredientId, Long recipeId);
}
