package com.josemolina.financial.financial;

import com.josemolina.financial.controller.FinancialSummaryController;
import com.josemolina.financial.model.FinancialSummary;
import com.josemolina.financial.model.User;
import com.josemolina.financial.service.financial.FinancialSummaryService;
import com.josemolina.financial.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class FinancialSummaryControllerTest {

    @Mock
    private FinancialSummaryService financialSummaryService;

    @Mock
    private UserService userService;

    @InjectMocks
    private FinancialSummaryController financialSummaryController;

    private User user;
    private FinancialSummary financialSummary;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setId("1");
        user.setName("John Doe");

        financialSummary = new FinancialSummary();
        financialSummary.setTotalIncome(new BigDecimal("1000.00"));
        financialSummary.setTotalExpenses(new BigDecimal("500.00"));
        financialSummary.setBalance(new BigDecimal("500.00"));
    }

    @Test
    void testGetFinancialSummary() {
        LocalDate from = LocalDate.of(2023, 1, 1);
        LocalDate to = LocalDate.of(2023, 12, 31);

        when(userService.findById("1")).thenReturn(user);
        when(financialSummaryService.generateSummary(any(User.class), eq(from), eq(to))).thenReturn(financialSummary);

        ResponseEntity<FinancialSummary> response = financialSummaryController.getFinancialSummary("1", from, to);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(new BigDecimal("1000.00"), response.getBody().getTotalIncome());
        assertEquals(new BigDecimal("500.00"), response.getBody().getTotalExpenses());
        assertEquals(new BigDecimal("500.00"), response.getBody().getBalance());
        verify(userService, times(1)).findById("1");
        verify(financialSummaryService, times(1)).generateSummary(any(User.class), eq(from), eq(to));
    }

}