package main.peppa.springframework.recipe.repositories;

import main.peppa.springframework.recipe.model.Ingredient;
import org.springframework.data.repository.CrudRepository;

public interface IngredientRepository extends CrudRepository <Ingredient, Long> {
}
