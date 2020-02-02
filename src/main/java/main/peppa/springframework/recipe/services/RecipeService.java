package main.peppa.springframework.recipe.services;

import main.peppa.springframework.recipe.model.Recipe;

public interface RecipeService {
    Iterable<Recipe> findAll();

}
