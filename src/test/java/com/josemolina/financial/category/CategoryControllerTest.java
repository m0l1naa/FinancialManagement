package com.josemolina.financial.category;

import com.josemolina.financial.controller.CategoryController;
import com.josemolina.financial.model.Category;
import com.josemolina.financial.service.category.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    private Category category;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        category = new Category();
        category.setId("1");
        category.setName("Groceries");
    }

    @Test
    void testFindAll() {
        List<Category> categories = Arrays.asList(category);
        when(categoryService.findAll()).thenReturn(categories);

        List<Category> result = categoryController.findAll();

        assertEquals(1, result.size());
        assertEquals("Groceries", result.get(0).getName());
        verify(categoryService, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(categoryService.findById("1")).thenReturn(category);

        Category result = categoryController.findById("1");

        assertEquals("Groceries", result.getName());
        verify(categoryService, times(1)).findById("1");
    }

    @Test
    void testSave() {
        when(categoryService.save(any(Category.class))).thenReturn(category);

        Category result = categoryController.save(category);

        assertEquals("Groceries", result.getName());
        verify(categoryService, times(1)).save(any(Category.class));
    }

    @Test
    void testUpdate() {
        when(categoryService.update(eq("1"), any(Category.class))).thenReturn(category);

        Category result = categoryController.update("1", category);

        assertEquals("Groceries", result.getName());
        verify(categoryService, times(1)).update(eq("1"), any(Category.class));
    }

    @Test
    void testDeleteById() {
        categoryController.deleteById("1");

        verify(categoryService, times(1)).deleteById("1");
    }

}