package com.josemolina.financial.income;

import com.josemolina.financial.model.Income;
import com.josemolina.financial.model.User;
import com.josemolina.financial.repository.IncomeRepository;
import com.josemolina.financial.service.income.IncomeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class IncomeServiceTest {

    @Mock
    private IncomeRepository incomeRepository;

    @InjectMocks
    private IncomeServiceImpl incomeServiceImpl;

    private Income income;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId("1");
        user.setName("John Doe");

        income = new Income();
        income.setId("1");
        income.setSource("Salary");
        income.setAmount(BigDecimal.valueOf(1000.00));
        income.setDate(LocalDate.now());
        income.setUser(user);
    }

    @Test
    void testFindAll() {
        List<Income> incomes = new ArrayList<>();
        incomes.add(income);
        when(incomeRepository.findAll()).thenReturn(incomes);

        List<Income> result = incomeServiceImpl.findAll();

        assertEquals(1, result.size());
        assertEquals("Salary", result.get(0).getSource());
        verify(incomeRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(incomeRepository.findById("1")).thenReturn(Optional.of(income));

        Income result = incomeServiceImpl.findById("1");

        assertNotNull(result);
        assertEquals("Salary", result.getSource());
        verify(incomeRepository, times(1)).findById("1");
    }

    @Test
    void testSave() {
        when(incomeRepository.save(any(Income.class))).thenReturn(income);

        Income result = incomeServiceImpl.save(income);

        assertNotNull(result);
        assertEquals("Salary", result.getSource());
        verify(incomeRepository, times(1)).save(any(Income.class));
    }

    @Test
    void testUpdate() {
        when(incomeRepository.findById("1")).thenReturn(Optional.of(income));
        when(incomeRepository.save(any(Income.class))).thenReturn(income);

        Income result = incomeServiceImpl.update("1", income);

        assertNotNull(result);
        assertEquals("Salary", result.getSource());
        verify(incomeRepository, times(1)).findById("1");
        verify(incomeRepository, times(1)).save(any(Income.class));
    }

    @Test
    void testDeleteById() {
        doNothing().when(incomeRepository).deleteById("1");

        incomeServiceImpl.deleteById("1");

        verify(incomeRepository, times(1)).deleteById("1");
    }

    @Test
    void testFindByUser() {
        List<Income> incomes = new ArrayList<>();
        incomes.add(income);
        when(incomeRepository.findByUser(eq(user))).thenReturn(incomes);

        List<Income> result = incomeServiceImpl.findByUser(user);

        assertEquals(1, result.size());
        assertEquals("Salary", result.get(0).getSource());
        verify(incomeRepository, times(1)).findByUser(eq(user));
    }

}