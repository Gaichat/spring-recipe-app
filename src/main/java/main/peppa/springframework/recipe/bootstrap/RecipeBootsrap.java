package main.peppa.springframework.recipe.bootstrap;

import main.peppa.springframework.recipe.model.Category;
import main.peppa.springframework.recipe.model.Difficulty;
import main.peppa.springframework.recipe.model.Ingredient;
import main.peppa.springframework.recipe.model.Recipe;
import main.peppa.springframework.recipe.repositories.CategoryRepository;
import main.peppa.springframework.recipe.repositories.RecipeRepository;
import main.peppa.springframework.recipe.repositories.UnitOfMeasureRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class RecipeBootsrap implements ApplicationListener<ContextRefreshedEvent> {
    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final RecipeRepository recipeRepository;

    public RecipeBootsrap(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository, RecipeRepository recipeRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.recipeRepository = recipeRepository;
    }

    private void loadData() {
        Recipe guacamole = new Recipe();
        guacamole.setCookTime(2);
        guacamole.setDescription("How to make the perfect guacamole");
        guacamole.setDifficulty(Difficulty.MODERATE);
        guacamole.setPrepTime(3);
        guacamole.setServing(4);
        try {
            guacamole.setImage(getImage("resources/images/Guacamole.jpg"));
        }catch (IOException e){
            System.out.println("image for guacamole not exist");
        }
        Set<Category> categories = new HashSet<>();
        categories.add(categoryRepository.findByDescription("American").get());
        guacamole.setCategories(categories);
        Set<Ingredient> ingredients = new HashSet<>();
        Ingredient firstIngr = new Ingredient();
        firstIngr.setDescription("avocado");
        firstIngr.setAmount(BigDecimal.valueOf(2));
        firstIngr.setUom(unitOfMeasureRepository.findByDescription("Ounce").get());
        ingredients.add(firstIngr);
        guacamole.setIngredients(ingredients);
        Recipe savedRecipe = recipeRepository.save(guacamole);
        System.out.println(savedRecipe.getDifficulty());

    }

    private static Byte[] getImage(String name) throws IOException{
        File fi = new File(name);
        byte[] fileContent = Files.readAllBytes(fi.toPath());
        return toObjects(fileContent);
    }

    private static Byte[] toObjects(byte[] bytesPrim) {
        Byte[] bytes = new Byte[bytesPrim.length];
        Arrays.setAll(bytes, n -> bytesPrim[n]);
        return bytes;
    }


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        loadData();

    }
}
