package main.peppa.springframework.recipe.controllers;


import main.peppa.springframework.recipe.services.RecipeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import main.peppa.springframework.recipe.model.Category;
import main.peppa.springframework.recipe.repositories.CategoryRepository;
import main.peppa.springframework.recipe.repositories.UnitOfMeasureRepository;

import java.util.Optional;

@Controller
public class IndexController {

    private final CategoryRepository categoryRepository;
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final RecipeService recipeService;

    public IndexController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository, RecipeService recipeService) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.recipeService = recipeService;
    }

    @RequestMapping({"","/","index","index.html"})
    public String getIndexPage(Model model){

        recipeService.findAll();
        recipeService.findAll().forEach(r->System.out.println(r.getDescription()));

        model.addAttribute("recipes",recipeService.findAll());

        return "index";
    }
}
