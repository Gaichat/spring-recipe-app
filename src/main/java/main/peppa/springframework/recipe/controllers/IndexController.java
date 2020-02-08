package main.peppa.springframework.recipe.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
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

    public IndexController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @RequestMapping({"","/","index","index.html"})
    public String getIndexPage(){
        log.debug("load index page...");
        Optional<Category> categoryOptional = categoryRepository.findByDescription("American");
        System.out.println(categoryOptional.get().getId());
        return "index";
    }
}
