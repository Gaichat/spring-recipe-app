package main.peppa.springframework.recipe.controllers;

import main.peppa.springframework.recipe.model.Recipe;
import main.peppa.springframework.recipe.services.RecipeService;
import org.h2.command.dml.MergeUsing;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import javax.persistence.Table;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class IndexControllerTest {

    @Mock
    RecipeService recipeService;

    @Mock
    Model model;

    IndexController indexController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        indexController = new IndexController(recipeService);
    }

    @Test
    void getIndexPage() {

        //given
        String expectedPage = "index";
                 /*
        ArgumentCaptor is used to capture arguments for mocked methods.
        In this example, we are mocking the service and telling "when the mock recipeService.getRecipes()
        is called then the mock should return a Set of recipe".
        We then created a ArgumentCapture to capture the argument (Set<Recipe>) for the mocked method.

        "Verify that addAttribute has been called on model once with the attribute name "recipes"
        and value Set<Recipe> - note argumentCaptor.capture() in the verify statement above is returning Set<Recipe>"

        eq : verify attribute name passed is equal to "recipes"
         */
        ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);

        Set<Recipe> recipes = new HashSet<>();
        recipes.add(new Recipe());
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipes.add(recipe);

        when(recipeService.getRecipes()).thenReturn(recipes);

        //when
        String viewName = indexController.getIndexPage(model);


        //then
        assertEquals(expectedPage,viewName);
        verify(recipeService, times(1)).getRecipes();
        verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());
        Set<Recipe> setInController = argumentCaptor.getValue();
        //we asserted that the ArgumentCapture object holds a Set with two Recipe objects
        assertEquals(2, setInController.size());

    }

}