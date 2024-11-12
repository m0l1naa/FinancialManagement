package com.josemolina.financial.category;

import com.josemolina.financial.model.Category;
import com.josemolina.financial.repository.CategoryRepository;
import com.josemolina.financial.service.category.CategoryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private Category category;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        category = new Category();
        category.setId("1");
        category.setName("Food");
    }

    @Test
    void testFindAll() {
        List<Category> categories = new ArrayList<>();
        categories.add(category);
        when(categoryRepository.findAll()).thenReturn(categories);

        List<Category> result = categoryService.findAll();

        assertEquals(1, result.size());
        assertEquals("Food", result.get(0).getName());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(categoryRepository.findById("1")).thenReturn(Optional.of(category));

        Category result = categoryService.findById("1");

        assertNotNull(result);
        assertEquals("Food", result.getName());
        verify(categoryRepository, times(1)).findById("1");
    }

    @Test
    void testSave() {
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        Category result = categoryService.save(category);

        assertNotNull(result);
        assertEquals("Food", result.getName());
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void testUpdate() {
        when(categoryRepository.findById("1")).thenReturn(Optional.of(category));
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        Category result = categoryService.update("1", category);

        assertNotNull(result);
        assertEquals("Food", result.getName());
        verify(categoryRepository, times(1)).findById("1");
        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    void testDeleteById() {
        doNothing().when(categoryRepository).deleteById("1");

        categoryService.deleteById("1");

        verify(categoryRepository, times(1)).deleteById("1");
    }

}