package main.peppa.springframework.recipe.controllers;

import main.peppa.springframework.recipe.commands.IngredientCommand;
import main.peppa.springframework.recipe.commands.RecipeCommand;
import main.peppa.springframework.recipe.commands.UnitOfMeasureCommand;
import main.peppa.springframework.recipe.model.Recipe;
import main.peppa.springframework.recipe.services.IngredientService;
import main.peppa.springframework.recipe.services.RecipeService;
import main.peppa.springframework.recipe.services.UnitOfMeasureService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class IngredientControllerTest {

    @InjectMocks
    IngredientController ingredientController;

    @Mock
    RecipeService recipeService;

    @Mock
    IngredientService ingredientService;

    @Mock
    UnitOfMeasureService uomService;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(ingredientController)
                .build();
    }

    @Test
    void getIngredients() throws Exception {
        //given
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(1L);

        //when
        when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);

        //then
        mockMvc.perform(get("/recipe/1/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/list"))
                .andExpect(model().attributeExists("recipe"));
        verify(recipeService, times(1)).findCommandById(anyLong());
    }

    @Test
    void showIngredient() throws Exception {
        //given
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setId(1L);

        //when
        when(ingredientService.findCommandByIdAndRecipeId(anyLong(), anyLong())).thenReturn(ingredientCommand);

        //then
        mockMvc.perform(get("/recipe/1/ingredient/1/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/show"))
                .andExpect(model().attributeExists("ingredient"));
        verify(ingredientService,times(1)).findCommandByIdAndRecipeId(anyLong(), anyLong());
    }

    @Test
    void getUpdateIngredientView() throws Exception {
        //given
        Set<UnitOfMeasureCommand> uomCommand = new HashSet<>();
        IngredientCommand ingredientCommand = new IngredientCommand();

        //when
        when(ingredientService.findCommandByIdAndRecipeId(anyLong(), anyLong())).thenReturn(ingredientCommand);
        when(uomService.findAll()).thenReturn(uomCommand);

        //then
        mockMvc.perform(get("/recipe/1/ingredient/1/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredientform"))
                .andExpect(model().attributeExists("ingredient"))
                .andExpect(model().attributeExists("uomList"));
        verify(ingredientService,times(1)).findCommandByIdAndRecipeId(anyLong(), anyLong());
        verify(uomService,times(1)).findAll();
    }


}