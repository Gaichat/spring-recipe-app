package main.peppa.springframework.recipe.controllers;

import lombok.extern.slf4j.Slf4j;
import main.peppa.springframework.recipe.commands.RecipeCommand;
import main.peppa.springframework.recipe.exceptions.NotFoundException;
import main.peppa.springframework.recipe.services.RecipeService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

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
    public String createRecipe(@Valid @ModelAttribute("recipe") RecipeCommand command, BindingResult bindingResult){
        //bean validation
        //look if binding result has errors
        if(bindingResult.hasErrors()){

            bindingResult.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });

            return "recipe/recipeform";
        }
        RecipeCommand created = recipeService.saveRecipeCommand(command);
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

}
