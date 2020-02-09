package main.peppa.springframework.recipe.controllers;

import lombok.extern.slf4j.Slf4j;
import main.peppa.springframework.recipe.repositories.RecipeRepository;
import main.peppa.springframework.recipe.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import main.peppa.springframework.recipe.model.Category;
import main.peppa.springframework.recipe.repositories.CategoryRepository;
import main.peppa.springframework.recipe.repositories.UnitOfMeasureRepository;

import java.util.Optional;

@Slf4j
@Controller
public class IndexController {

    private final RecipeService recipeService;

    public IndexController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping({"","/","index","index.html"})
    public String getIndexPage(Model model){
        log.info("load index page...");
        model.addAttribute("recipes", recipeService.getRecipes());
        return "index";
    }
}
