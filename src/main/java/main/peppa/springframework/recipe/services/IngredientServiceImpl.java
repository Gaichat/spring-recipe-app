package main.peppa.springframework.recipe.services;

import lombok.extern.slf4j.Slf4j;
import main.peppa.springframework.recipe.commands.IngredientCommand;
import main.peppa.springframework.recipe.converters.IngredientCommandToIngredient;
import main.peppa.springframework.recipe.converters.IngredientToIngredientCommand;
import main.peppa.springframework.recipe.model.Ingredient;
import main.peppa.springframework.recipe.model.Recipe;
import main.peppa.springframework.recipe.repositories.IngredientRepository;
import main.peppa.springframework.recipe.repositories.RecipeRepository;
import main.peppa.springframework.recipe.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Slf4j
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingrdienrRepository;
    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final RecipeRepository recipeRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;

    public IngredientServiceImpl(IngredientRepository ingrdienrRepository, IngredientToIngredientCommand converter, IngredientCommandToIngredient converterToIng, RecipeRepository recipeRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.ingrdienrRepository = ingrdienrRepository;
        this.ingredientToIngredientCommand = converter;
        this.ingredientCommandToIngredient = converterToIng;
        this.recipeRepository = recipeRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;

    }

/*    @Override
    @Transactional
    public IngredientCommand findCommandById(Long id) {
        Ingredient ingredient = ingrdienrRepository.findById(id).orElse(null);
        return ingredientToIngredientCommand.convert(ingredient);
    }*/

    @Override
    @Transactional
    public IngredientCommand save(IngredientCommand ingredientCommand) {
             Optional<Recipe> recipeOptional = recipeRepository.findById(ingredientCommand.getRecipeId());

        if(!recipeOptional.isPresent()){

            //todo toss error if not found!
            log.error("Recipe not found for id: " + ingredientCommand.getRecipeId());
            return new IngredientCommand();
        } else {
            Recipe recipe = recipeOptional.get();

            Optional<Ingredient> ingredientOptional = recipe
                    .getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                    .findFirst();

            if(ingredientOptional.isPresent()){
                Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription(ingredientCommand.getDescription());
                ingredientFound.setAmount(ingredientCommand.getAmount());
                ingredientFound.setUom(unitOfMeasureRepository
                        .findById(ingredientCommand.getUom().getId())
                        .orElseThrow(() -> new RuntimeException("UOM NOT FOUND"))); //todo address this
            } else {
                //add new Ingredient
                recipe.addIngredient(ingredientCommandToIngredient.convert(ingredientCommand));
            }

            Recipe savedRecipe = recipeRepository.save(recipe);

            //to do check for fail
            return ingredientToIngredientCommand.convert(savedRecipe.getIngredients().stream()
                    .filter(recipeIngredients -> recipeIngredients.getId().equals(ingredientCommand.getId()))
                    .findFirst()
                    .get());
        }
    }

    @Override
    public IngredientCommand findCommandByIdAndRecipeId(Long ingredientId, Long recipeId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (!recipeOptional.isPresent()){
            //todo impl error handling
            log.error("recipe id not found. Id: " + recipeId);
        }

        Recipe recipe = recipeOptional.get();
        log.info("findCommandByIdAndRecipeId "+recipe.getId());
        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map( ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst();

        if(!ingredientCommandOptional.isPresent()){
            //todo impl error handling
            log.error("Ingredient id not found: " + ingredientId);
        }
        log.info("findCommandByIdAndRecipeId "+recipe.getId());
        return ingredientCommandOptional.get();
    }
}
