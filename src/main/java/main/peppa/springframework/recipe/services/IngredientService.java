package main.peppa.springframework.recipe.services;

import main.peppa.springframework.recipe.commands.IngredientCommand;

public interface IngredientService {
    IngredientCommand findCommandById(Long id);
}
