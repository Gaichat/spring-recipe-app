package main.peppa.springframework.recipe.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import main.peppa.springframework.recipe.model.Recipe;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class CategoryCommand {
    private Long id;
    private String description;
   // CategoryCommand is missing Set of Recipes because that set was there only to create database relation. You don't need so send it.
}
