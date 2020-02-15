package main.peppa.springframework.recipe.converters;

import main.peppa.springframework.recipe.commands.CategoryCommand;
import main.peppa.springframework.recipe.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;


class CategoryCommandToCategoryTest {

    Category category;

    CategoryCommand categoryCommand;

    CategoryCommandToCategory categoryCommandToCategory;

    @BeforeEach
    void setUp() {
        categoryCommandToCategory = new CategoryCommandToCategory();
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(categoryCommandToCategory.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(categoryCommandToCategory.convert(new CategoryCommand()));
    }

    @Test
    void convert() {
        categoryCommand = new CategoryCommand();
        categoryCommand.setId(1L);
        categoryCommand.setDescription("test");
        category = categoryCommandToCategory.convert(categoryCommand);
        assertEquals(categoryCommand.getId(),category.getId());
        assertEquals(categoryCommand.getDescription(),category.getDescription());
    }
}