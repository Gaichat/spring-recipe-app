package main.peppa.springframework.recipe.services;

import lombok.extern.slf4j.Slf4j;
import main.peppa.springframework.recipe.commands.RecipeCommand;
import main.peppa.springframework.recipe.converters.RecipeCommandToRecipe;
import main.peppa.springframework.recipe.converters.RecipeToRecipeCommand;
import main.peppa.springframework.recipe.exceptions.NotFoundException;
import main.peppa.springframework.recipe.model.Recipe;
import main.peppa.springframework.recipe.repositories.RecipeRepository;
import org.aspectj.weaver.ast.Not;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class RecipeServieImpl implements RecipeService{

    private final RecipeRepository recipeRepository;

    private final RecipeToRecipeCommand recipeToRecipeCommand;

    private final RecipeCommandToRecipe recipeCommandToRecipe;

    public RecipeServieImpl(RecipeRepository recipeRepository, RecipeToRecipeCommand recipeToRecipeCommand,
                            RecipeCommandToRecipe recipeCommandToRecipe) {
        this.recipeRepository = recipeRepository;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
    }

    @Override
    public Set<Recipe> getRecipes(){
        log.info("I'm in geRecipes() service");
        Set<Recipe> recipes = new HashSet<>();
        recipeRepository.findAll().forEach(recipes::add);
        return recipes;
    }

    @Override
    public Recipe findById(Long id) {
        return this.getRecipes().stream()
                .filter(recipe -> recipe.getId().equals(id))
                .findFirst().orElseThrow(()->new NotFoundException("recipe id "+id.toString()+" not exist"));
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand) {
        Recipe detachedRecipe = recipeCommandToRecipe.convert(recipeCommand);
        Recipe savedRecipe = recipeRepository.save(detachedRecipe);
        log.debug("Saved RecipeId:" + savedRecipe.getId());
        return recipeToRecipeCommand.convert(savedRecipe);
    }

    @Override
    @Transactional
    public RecipeCommand findCommandById(Long id) {
        return recipeToRecipeCommand.convert(findById(id));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        recipeRepository.deleteById(id);
    }

}
