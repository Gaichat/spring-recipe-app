package main.peppa.springframework.recipe.services;

import main.peppa.springframework.recipe.commands.UnitOfMeasureCommand;
import main.peppa.springframework.recipe.model.UnitOfMeasure;

import java.util.Set;

public interface UnitOfMeasureService {
    Set<UnitOfMeasureCommand> findAll();
}
