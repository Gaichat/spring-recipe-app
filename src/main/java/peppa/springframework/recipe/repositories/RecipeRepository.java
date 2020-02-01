package peppa.springframework.recipe.repositories;

import org.springframework.data.repository.CrudRepository;
import peppa.springframework.recipe.model.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}
