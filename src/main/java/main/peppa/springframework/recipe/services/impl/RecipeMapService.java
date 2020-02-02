package main.peppa.springframework.recipe.services.impl;

import main.peppa.springframework.recipe.model.Recipe;
import main.peppa.springframework.recipe.repositories.RecipeRepository;
import main.peppa.springframework.recipe.services.RecipeService;
import org.springframework.stereotype.Service;

@Service
public class RecipeMapService implements RecipeService {
    private final RecipeRepository recipeRepository;

    public RecipeMapService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public Iterable<Recipe> findAll(){
        return recipeRepository.findAll();
    }

}
