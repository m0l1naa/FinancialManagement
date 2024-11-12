package com.josemolina.financial.service.financial;

import com.josemolina.financial.model.FinancialSummary;
import com.josemolina.financial.model.User;

import java.time.LocalDate;

public interface FinancialSummaryService {
    FinancialSummary generateSummary(User user, LocalDate from, LocalDate to);
}