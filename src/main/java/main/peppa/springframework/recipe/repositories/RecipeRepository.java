package main.peppa.springframework.recipe.repositories;

import main.peppa.springframework.recipe.model.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}
