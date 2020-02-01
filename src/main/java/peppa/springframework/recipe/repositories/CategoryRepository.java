package peppa.springframework.recipe.repositories;

import org.springframework.data.repository.CrudRepository;
import peppa.springframework.recipe.model.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
