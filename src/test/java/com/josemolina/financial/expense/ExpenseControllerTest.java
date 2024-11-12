package com.josemolina.financial.expense;

import com.josemolina.financial.controller.ExpenseController;
import com.josemolina.financial.model.Category;
import com.josemolina.financial.model.Expense;
import com.josemolina.financial.service.category.CategoryService;
import com.josemolina.financial.service.expense.ExpenseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class ExpenseControllerTest {

    @Mock
    private ExpenseService expenseService;

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private ExpenseController expenseController;

    private Expense expense;
    private Category category;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        category = new Category();
        category.setId("1");
        category.setName("Food");

        expense = new Expense();
        expense.setId("1");
        expense.setAmount(BigDecimal.valueOf(100.00));
        expense.setCategory(category);
    }

    @Test
    void testFindAll() {
        List<Expense> expenses = Arrays.asList(expense);
        when(expenseService.findAll()).thenReturn(expenses);

        List<Expense> result = expenseController.findAll();

        assertEquals(1, result.size());
        assertEquals(BigDecimal.valueOf(100.00), result.get(0).getAmount());
        verify(expenseService, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(expenseService.findById("1")).thenReturn(expense);

        Expense result = expenseController.findById("1");

        assertEquals(BigDecimal.valueOf(100.00), result.getAmount());
        verify(expenseService, times(1)).findById("1");
    }

    @Test
    void testSaveWithCategory() {
        when(expenseService.save(any(Expense.class))).thenReturn(expense);

        Expense result = expenseController.save("1", expense);

        assertEquals(BigDecimal.valueOf(100.00), result.getAmount());
        assertEquals("Food", result.getCategory().getName());
        verify(expenseService, times(1)).save(any(Expense.class));
        verify(categoryService, never()).findById(anyString());
    }

    @Test
    void testSaveWithoutCategory() {
        expense.setCategory(null);
        when(categoryService.findById("1")).thenReturn(category);
        when(expenseService.save(any(Expense.class))).thenReturn(expense);

        Expense result = expenseController.save("1", expense);

        assertEquals(BigDecimal.valueOf(100.00), result.getAmount());
        assertEquals("Food", result.getCategory().getName());
        verify(categoryService, times(1)).findById("1");
        verify(expenseService, times(1)).save(any(Expense.class));
    }

    @Test
    void testUpdate() {
        when(expenseService.update(eq("1"), any(Expense.class))).thenReturn(expense);

        Expense result = expenseController.update("1", expense);

        assertEquals(BigDecimal.valueOf(100.00), result.getAmount());
        verify(expenseService, times(1)).update(eq("1"), any(Expense.class));
    }

    @Test
    void testDeleteById() {
        expenseController.deleteById("1");

        verify(expenseService, times(1)).deleteById("1");
    }

    @Test
    void testFindIncomesForAuthenticatedUser() {
        List<Expense> expenses = Arrays.asList(expense);
        when(expenseService.findIncomesForAuthenticatedUser()).thenReturn(expenses);

        List<Expense> result = expenseController.findIncomesForAuthenticatedUser();

        assertEquals(1, result.size());
        assertEquals(BigDecimal.valueOf(100.00), result.get(0).getAmount());
        verify(expenseService, times(1)).findIncomesForAuthenticatedUser();
    }

}