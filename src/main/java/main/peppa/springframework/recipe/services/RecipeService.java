package main.peppa.springframework.recipe.services;

import main.peppa.springframework.recipe.commands.RecipeCommand;
import main.peppa.springframework.recipe.model.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipes();

    Recipe findById(Long id);

    RecipeCommand saveRecipeCommand(RecipeCommand testRecipeCommand);
}
