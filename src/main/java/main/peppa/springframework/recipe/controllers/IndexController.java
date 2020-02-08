package main.peppa.springframework.recipe.controllers;

import lombok.extern.slf4j.Slf4j;
import main.peppa.springframework.recipe.repositories.RecipeRepository;
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

    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final RecipeRepository recipeRepository;

    public IndexController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository, RecipeRepository recipeRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.recipeRepository = recipeRepository;
    }

    @RequestMapping({"","/","index","index.html"})
    public String getIndexPage(Model model){
        log.info("load index page...");
        Optional<Category> categoryOptional = categoryRepository.findByDescription("American");
        model.addAttribute("recipes", recipeRepository.findAll());
        return "index";
    }
}
