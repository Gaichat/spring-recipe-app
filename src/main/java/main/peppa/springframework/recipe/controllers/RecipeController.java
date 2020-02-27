package main.peppa.springframework.recipe.controllers;

import lombok.extern.slf4j.Slf4j;
import main.peppa.springframework.recipe.commands.RecipeCommand;
import main.peppa.springframework.recipe.exceptions.NotFoundException;
import main.peppa.springframework.recipe.services.RecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Slf4j
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/recipe/{id}/show")
    public String showRecipe(@PathVariable String id, Model model){
        model.addAttribute("recipe",recipeService.findById(Long.valueOf(id)));
        return "recipe/show";
    }

    @GetMapping("recipe/new")
    public String newRecipe(Model model){
        model.addAttribute("recipe", new RecipeCommand());
        return "recipe/recipeform";
    }

    @PostMapping("recipe")
    public String createRecipe(@ModelAttribute RecipeCommand recipeCommand){
        RecipeCommand created = recipeService.saveRecipeCommand(recipeCommand);
        return "redirect:/recipe/"+created.getId()+"/show";
    }

    @GetMapping("recipe/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model){
        model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
        return "recipe/recipeform";
    }

    @GetMapping("recipe/{id}/delete")
    public String deteleRecipe(@PathVariable String id, Model model){
        recipeService.deleteById(Long.valueOf(id));
        return "redirect:/";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView modelAndView(Exception exception){

        log.error("Handling not found exception");
        log.error(exception.getMessage());

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("recipe/404error");
        modelAndView.addObject("exception", exception);

        return modelAndView;
    }

}
