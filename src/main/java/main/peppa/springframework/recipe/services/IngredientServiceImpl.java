package main.peppa.springframework.recipe.services;

import main.peppa.springframework.recipe.commands.IngredientCommand;
import main.peppa.springframework.recipe.converters.IngredientToIngredientCommand;
import main.peppa.springframework.recipe.model.Ingredient;
import main.peppa.springframework.recipe.repositories.IngredientRepository;
import org.springframework.stereotype.Service;

@Service
public class IngredientServiceImpl implements IngredientService {

    private final IngredientRepository ingrdienrRepository;
    private final IngredientToIngredientCommand converter;

    public IngredientServiceImpl(IngredientRepository ingrdienrRepository, IngredientToIngredientCommand converter) {
        this.ingrdienrRepository = ingrdienrRepository;
        this.converter = converter;
    }

    @Override
    public IngredientCommand findCommandById(Long id) {
        Ingredient ingredient = ingrdienrRepository.findById(id).orElse(null);
        return converter.convert(ingredient);
    }
}
