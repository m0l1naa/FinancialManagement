package com.josemolina.financial.financial;

import com.josemolina.financial.model.*;
import com.josemolina.financial.repository.ExpenseRepository;
import com.josemolina.financial.repository.FinancialSummaryRepository;
import com.josemolina.financial.repository.IncomeRepository;
import com.josemolina.financial.service.financial.FinancialSummaryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FinancialSummaryServiceTest {

    @Mock
    private FinancialSummaryRepository financialSummaryRepository;

    @Mock
    private ExpenseRepository expenseRepository;

    @Mock
    private IncomeRepository incomeRepository;

    @InjectMocks
    private FinancialSummaryServiceImpl financialSummaryServiceImpl;

    private User user;
    private Category foodCategory;
    private Category transportCategory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User("Jane Doe", "jane@example.com", "password", "0987654321");
        user.setId("1");

        foodCategory = new Category("Food");
        foodCategory.setId("1");
        transportCategory = new Category("Transport");
        transportCategory.setId("2");
    }

    @Test
    void testGenerateSummary() {
        Expense expense1 = new Expense("Groceries", BigDecimal.valueOf(50.0), LocalDate.of(2024, 10, 1), foodCategory, user);
        Expense expense2 = new Expense("Bus Ticket", BigDecimal.valueOf(10.0), LocalDate.of(2024, 10, 2), transportCategory, user);
        List<Expense> expenses = Arrays.asList(expense1, expense2);

        Income income1 = new Income("Salary", BigDecimal.valueOf(500.0), LocalDate.of(2024, 10, 1), user);
        Income income2 = new Income("Freelance Work", BigDecimal.valueOf(200.0), LocalDate.of(2024, 10, 3), user);
        List<Income> incomes = Arrays.asList(income1, income2);

        when(expenseRepository.findByUserAndDateBetween(user, LocalDate.of(2024, 10, 1), LocalDate.of(2024, 10, 31))).thenReturn(expenses);
        when(incomeRepository.findByUserAndDateBetween(user, LocalDate.of(2024, 10, 1), LocalDate.of(2024, 10, 31))).thenReturn(incomes);

        FinancialSummary summary = financialSummaryServiceImpl.generateSummary(user, LocalDate.of(2024, 10, 1), LocalDate.of(2024, 10, 31));

        assertNotNull(summary);
        assertEquals(BigDecimal.valueOf(60.0), summary.getTotalExpenses());
        assertEquals(BigDecimal.valueOf(700.0), summary.getTotalIncome());
        assertEquals(BigDecimal.valueOf(640.0), summary.getBalance());

        Map<Category, BigDecimal> expectedExpensesByCategory = new HashMap<>();
        expectedExpensesByCategory.put(foodCategory, BigDecimal.valueOf(50.0));
        expectedExpensesByCategory.put(transportCategory, BigDecimal.valueOf(10.0));

        assertEquals(expectedExpensesByCategory, summary.getExpensesByCategory());
    }

    @Test
    void testGenerateSummaryWithNoExpensesAndIncomes() {
        when(expenseRepository.findByUserAndDateBetween(user, LocalDate.of(2024, 10, 1), LocalDate.of(2024, 10, 31))).thenReturn(Arrays.asList());
        when(incomeRepository.findByUserAndDateBetween(user, LocalDate.of(2024, 10, 1), LocalDate.of(2024, 10, 31))).thenReturn(Arrays.asList());

        FinancialSummary summary = financialSummaryServiceImpl.generateSummary(user, LocalDate.of(2024, 10, 1), LocalDate.of(2024, 10, 31));

        assertNotNull(summary);
        assertEquals(BigDecimal.ZERO, summary.getTotalExpenses());
        assertEquals(BigDecimal.ZERO, summary.getTotalIncome());
        assertEquals(BigDecimal.ZERO, summary.getBalance());
        assertTrue(summary.getExpensesByCategory().isEmpty());
    }

}