package main.peppa.springframework.recipe.services;

import main.peppa.springframework.recipe.model.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipes();

}
